
package pk.questlab.mbt.testgenerator.diagrams.helpers;

import org.polarsys.capella.core.data.interaction.AbstractEnd;
import org.polarsys.capella.core.data.interaction.InstanceRole;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.core.data.interaction.SequenceMessage;
import org.polarsys.capella.core.data.interaction.SequenceMessageValuation;

/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 */
public class ExchangeScenarioHelper {
	private static ExchangeScenarioHelper INSTANCE;
	private static Scenario root;
	private static InstanceRole callInitiator; // first call initiator
	private ExchangeScenarioHelper()
	{
		
	}
	public static void init(Scenario droot)
	{
		root=droot;
		for(InstanceRole ir:root.getOwnedInstanceRoles())
		{
			if(ir.getAbstractEnds().size()>0)
			{
				AbstractEnd tempEnd=ir.getAbstractEnds().get(0);
				if(tempEnd.getName().toLowerCase().equals(new String("send call message call")))
				{
					callInitiator=ir;
				}
			}
		}
	}
	public static ExchangeScenarioHelper getInstance()
	{
		if(INSTANCE==null)
		{
			INSTANCE= new ExchangeScenarioHelper();
		}
		return INSTANCE;
	}
	/*
	 * @param 
	 * Description: 
	 * Dependency: 
	 * */
	public void prinScenario()
	{
		if(callInitiator!=null) // have some start
		{
			for(SequenceMessage sm:root.getOwnedMessages())
			{
				// Part - Functional Exchange -> Part
				System.out.println(sm.getSendingPart().getLabel()+"-- "+sm.getInvokedOperation().getName()+" --> "+sm.getReceivingPart().getLabel());
			}
		}
	}
}
