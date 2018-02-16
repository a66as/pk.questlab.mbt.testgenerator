/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 * This class holds the extracted data of diagrams.
 */
package pk.questlab.mbt.testgenerator.diagrams;

import org.eclipse.emf.ecore.resource.Resource;

public class DiagramResources {
	private static Resource modelResource;
	private static DiagramResources INSTANCE=null;
	private DiagramResources(Resource r)
	{
		modelResource=r;
	}
	public static DiagramResources getInstance(Resource r)
	{
		if(INSTANCE==null)
		{
			INSTANCE= new DiagramResources(r);
			return INSTANCE;
		}
		else
		{
			INSTANCE.setResource(r);
			return INSTANCE;
		}
	}
	public static DiagramResources getInstance()
	{
		return INSTANCE;
	}
	public void setResource(Resource r)
	{
		INSTANCE.modelResource=r;
	}
	public Resource getResource()
	{
		return INSTANCE.modelResource;
	}
}
