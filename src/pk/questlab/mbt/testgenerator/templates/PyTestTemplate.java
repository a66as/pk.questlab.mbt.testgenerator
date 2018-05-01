package pk.questlab.mbt.testgenerator.templates;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.BodyDeclaration;

public class PyTestTemplate {
private String testName;
private String testClassifier;
private ArrayList<String> testBody;
/*
 * @param String t_name the name of testcase without spaces and underscores
 * @param String t_classifier name of test suite to which the testcase is associated
 * @param ArrayList<String> t_body the lines array of the body with out ";"s and "\n"s
 */
	public PyTestTemplate(String t_name, String t_classifier, ArrayList<String> t_body)
	{
		testName=t_name;
		testClassifier=t_classifier;
		testBody=t_body;
	}
	public String getExecutableTestText()
	{
		String text="\n#Testcase "+testName+" for "+testClassifier+"\n";
		text+="\tdef"+" test_"+testName+"(self):\n";
		for(String line:testBody)
		{
			text+="\t\t"+line+"\n";
		}
		return text;
	}
}
