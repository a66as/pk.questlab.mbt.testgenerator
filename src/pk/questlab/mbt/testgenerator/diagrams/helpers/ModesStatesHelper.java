/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 */
package pk.questlab.mbt.testgenerator.diagrams.helpers;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.common.data.behavior.AbstractEvent;
import org.polarsys.capella.core.data.capellacommon.AbstractState;
import org.polarsys.capella.core.data.capellacommon.StateTransition;
import org.polarsys.capella.core.data.capellacommon.impl.InitialPseudoStateImpl;
import org.polarsys.capella.core.data.capellacommon.impl.RegionImpl;

public class ModesStatesHelper {
	private static ModesStatesHelper INSTANCE;
	private static RegionImpl root;
	private ModesStatesHelper()
	{
		
	}
	public static void init(RegionImpl region)
	{
		root=region;
	}
	public static ModesStatesHelper getInstance()
	{
		if(INSTANCE==null)
		{
			INSTANCE= new ModesStatesHelper();
		}
		return INSTANCE;
	}
	/*
	 * @param InitialPseudoStateImpl alpha 
	 * Description: This function uses the alpha and send it to printStateRecursively to print data about each state
	 * Dependency: printStateRecursively(AbstractState, ArrayList)
	 * */
	public void prinModeState()
	{
		// Visited list to avoid loops in Diagram
		ArrayList<AbstractState> visited= new ArrayList<AbstractState>();
		// Prints states recursively
		for(AbstractState as:root.getOwnedStates())
		{
			if(as instanceof InitialPseudoStateImpl)
			{
				InitialPseudoStateImpl alpha= (InitialPseudoStateImpl)as;
				printStateRecursively(alpha, visited);
			}
		}
	}
	/*
	 * @param AbstractState state
	 * @param ArrayList visited_states 
	 * Description: This function prints state and transition and call it self recursively
	 * Dependency: self, printTransition(StateTransition)
	 * */
	private static void printStateRecursively(AbstractState state, ArrayList visited)
	{
		if(state==null)
			return;
		if(visited.contains(state))
			return;
		System.out.println(state.getName());
		visited.add(state);
		for(StateTransition transition:state.getOutgoing())
		{
			printTransition(transition);
		}
		for(StateTransition transition:state.getOutgoing())
		{
			printStateRecursively(transition.getTarget(),visited);
		}
	}
	/*
	 * @param StateTransition t
	 * Description: This function prints data of a transition
	 * */
	private static void printTransition(StateTransition t)
	{
		if(t==null)
			return;
		String result="|__ ";
		result+=t.getName()+" "+t.getTriggerDescription();
		if(t.getTriggers()!=null && t.getTriggers().size()>0)
		{
			for(AbstractEvent trigger:t.getTriggers())
			{
				result+=trigger.getName();
			}
		}
		result+=" / ";
		if(!t.getEffect().isEmpty())
		{
			for(AbstractEvent effect:t.getEffect())
			{
				result+=effect;
			}
		}
		result+="/";
		result+=" ["+t.getGuard()+"]";
		result+=" -->"+t.getTarget().getName();
		System.out.println(result);
		result="";
	}
}
