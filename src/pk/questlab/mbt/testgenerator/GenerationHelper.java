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

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.internal.metamodel.spec.DViewSpec;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.viewpoint.DAnalysis;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.sirius.viewpoint.DView;
import org.polarsys.capella.core.data.oa.impl.OperationalContextImpl;

import pk.questlab.mbt.testgenerator.diagrams.DiagramResources;
import pk.questlab.mbt.testgenerator.diagrams.EPBSViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.LAViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.MBTViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.OAViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.PAViewPoint;
import pk.questlab.mbt.testgenerator.diagrams.SAViewPoint;

public class GenerationHelper {
	private static GenerationHelper INSTANCE=null;
	ArrayList<DRepresentation>common= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>oa= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>sa= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>pa= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>la= new ArrayList<DRepresentation>();
	ArrayList<DRepresentation>epbs= new ArrayList<DRepresentation>();
	public void initDiagramResources(Resource r)
	{
		DiagramResources.getInstance(r);
		DAnalysis danalysis=(DAnalysis) DiagramResources.getInstance().getResource().getContents().get(0);
		for(DView d:danalysis.getOwnedViews())
		{
			for(DRepresentationDescriptor drd:d.getOwnedRepresentationDescriptors())
			{
				if(((DViewSpec)(drd.eContainer())).getViewpoint().getName().toLowerCase().toString().equals(new String("common"))) // view point
				{
					common.add(drd.getRepresentation());
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
				//System.out.println(((DViewSpec)(drd.eContainer())).getViewpoint().getName().toLowerCase().toString());
				//System.out.println(drd.getDescription().getName()); // full type in capella
				//System.out.println("-->"+drd.getRepresentation().getName()+"/"+drd.getDescription().getLabel()); // diagram name
			}
		}
		MBTViewPoint capellaCommon= new MBTViewPoint((ECollections.asEList(common)));
		System.out.println("Number of common diagrams: "+capellaCommon.getCommonDiagrams().size());
		SAViewPoint capellaSA= new SAViewPoint(ECollections.asEList(sa));
		System.out.println("Number of system diagrams: "+capellaSA.getOwnedDiagrams().size());
		OAViewPoint capellaOA= new OAViewPoint(ECollections.asEList(oa));
		System.out.println("Number of operational diagrams: "+capellaOA.getOwnedDiagrams().size());
		LAViewPoint capellaLA= new LAViewPoint(ECollections.asEList(la));
		System.out.println("Number of logical diagrams: "+capellaLA.getOwnedDiagrams().size());
		PAViewPoint capellaPA= new PAViewPoint(ECollections.asEList(pa));
		System.out.println("Number of physical diagrams: "+capellaPA.getOwnedDiagrams().size());
		EPBSViewPoint capellaEPBS= new EPBSViewPoint(ECollections.asEList(epbs));
		System.out.println("Number of epbs diagrams: "+capellaEPBS.getOwnedDiagrams().size());
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
