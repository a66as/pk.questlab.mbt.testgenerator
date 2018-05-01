
package pk.questlab.mbt.testgenerator.diagrams.helpers;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.AbstractConstraint;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.helpers.interaction.delegates.InteractionUseHelper;
import org.polarsys.capella.core.data.helpers.interaction.delegates.SequenceMessageHelper;
import org.polarsys.capella.core.data.information.datavalue.OpaqueExpression;
import org.polarsys.capella.core.data.interaction.AbstractEnd;
import org.polarsys.capella.core.data.interaction.AbstractFragment;
import org.polarsys.capella.core.data.interaction.CombinedFragment;
import org.polarsys.capella.core.data.interaction.ConstraintDuration;
import org.polarsys.capella.core.data.interaction.EventReceiptOperation;
import org.polarsys.capella.core.data.interaction.InstanceRole;
import org.polarsys.capella.core.data.interaction.InteractionFragment;
import org.polarsys.capella.core.data.interaction.InteractionOperand;
import org.polarsys.capella.core.data.interaction.MessageEnd;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.data.interaction.SequenceMessage;
import org.polarsys.capella.core.data.interaction.TimeLapse;
import org.polarsys.capella.core.data.interaction.impl.ExecutionImpl;
import org.polarsys.capella.core.data.interaction.impl.FragmentEndImpl;

import pk.questlab.mbt.testgenerator.templates.GtestTemplate;
import pk.questlab.mbt.testgenerator.templates.PySuiteTemplate;
import pk.questlab.mbt.testgenerator.templates.PyTestTemplate;
import pk.questlab.mbt.testgenerator.templates.TESTTemplate;

