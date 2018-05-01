/**
 * 
 */
package pk.questlab.mbt.testgenerator.diagrams.helpers;

import java.util.ArrayList;

import javax.swing.plaf.synth.Region;
import javax.swing.text.html.InlineView;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.business.internal.metamodel.helper.DSemanticDiagramHelper;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.polarsys.capella.common.data.behavior.AbstractEvent;
import org.polarsys.capella.core.data.capellacommon.AbstractState;
import org.polarsys.capella.core.data.capellacommon.InitialPseudoState;
import org.polarsys.capella.core.data.capellacommon.Mode;
import org.polarsys.capella.core.data.capellacommon.StateTransition;
import org.polarsys.capella.core.data.capellacommon.impl.InitialPseudoStateImpl;
import org.polarsys.capella.core.data.capellacommon.impl.RegionImpl;
import org.polarsys.capella.core.data.capellacommon.impl.StateImpl;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.information.AbstractEventOperation;
import org.polarsys.capella.core.data.information.datavalue.OpaqueExpression;
import org.polarsys.capella.core.data.interaction.AbstractCapability;
import org.polarsys.capella.core.data.interaction.ConstraintDuration;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.data.interaction.SequenceMessage;

import pk.questlab.mbt.testgenerator.diagrams.MBTViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.SAViewPoint;
import pk.questlab.mbt.testgenerator.templates.PySuiteTemplate;
import pk.questlab.mbt.testgenerator.templates.PyTestTemplate;

/**
 * @author Muhammad Abbas
 *
 */
