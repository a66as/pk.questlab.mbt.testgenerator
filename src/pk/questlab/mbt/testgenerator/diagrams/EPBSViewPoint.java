package pk.questlab.mbt.testgenerator.diagrams;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public class EPBSViewPoint extends MBTViewPoint {

	public EPBSViewPoint(EList diagrams) {
		super(diagrams);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void printViewPoint() {
		System.out.println("[TestGen Info] Viewpoint: EPBS");
		for(Object diagramObj:getOwnedDiagrams())
		{
			System.out.println(diagramObj.getClass());
		}
	}

}
