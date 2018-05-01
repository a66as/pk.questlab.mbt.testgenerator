/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 * This class extracts all the project models and populates the diagrams pkg.
 */
package pk.questlab.mbt.testgenerator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.impl.DSemanticDiagramImpl;

import pk.questlab.mbt.testgenerator.templates.GtestTemplate;
import pk.questlab.mbt.testgenerator.templates.PySuiteTemplate;
import pk.questlab.mbt.testgenerator.templates.PyTestTemplate;

public class ProjectHelper {
private static String name;
private static String airdFile;
private static String melodyFile;
private static String metaFile;
private static Session siriusSession;
private static IProject project;
/*
 * @param projectName from the UI
 * Description: This function initiates the project and extract its data
 * */
@SuppressWarnings("deprecation")
public static void init(String projectName, IProject iProject)
{
	System.out.println("// Context projectHelper::init");
	name=projectName;
	airdFile=projectName+".aird";
	melodyFile=projectName+".melodymodeller";
	metaFile=projectName+".afm";
	project=iProject;
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
public static boolean addToProject(GtestTemplate suite)
{
	IFolder folder = project.getFolder("test");
	
	try {
		project.refreshLocal(2, new NullProgressMonitor());
	} catch (CoreException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		folder.refreshLocal(2, new NullProgressMonitor());
	} catch (CoreException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	if (!folder.exists())
		try {
			folder.create(IResource.NONE, true, null);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IFile file = folder.getFile(suite.getSuiteName()+".cc");
		if (!file.exists()) {
		    byte[] bytes = suite.getExecutableSuiteText().getBytes();
		    InputStream source = new ByteArrayInputStream(bytes);
		    try {
				file.create(source, IResource.NONE, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return true;
		}
		else{
			byte[] bytes = suite.getExecutableSuiteText().getBytes();
			InputStream source = new ByteArrayInputStream(bytes);
			try {
				file.delete(true, new NullProgressMonitor());
				file.create(source, IResource.NONE, null);
				return true;
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	return false;
}
public static boolean addToProject(PySuiteTemplate suite) {
	IFolder folder = project.getFolder("test");
	
	try {
		project.refreshLocal(2, new NullProgressMonitor());
	} catch (CoreException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		folder.refreshLocal(2, new NullProgressMonitor());
	} catch (CoreException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	if (!folder.exists())
		try {
			folder.create(IResource.NONE, true, null);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IFile file = folder.getFile(suite.getSuiteName()+".py");
		if (!file.exists()) {
		    byte[] bytes = suite.getExecutableSuiteText().getBytes();
		    InputStream source = new ByteArrayInputStream(bytes);
		    try {
				file.create(source, IResource.NONE, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return true;
		}
		else{
			byte[] bytes = suite.getExecutableSuiteText().getBytes();
			InputStream source = new ByteArrayInputStream(bytes);
			try {
				file.delete(true, new NullProgressMonitor());
				file.create(source, IResource.NONE, null);
				return true;
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	return false;
	
}
}