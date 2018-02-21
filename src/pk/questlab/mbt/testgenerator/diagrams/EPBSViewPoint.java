package pk.questlab.mbt.testgenerator.diagrams;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.viewpoint.DRepresentation;

public class EPBSViewPoint extends MBTViewPoint {

	private EList<DRepresentation> ownedDiagrams;
	public EPBSViewPoint(EList<DRepresentation> diagrams) {
		ownedDiagrams=diagrams;
	}
	public void printViewPoint() {
		System.out.println("[TestGen Info] Viewpoint: EPBS");
		for(Object diagramObj:ownedDiagrams)
		{
			System.out.println(diagramObj.getClass());
		}
	}
	public EList<DRepresentation> getOwnedDiagrams()
	{
		return ownedDiagrams;
	}
	public void dispose()
	{
		ownedDiagrams.clear();
	}

}