public class SneakTestGenerationHelper {
	private static SneakTestGenerationHelper ins;
	private static RegionImpl root;
	private static SAViewPoint viewPoint;
	private static ArrayList<AbstractEventOperation> allFunctions;
	private static ArrayList<PyTestTemplate>tests;
	private static ArrayList<String> includes;
	private SneakTestGenerationHelper()
	{
		includes= new ArrayList<String>();
		tests= new ArrayList<PyTestTemplate>();
		allFunctions=new ArrayList<AbstractEventOperation>();
	}
	public PySuiteTemplate getPySuite()
	{
		processModeState();
		PySuiteTemplate suite= new PySuiteTemplate("SneakPath", includes, tests);
		return suite;
	}
	public static SneakTestGenerationHelper getInstance()
	{
		if(ins==null)
		{
			ins = new SneakTestGenerationHelper();
			return ins;
		}
		return ins;
	}
	public void init(RegionImpl region, SAViewPoint view)
	{
		this.root= region;
		this.viewPoint=view;
		allFunctions= new ArrayList<AbstractEventOperation>();
		for(DRepresentation d:viewPoint.getOwnedDiagrams())
		{
			if(((DSemanticDiagram)d).getDescription().getName().toLowerCase().equals(new String("component exchanges scenario")))
			{
				Scenario diagramRoot=(Scenario) DSemanticDiagramHelper.getRootContent((DSemanticDiagram)d);
				for(SequenceMessage sm: diagramRoot.getOwnedMessages())
				{
					AbstractEventOperation aeo=sm.getInvokedOperation();
					if(!allFunctions.contains(aeo))
					{
						allFunctions.add(aeo);
					}
				}
			}
		}
	}
	public void processModeState()
	{
		// Visited list to avoid loops in Diagram
		ArrayList<AbstractState> visited= new ArrayList<AbstractState>();
		// Prints states recursively
		for(AbstractState as:root.getOwnedStates())
		{
			if(as instanceof InitialPseudoStateImpl)
			{
				InitialPseudoStateImpl alpha= (InitialPseudoStateImpl)as;
				processStateRecursively(alpha, visited, null);
			}
		}
	}
	/*
	 * @param AbstractState state
	 * @param ArrayList visited_states 
	 * Description: This function prints state and transition and call it self recursively
	 * Dependency: self, printTransition(StateTransition)
	 * */
	private static void processStateRecursively(AbstractState state, ArrayList visited, StateTransition transition)
	{
		if(state==null)
			return;
		if(visited.contains(state))
			return;
		// State based code here
		visited.add(state);
		if(state instanceof InitialPseudoState)
		{
			StateTransition first= state.getOutgoing().get(0);
			for(AbstractEvent ae:first.getTriggers())
			{
				if(ae instanceof AbstractEventOperation)
				{
					AbstractEventOperation aeoTemp=(AbstractEventOperation) ae;
					if(aeoTemp.getSummary()!=null)
					{
						//includes.add(aeoTemp.getSummary().replace("&quot;", "\""));
					}
					else
					{
						//go for name
					}
				}
			}
		}
		else
		{
			// code for rest of states
			for(AbstractEventOperation func:allFunctions)
			{
				ArrayList<String> tempTestBody=new ArrayList<String>();
				Mode m= (Mode)state;
				String testName= m.getName().replaceAll("[^a-zA-Z]+","")+func.getName().replaceAll("[^a-zA-Z]+","")+"SneakTest";
				String testClass="SneakPath";
				String cut="SneakPath";
				if(!m.getAvailableAbstractFunctions().isEmpty())
				{
					boolean generate=true;
					for(AbstractFunction af:m.getAvailableAbstractFunctions())
					{
						if(func.getName().equals(af.getName()))
						{
							generate=false;
						}
					}
					if(generate)
					{
						if(func.getInvokingSequenceMessages().isEmpty())
							continue;
						SequenceMessage sm=func.getInvokingSequenceMessages().get(0);
						if(sm.getExchangeContext()==null)
							continue;
						OpaqueExpression oe=(OpaqueExpression) sm.getExchangeContext().getOwnedSpecification();
						if(oe==null)
							continue;
						if(oe.getBodies().isEmpty())
							break;
						String ocl=oe.getBodies().get(0);
						String context=null;
						String methodCall=null;
						if(sm.getReceivingPart().getRepresentingInstanceRoles().isEmpty())
							continue;
						if(sm.getReceivingPart().getRepresentingInstanceRoles().get(0).getSummary()==null || sm.getReceivingPart().getRepresentingInstanceRoles().get(0).getSummary().isEmpty())
						{
							context=sm.getReceivingPart().getRepresentingInstanceRoles().get(0).getName().replaceAll("[^a-zA-Z]+","");
						}
						else
						{
							// go for summary
							context=sm.getReceivingPart().getRepresentingInstanceRoles().get(0).getSummary().replace("&quot;", "\"");;
						}
						if(transition==null)
							continue;
						else
						{
							for(AbstractEvent ae:transition.getTriggers())
							{
								if(ae instanceof AbstractEventOperation)
								{
									AbstractEventOperation aeoTemp=(AbstractEventOperation) ae;
									if(aeoTemp.getSummary()!=null)
									{
										tempTestBody.add(aeoTemp.getSummary().replace("&quot;", "\""));
									}
									else
									{
										tempTestBody.add(aeoTemp.getName());
									}
									//time here
									if(!((AbstractEventOperation) ae).getInvokingSequenceMessages().isEmpty())
									{
										SequenceMessage smTemp=((AbstractEventOperation) ae).getInvokingSequenceMessages().get(0);
										if(((Scenario)smTemp.eContainer()).getOwnedConstraintDurations()!=null)
										{
											Scenario sroot=(Scenario) smTemp.eContainer();
											if(sroot.getOwnedConstraintDurations().size()>0)
											{
												for(ConstraintDuration duration:sroot.getOwnedConstraintDurations())
												{
													if(duration.getStart().equals(smTemp.getReceivingEnd()))
													{
														tempTestBody.add("time.sleep("+duration.getDuration()+")");
													}
												}
											}
										}
									}
								}
							}
						}
						if(sm.getSummary()!=null)
							methodCall=sm.getSummary().replace("&quot;", "\"");
						else
							methodCall=sm.getName();
						tempTestBody.add(methodCall);
						if(((Scenario)sm.eContainer()).getOwnedConstraintDurations()!=null)
						{
							Scenario sroot=(Scenario) sm.eContainer();
							if(sroot.getOwnedConstraintDurations().size()>0)
							{
								for(ConstraintDuration duration:sroot.getOwnedConstraintDurations())
								{
									if(duration.getStart().equals(sm.getReceivingEnd()))
									{
										tempTestBody.add("time.sleep("+duration.getDuration()+")");
									}
								}
							}
						}
						for(String line:oclToPyPostConditions(ocl, context))
						{
							tempTestBody.add(line);
						}
						PyTestTemplate testMethod= new PyTestTemplate(testName, testClass, tempTestBody);
						tests.add(testMethod);
					}
				}
			}
		}
		
		for(StateTransition t:state.getOutgoing())
		{
			processStateRecursively(t.getTarget(),visited, t);
		}
	}
	private static ArrayList<String> oclToPyPostConditions(String ocl, String context) {
		String[] parsedOcl= ocl.split("\n");
		ArrayList<String> toret= new ArrayList<String>();
		for(String line:parsedOcl)
		{
			if(line!=null && line!="")
			{
				String pyString=line.replace("self", context);
				pyString=pyString.replace("=", "==");
				pyString=pyString.replace("<>", "!=");
				pyString= "self.assertFalse("+pyString+")";
				toret.add(pyString);
			}
		}
		
		return toret;
	}
}
