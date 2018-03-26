/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 * This class helps in generating test cases
 */
package pk.questlab.mbt.testgenerator;

import java.util.ArrayList;
import java.util.Vector;

import javax.xml.soap.SAAJResult;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.internal.metamodel.spec.DViewSpec;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.business.internal.metamodel.helper.DSemanticDiagramHelper;
import org.eclipse.sirius.viewpoint.DAnalysis;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.sirius.viewpoint.DView;
import org.eclipse.ui.internal.e4.compatibility.ModeledPageLayoutUtils;
import org.polarsys.capella.core.data.capellacommon.AbstractState;
import org.polarsys.capella.core.data.capellacommon.CapellacommonFactory;
import org.polarsys.capella.core.data.capellacommon.CapellacommonPackage;
import org.polarsys.capella.core.data.capellacommon.InitialPseudoState;
import org.polarsys.capella.core.data.capellacommon.Mode;
import org.polarsys.capella.core.data.capellacommon.Pseudostate;
import org.polarsys.capella.core.data.capellacommon.State;
import org.polarsys.capella.core.data.capellacommon.impl.CapellacommonFactoryImpl;
import org.polarsys.capella.core.data.capellacommon.impl.CapellacommonPackageImpl;
import org.polarsys.capella.core.data.capellacommon.impl.InitialPseudoStateImpl;
import org.polarsys.capella.core.data.capellacommon.impl.PseudostateImpl;
import org.polarsys.capella.core.data.capellacommon.impl.RegionImpl;
import org.polarsys.capella.core.data.capellacommon.util.CapellacommonResourceFactoryImpl;
import org.polarsys.capella.core.data.capellacommon.util.CapellacommonResourceImpl;
import org.polarsys.capella.core.data.capellacommon.util.CapellacommonSwitch;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.CapellacoreFactory;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.data.interaction.impl.ScenarioImpl;
import org.polarsys.capella.core.data.oa.impl.OperationalContextImpl;
import org.polarsys.capella.core.model.handler.validation.CapellaDiagnostician;

import pk.questlab.mbt.testgenerator.diagrams.DiagramResources;
import pk.questlab.mbt.testgenerator.diagrams.EPBSViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.LAViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.MBTViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.OAViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.PAViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.SAViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.helpers.ExchangeScenarioHelper;
import pk.questlab.mbt.testgenerator.diagrams.helpers.ModesStatesHelper;