/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 */
public class ExchangeScenarioHelper 
{
	private static ExchangeScenarioHelper INSTANCE;
	private static Scenario root;
	private static InstanceRole callInitiator; // first call initiator
	private static String diagramName;
	private ExchangeScenarioHelper()
	{
		
	}
	public static void init(Scenario droot, String modelName)
	{
		root=droot;
		diagramName=modelName;
		diagramName=diagramName.replace("[ES]", "");
		diagramName=diagramName.replace("\\s+", "");
		diagramName=diagramName.replaceAll("[^a-zA-Z]+","");
		for(InstanceRole ir:root.getOwnedInstanceRoles())
		{
			if(ir.getAbstractEnds().size()>0)
			{
				AbstractEnd tempEnd=ir.getAbstractEnds().get(0);
				if(tempEnd.getName().toLowerCase().equals(new String("send call message call")))
				{
					callInitiator=ir;
				}
			}
		}
	}
	public static ExchangeScenarioHelper getInstance()
	{
		if(INSTANCE==null)
		{
			INSTANCE= new ExchangeScenarioHelper();
		}
		return INSTANCE;
	}
	/*
	 * @param 
	 * Description: 
	 * Dependency: 
	 * */
	public void printScenario()
	{
		if(callInitiator!=null) // have some start
		{
			for(SequenceMessage sm: root.getOwnedMessages())
			{
				System.out.println(sm.getSendingPart().getName()+" -"+sm.getName()+"-> "+ sm.getReceivingPart().getName());
			}
		}
	}
	public GtestTemplate getGTestSuite()
	{
		ArrayList<TESTTemplate> testMethods= new ArrayList<TESTTemplate>();
		int altCount=0;
		boolean altDetected=false;
		ArrayList<String> op_t_body=new ArrayList<String>();
		for(InteractionFragment interactionFragment:root.getOwnedInteractionFragments())
		{
			ArrayList<String> t_body=new ArrayList<String>();
			if(interactionFragment instanceof FragmentEndImpl)
			{
				FragmentEndImpl fragmnetEnd= (FragmentEndImpl)interactionFragment;
				AbstractFragment abstractFragment=fragmnetEnd.getAbstractFragment();
				if(abstractFragment instanceof CombinedFragment && fragmnetEnd.getName().equals(new String("start")) &&((CombinedFragment) abstractFragment).getOperator().toString().equals(new String("ALT")))
				{
					altDetected=true;
				}
				else if(abstractFragment instanceof CombinedFragment && fragmnetEnd.getName().equals(new String("end")) && ((CombinedFragment) abstractFragment).getOperator().toString().equals(new String("ALT")))
				{
					altDetected=false;
					op_t_body.add("}");
					String testClassifier=diagramName;
					TESTTemplate operandTestCase= new TESTTemplate("altFragment", testClassifier, op_t_body);
					testMethods.add(operandTestCase);
					op_t_body=new ArrayList<String>();
				}
			}
			else if(interactionFragment instanceof InteractionOperand && altDetected==true)
			{
				InteractionOperand operand= (InteractionOperand)interactionFragment;
				AbstractConstraint constraint=(AbstractConstraint) operand.getGuard();
				OpaqueExpression opaqueExpression=(OpaqueExpression) constraint.getOwnedSpecification();
				if(opaqueExpression.getBodies().size()>0)
				{
					if(altCount==0)
					{
						op_t_body.add("if("+opaqueExpression.getBodies().get(0).toString()+"){");
						altCount++;
					}
					else if(altCount>0)
					{
						op_t_body.add("}");
						op_t_body.add("else if("+opaqueExpression.getBodies().get(0).toString()+"){");
						altCount++;
					}
				}	
			}
			else if(interactionFragment instanceof MessageEnd && interactionFragment.getName().toString().equals(new String("Send Call Message Call")) && altDetected==true)
			{
				MessageEnd messageEnd = (MessageEnd) interactionFragment;
	            SequenceMessage sequenceMessage = messageEnd.getMessage();
	            String testName=sequenceMessage.getInvokedOperation().getName().replaceAll("[^a-zA-Z]+","")+"Test";
				String calledFunction=sequenceMessage.getInvokedOperation().getName().replaceAll("[^a-zA-Z]+","");
				String testClassifier=diagramName;
				if(sequenceMessage.getExchangedItems().isEmpty())
				{
					op_t_body.add("   "+calledFunction+"()");
					op_t_body.add("   EXPECT_TRUE("+calledFunction+"CALLED)");
				}
			}
			else if(interactionFragment instanceof MessageEnd && interactionFragment.getName().toString().equals(new String("Send Call Message Call")) && altDetected==false) 
			{
	            MessageEnd messageEnd = (MessageEnd) interactionFragment;
	            SequenceMessage sequenceMessage = messageEnd.getMessage();
	            String testName=sequenceMessage.getInvokedOperation().getName().replaceAll("[^a-zA-Z]+","")+"Test";
				String calledFunction=sequenceMessage.getInvokedOperation().getName().replaceAll("[^a-zA-Z]+","");
				String testClassifier=diagramName;
				if(sequenceMessage.getExchangedItems().isEmpty())
				{
					t_body.add(calledFunction+"()");
					System.out.println(calledFunction);
					if(sequenceMessage.getExchangeContext()!=null)
					{
						OpaqueExpression tempExp=(OpaqueExpression) sequenceMessage.getExchangeContext().getOwnedSpecification();
						t_body.add(tempExp.getBodies().get(0).toString());
					}
					t_body.add("ASSERT_TRUE("+calledFunction+"CALLED)");
				}
				TESTTemplate testCase= new TESTTemplate(testName, testClassifier, t_body);
				testMethods.add(testCase);
	     	}
		}
		ArrayList<String> incs=new ArrayList<String>();
		for(InstanceRole ir:root.getOwnedInstanceRoles())
		{
			String temp=ir.getName().replaceAll("[^a-zA-Z]+","");
			temp=temp.replace("\\s", "");
			incs.add("#include \""+temp+".h\"");
		}
		return new GtestTemplate(diagramName, incs, testMethods);
	}
	public PySuiteTemplate getPySuite()
	{
		ArrayList<PyTestTemplate> testMethods= new ArrayList<PyTestTemplate>();
		int altCount=0;
		boolean altDetected=false;
		ArrayList<String> op_t_body=new ArrayList<String>();
		for(InteractionFragment interactionFragment:root.getOwnedInteractionFragments())
		{
			ArrayList<String> t_body=new ArrayList<String>();
			if(interactionFragment instanceof FragmentEndImpl)
			{
				FragmentEndImpl fragmnetEnd= (FragmentEndImpl)interactionFragment;
				AbstractFragment abstractFragment=fragmnetEnd.getAbstractFragment();
				if(abstractFragment instanceof CombinedFragment && fragmnetEnd.getName().equals(new String("start")) &&((CombinedFragment) abstractFragment).getOperator().toString().equals(new String("ALT")))
				{
					altDetected=true;
				}
				else if(abstractFragment instanceof CombinedFragment && fragmnetEnd.getName().equals(new String("end")) && ((CombinedFragment) abstractFragment).getOperator().toString().equals(new String("ALT")))
				{
					altDetected=false;
					String testClassifier=diagramName;
					PyTestTemplate operandTestCase= new PyTestTemplate("altFragment", testClassifier, op_t_body);
					testMethods.add(operandTestCase);
					op_t_body=new ArrayList<String>();
				}
			}
			else if(interactionFragment instanceof InteractionOperand && altDetected==true)
			{
				InteractionOperand operand= (InteractionOperand)interactionFragment;
				AbstractConstraint constraint=(AbstractConstraint) operand.getGuard();
				OpaqueExpression opaqueExpression=(OpaqueExpression) constraint.getOwnedSpecification();
				if(opaqueExpression.getBodies().size()>0)
				{
					if(altCount==0)
					{
						op_t_body.add("\tif"+opaqueExpression.getBodies().get(0).toString()+":");
						altCount++;
					}
					else if(altCount>0)
					{
						op_t_body.add("\telse if "+opaqueExpression.getBodies().get(0).toString()+":");
						altCount++;
					}
				}	
			}
			else if(interactionFragment instanceof MessageEnd && interactionFragment.getName().toString().equals(new String("Send Call Message Call")) && altDetected==true)
			{
				MessageEnd messageEnd = (MessageEnd) interactionFragment;
	            SequenceMessage sequenceMessage = messageEnd.getMessage();
	            String testName=sequenceMessage.getInvokedOperation().getName().replaceAll("[^a-zA-Z]+","")+"Test";
				String calledFunction=sequenceMessage.getSummary().replace("&quot;", "\"");;
				String testClassifier=diagramName;
				if(sequenceMessage.getExchangedItems().isEmpty())
				{
					op_t_body.add("\t\t\t"+calledFunction);
					if(sequenceMessage.getExchangeContext()!=null)
					{
						OpaqueExpression tempExp=(OpaqueExpression) sequenceMessage.getExchangeContext().getOwnedSpecification();
						for(String s:oclToPyPostConditions(tempExp.getBodies().get(0).toString(), sequenceMessage.getReceivingEnd().getCovered().getSummary()))
							op_t_body.add(s);
					}
				}
			}
			else if(interactionFragment instanceof MessageEnd && interactionFragment.getName().toString().equals(new String("Send Call Message Call")) && altDetected==false) 
			{
	            MessageEnd messageEnd = (MessageEnd) interactionFragment;
	            SequenceMessage sequenceMessage = messageEnd.getMessage();
	            String testName=sequenceMessage.getInvokedOperation().getName().replaceAll("[^a-zA-Z]+","")+"Test";
				String calledFunction=sequenceMessage.getSummary().replace("&quot;", "\"");
				String testClassifier=diagramName;
				if(sequenceMessage.getExchangedItems().isEmpty())
				{
					t_body.add(""+calledFunction);
					if(root.getOwnedConstraintDurations()!=null)
					{
						if(root.getOwnedConstraintDurations().size()>0)
						{
							for(ConstraintDuration duration:root.getOwnedConstraintDurations())
							{
								if(duration.getStart().equals(sequenceMessage.getReceivingEnd()))
								{
									t_body.add("time.sleep("+duration.getDuration()+")");
								}
							}
						}
					}
					if(sequenceMessage.getExchangeContext()!=null)
					{
						OpaqueExpression tempExp=(OpaqueExpression) sequenceMessage.getExchangeContext().getOwnedSpecification();
						for(String s:oclToPyPostConditions(tempExp.getBodies().get(0).toString(), sequenceMessage.getReceivingEnd().getCovered().getSummary()))
							t_body.add(s);
					}
				}
				PyTestTemplate testCase= new PyTestTemplate(testName, testClassifier, t_body);
				testMethods.add(testCase);
	     	}
		}
		ArrayList<String> incs=new ArrayList<String>();
		for(InstanceRole ir:root.getOwnedInstanceRoles())
		{
			String temp=ir.getName().replaceAll("[^a-zA-Z]+","");
			temp=temp.replace("\\s", "");
			incs.add("import "+temp);
		}
		return new PySuiteTemplate(diagramName, incs, testMethods);
	}
	private ArrayList<String> oclToPyPostConditions(String ocl, String context) {
		String[] parsedOcl= ocl.split("\n");
		ArrayList<String> toret= new ArrayList<String>();
		for(String line:parsedOcl)
		{
			if(line!=null && line!="")
			{
				String pyString=line.replace("self", context);
				pyString=pyString.replace("=", "==");
				pyString=pyString.replace("<>", "!=");
				pyString= "self.assertTrue("+pyString+")";
				toret.add(pyString);
			}
		}
		
		return toret;
	}
}
