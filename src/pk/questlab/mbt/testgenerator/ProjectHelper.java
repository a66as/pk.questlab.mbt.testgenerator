/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 * This class extracts all the project models and populates the diagrams pkg.
 */
package pk.questlab.mbt.testgenerator;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.impl.DSemanticDiagramImpl;

public class ProjectHelper {
private static String name;
private static String airdFile;
private static String melodyFile;
private static String metaFile;
private static Session siriusSession;
/*
 * @param projectName from the UI
 * Description: This function initiates the project and extract its data
 * */
@SuppressWarnings("deprecation")
public static void init(String projectName)
{
	name=projectName;
	airdFile=projectName+".aird";
	melodyFile=projectName+".melodymodeller";
	metaFile=projectName+".afm";
	siriusSession=SessionManager.INSTANCE.getSession(URI.createPlatformResourceURI("/"+name+"/"+airdFile), new NullProgressMonitor());
	if(siriusSession==null)
	{
		System.out.println("[TestGen Error] Unable to create session for project: "+projectName);
	}
	else
	{
		System.out.println("[TestGen Info] AIRD Session created for project: "+projectName);
		System.out.println("Session Resource Have the following contents:");
		for(EObject e: siriusSession.getSessionResource().getContents())
		{
			System.out.println(e.eClass());
			if(e instanceof DSemanticDiagramImpl || e instanceof DSemanticDiagram)
			{
				System.out.println("Diagram: "+((DSemanticDiagramImpl)e).getName());
			}
		}
		//DAnalysisImpl root=(DAnalysisImpl) siriusSession.getSessionResource().getContents().get(0);
		//DViewImpl dView=(DViewImpl) root.getOwnedViews().get(0);
		//DRepresentation myRepresentation = dView.getOwnedRepresentationDescriptors().get(0).getRepresentation();
		//System.out.println(myRepresentation.getName());
		//TreeIterator<EObject> melodyIterator= capellaProject.getOwnedModelRoots().get(0).eAllContents();
		//while(melodyIterator.hasNext())
		//{
			//EObject next= melodyIterator.next();
				//System.out.println(next.eClass().getName());
		//}
	}
	
}
/*
 * @return airdFile the name of aird file with extension
 * */
public static String getAird()
{
	return airdFile;
}
/*
 * @return melodyFile the name of melodymodeller file with extension
 * */
public static String getMelody()
{
	return melodyFile;
}
/*
 * @return afmFile the name of metadata file with extension
 * */
public static String getMata()
{
	return metaFile;
}
}