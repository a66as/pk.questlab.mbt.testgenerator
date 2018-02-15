/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 * TODO
 */
package pk.questlab.capella.testgenerator;

import java.util.Collection;
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
 * @param TODO
 * Description: TODO
 * @return TODO
 * */
public static void init(String projectName)
{
	name=projectName;
	airdFile=projectName+".aird";
	melodyFile=projectName+".melodymodeller";
	metaFile=projectName+".afm";
	siriusSession=null;
	Collection<Session> sessions = SessionManager.INSTANCE.getSessions();
	System.out.println("Currently active sessions:");
	for(Session s:sessions)
	{
		if(s.getSessionResource().getURI().toString().endsWith(airdFile))
		{
			siriusSession=s;
		}
	}
	if(siriusSession==null)
	{
		System.out.println("[TestGenerator] Unable to create session for project: "+projectName);
	}
	else
	{
		System.out.println("[TestGenerator] AIRD Session created for project: "+projectName);
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
 * @param TODO
 * Description: TODO
 * @return TODO
 * */
public static String getAird()
{
	return airdFile;
}
/*
 * @param TODO
 * Description: TODO
 * @return TODO
 * */
public static String getMelody()
{
	return melodyFile;
}
/*
 * @param TODO
 * Description: TODO
 * @return TODO
 * */
public static String getMata()
{
	return metaFile;
}
}