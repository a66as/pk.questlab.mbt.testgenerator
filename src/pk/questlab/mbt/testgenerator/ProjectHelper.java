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
	System.out.println("// Context projectHelper::init");
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
		GenerationHelper.getInstance().initDiagramResources(siriusSession.getSessionResource());
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