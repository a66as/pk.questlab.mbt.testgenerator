/**
 * @author Muhammad Abbas
 * (c) 2018, QUEST Lab, Pakistan
 * @version 0.1
 * This class Handles the UI input from the tester's menu in the eclipse application
 */
package pk.questlab.mbt.handlers;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;

import pk.questlab.mbt.testgenerator.ProjectHelper;

public class ApplicationUIHandler {
	/*
	 * Description: This method initializes the UI for projectName input and sends it to the ProjectHelper.init
	 * */
	public static void interfaceInit()
	{
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		ArrayList<String>projectsList=  new ArrayList<String>();
		for(IProject p:projects)
		{
			projectsList.add(p.getName());
		}
		JFrame f= new JFrame();
		JLabel notification= new JLabel("No projects in workspace!");
		JDialog projectsDialog;
		JComboBox<String> projectsDropDown= new JComboBox<String>();
		for(String s:projectsList)
		{
			projectsDropDown.addItem(s);
		}
		projectsDialog = new JDialog(f , "Select a Project", true); 
		projectsDialog.setLayout( new FlowLayout() );  
        projectsDialog.add(projectsDropDown);
        JButton doneBtn = new JButton ("Generate"); 
        doneBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!projectsDropDown.getSelectedItem().toString().isEmpty())
				{
					for(IProject p:projects)
					{
						if(p.getName().equals(projectsDropDown.getSelectedItem().toString()))
						{
							projectsDialog.setVisible(false);
							System.out.println("================================================================================================");
							System.out.println("//Context UI Handler");
							System.out.println("[TestGen Info] Selected Project:"+projectsDropDown.getSelectedItem().toString());
							ProjectHelper.init(projectsDropDown.getSelectedItem().toString(),p);
						}
					}
					
				}
			}
		});
        projectsDialog.add(doneBtn); 
        notification.setLocation(0, 15);
        if(projectsList.isEmpty() || projectsList.size()==0)
        	projectsDialog.add(notification);
        projectsDialog.setSize(330,100);    
        projectsDialog.setVisible(true);	
	}
}
