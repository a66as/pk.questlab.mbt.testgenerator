/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 * This class helps in generating test cases
 */
package pk.questlab.mbt.testgenerator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.internal.metamodel.spec.DViewSpec;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.viewpoint.DAnalysis;
import org.eclipse.sirius.viewpoint.DRepresentationDescriptor;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.sirius.viewpoint.DView;
import org.polarsys.capella.core.data.oa.impl.OperationalContextImpl;

import pk.questlab.mbt.testgenerator.diagrams.DiagramResources;

public class GenerationHelper {
	private static GenerationHelper INSTANCE=null;
	public void initDiagramResources(Resource r)
	{
		DiagramResources.getInstance(r);
		DAnalysis danalysis=(DAnalysis) DiagramResources.getInstance().getResource().getContents().get(0);
		for(DView d:danalysis.getOwnedViews())
		{
			for(DRepresentationDescriptor drd:d.getOwnedRepresentationDescriptors())
			{
				System.out.println(((DViewSpec)(drd.eContainer())).getViewpoint().getName());
				System.out.println("-->"+drd.getRepresentation().getName()+"/"+drd.getDescription().getLabel()+"/"+drd.getDescription().getTitleExpression());
				
				//for(EObject e:drd.getRepresentation().getRepresentationElements())
					//System.out.println("---->"+((DRepresentationElement) e).getName());
			}
		}
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
