package pk.questlab.mbt.testgenerator.diagrams;

import org.eclipse.emf.common.util.EList;

public class PAViewPoint extends MBTViewPoint {

	public PAViewPoint(EList diagrams) {
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
