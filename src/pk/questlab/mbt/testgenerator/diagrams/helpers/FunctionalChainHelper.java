package pk.questlab.mbt.testgenerator.diagrams.helpers;

import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;

public class FunctionalChainHelper {
	private static FunctionalChainHelper INSTANCE;
	private static FunctionalChain root;
	private FunctionalChainHelper()
	{
		
	}
	public static void init(FunctionalChain mroot)
	{
		root=mroot;
	}
	public static FunctionalChainHelper getInstance()
	{
		if(INSTANCE==null)
		{
			INSTANCE= new FunctionalChainHelper();
		}
		return INSTANCE;
	}
	/*
	 * @param 
	 * Description: 
	 * Dependency: 
	 * */
	public void printFunctionalChain()
	{
		// format: Function ---> Arrow Label
		//         ArrowLabel ---> TargetedArrow(s)/Functions
		for(FunctionalChainInvolvement next:root.getFirstFunctionalChainInvolvements())
		{
			System.out.print(next.getInvolved().getLabel());
			System.out.println();
			printFunctionRecursively(next);
		}
	}
	public void printFunctionRecursively(FunctionalChainInvolvement toprint)
	{
		if(toprint!=null)
		{
			System.out.print(toprint.getInvolved().getLabel()+" ---> ");
			if(toprint.getNextFunctionalChainInvolvements().size()==0)
			{
				System.out.println();
				return;
			}
			else
			{
				for(FunctionalChainInvolvement next:toprint.getNextFunctionalChainInvolvements())
				{
					System.out.print(next.getInvolved().getLabel()+",");
				}
				System.out.println();
				for(FunctionalChainInvolvement next:toprint.getNextFunctionalChainInvolvements())
				{
					printFunctionRecursively(next);
				}
				
			}
		}
	}
}
