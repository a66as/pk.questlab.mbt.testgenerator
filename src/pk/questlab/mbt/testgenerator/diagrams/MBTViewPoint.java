package pk.questlab.mbt.testgenerator.diagrams;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.viewpoint.DRepresentation;

public class MBTViewPoint {
	protected static EList<DRepresentation>commonDiagrams;
	protected MBTViewPoint()
	{
		
	}
	public MBTViewPoint(EList<DRepresentation> d)
	{
		commonDiagrams=d;
	}
	public EList getCommonDiagrams()
	{
		return commonDiagrams;
	}
}