public class GenerationHelper {
	private static GenerationHelper INSTANCE=null;
	ArrayList<DRepresentation>oa= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>sa= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>pa= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>la= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>epbs= new ArrayList<DRepresentation>();
	protected static ArrayList<MBTViewPoint> viewpoints= new ArrayList<MBTViewPoint>();
	public void initDiagramResources(Resource r)
	{
		DiagramResources.getInstance(r);
		DAnalysis danalysis=(DAnalysis) DiagramResources.getInstance().getResource().getContents().get(0);
		System.out.println("//Context Generation Helper");
		for(DView d:danalysis.getOwnedViews())
		{
			for(DRepresentationDescriptor drd:d.getOwnedRepresentationDescriptors())
			{
				if(((DViewSpec)(drd.eContainer())).getViewpoint().getName().toLowerCase().toString().equals(new String("common"))) // view point
				{
					String temp=((CapellaElement)drd.getTarget()).getFullLabel().toLowerCase(); 
					if(temp.contains("/operational analysis/"))
					{
						oa.add(drd.getRepresentation());
					}
					else if(temp.contains("/logical architecture/"))
					{
						la.add(drd.getRepresentation());
					}
					else if(temp.contains("/system analysis/"))
					{
						sa.add(drd.getRepresentation());
					}
					else if(temp.contains("/physical architecture/"))
					{
						pa.add(drd.getRepresentation());
					}
					else if(temp.contains("/epbs architecture/"))
					{
						epbs.add(drd.getRepresentation());
					}
				}
				else if(((DViewSpec)(drd.eContainer())).getViewpoint().getName().toLowerCase().toString().equals(new String("logical architecture"))) // view point
				{
					la.add(drd.getRepresentation());
				}
				else if(((DViewSpec)(drd.eContainer())).getViewpoint().getName().toLowerCase().toString().equals(new String("operational analysis"))) // view point
				{
					oa.add(drd.getRepresentation());
				}
				else if(((DViewSpec)(drd.eContainer())).getViewpoint().getName().toLowerCase().toString().equals(new String("system analysis"))) // view point
				{
					sa.add(drd.getRepresentation());
				}
				else if(((DViewSpec)(drd.eContainer())).getViewpoint().getName().toLowerCase().toString().equals(new String("physical architecture"))) // view point
				{
					pa.add(drd.getRepresentation());
				}
				else if(((DViewSpec)(drd.eContainer())).getViewpoint().getName().toLowerCase().toString().equals(new String("epbs architecture"))) // view point
				{
					epbs.add(drd.getRepresentation());
				}
			}
		}
		OAViewPoint capellaOA= new OAViewPoint(ECollections.asEList(oa));
		System.out.println("Number of OA diagrams: "+capellaOA.getOwnedDiagrams().size());
		for(DRepresentation d: capellaOA.getOwnedDiagrams())
		{
			System.out.println("-"+d.getName());
			//[h01]hard code for testing statemodes helper
			if(((DSemanticDiagram)d).getDescription().getName().toLowerCase().equals(new String("Modes & States").toLowerCase()))
			{
				RegionImpl diagramRoot=(RegionImpl) DSemanticDiagramHelper.getRootContent((DSemanticDiagram) d); // will always retuen only one region
				ModesStatesHelper.getInstance().init(diagramRoot);
				ModesStatesHelper.getInstance().prinModeState();
			}
			//[/h01]
		}
		SAViewPoint capellaSA= new SAViewPoint(ECollections.asEList(sa));
		System.out.println("Number of SA diagrams: "+capellaSA.getOwnedDiagrams().size());
		for(DRepresentation d: capellaSA.getOwnedDiagrams())
		{
			System.out.println("-"+d.getName());
		}
		LAViewPoint capellaLA= new LAViewPoint(ECollections.asEList(la));
		System.out.println("Number of LA diagrams: "+capellaLA.getOwnedDiagrams().size());
		for(DRepresentation d: capellaLA.getOwnedDiagrams())
		{
			System.out.println("-"+d.getName());
			//[h02] hard code for testing scenario and functional chain helper
			if(((DSemanticDiagram)d).getDescription().getName().toLowerCase().equals(new String("component exchanges scenario")))
			{
				Scenario diagramRoot=(Scenario) DSemanticDiagramHelper.getRootContent((DSemanticDiagram)d);
				ExchangeScenarioHelper.getInstance().init(diagramRoot);
				ExchangeScenarioHelper.getInstance().prinScenario();
			}
		}
		PAViewPoint capellaPA= new PAViewPoint(ECollections.asEList(pa));
		System.out.println("Number of PA diagrams: "+capellaPA.getOwnedDiagrams().size());
		for(DRepresentation d: capellaPA.getOwnedDiagrams())
		{
			System.out.println("-"+d.getName());
		}
		EPBSViewPoint capellaEPBS= new EPBSViewPoint(ECollections.asEList(epbs));
		System.out.println("Number of EPBS diagrams: "+capellaEPBS.getOwnedDiagrams().size());
		for(DRepresentation d: capellaEPBS.getOwnedDiagrams())
		{
			System.out.println("-"+d.getName());
		}
		viewpoints.add(capellaOA);
		viewpoints.add(capellaSA);
		viewpoints.add(capellaLA);
		viewpoints.add(capellaPA);
		viewpoints.add(capellaEPBS);
		capellaOA.dispose();
		capellaSA.dispose();
		capellaLA.dispose();
		capellaPA.dispose();
		capellaEPBS.dispose();
		System.out.println("================================================================================================");
	}
	public static void dispose()
	{
		viewpoints.clear();
	}
	public static GenerationHelper getInstance()
	{
		if(INSTANCE==null)
		{
			INSTANCE= new GenerationHelper();
			return INSTANCE;
		}
		else
		{
			return INSTANCE;
		}
	}
}
