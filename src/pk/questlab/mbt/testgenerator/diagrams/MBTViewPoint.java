package pk.questlab.mbt.testgenerator.diagrams;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public abstract class MBTViewPoint {
	private EList<EObject>ownedDiagrams;
	public abstract void printViewPoint();
	public MBTViewPoint(EList diagrams)
	{
		
	}
	public EList getOwnedDiagrams()
	{
		return ownedDiagrams;
	}
}
