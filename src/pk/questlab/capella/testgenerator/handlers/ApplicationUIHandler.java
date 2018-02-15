/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 * This class Handles the UI input from the tester's menu in the eclipse application
 */
package pk.questlab.capella.testgenerator.handlers;

import java.awt.GridLayout;
import pk.questlab.capella.testgenerator.ProjectHelper;
import javax.swing.*;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;

public class ApplicationUIHandler {
	/*
	 * @param TODO
	 * Description: TODO
	 * @return TODO
	 * */
	public static void interfaceInit()
	{
		JTextField projectName= new JTextField();
		JPanel projectPanel=new JPanel(new GridLayout(0,1));
		projectPanel.add(new JLabel("Project Name"));
		String panelResult= JOptionPane.showInputDialog(null, projectPanel, "Test Generator");
		ProjectHelper.init(panelResult);
	}
}
