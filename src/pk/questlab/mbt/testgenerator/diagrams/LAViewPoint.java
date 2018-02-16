package pk.questlab.mbt.testgenerator.diagrams;

import org.eclipse.emf.common.util.EList;

public class LAViewPoint extends MBTViewPoint {

	public LAViewPoint(EList diagrams) {
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
