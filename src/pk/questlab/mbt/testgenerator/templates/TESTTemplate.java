package pk.questlab.mbt.testgenerator.templates;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.BodyDeclaration;

public class TESTTemplate {
private String testName;
private String testClassifier;
private ArrayList<String> testBody;
/*
 * @param String t_name the name of testcase without spaces and underscores
 * @param String t_classifier name of test suite to which the testcase is associated
 * @param ArrayList<String> t_body the lines array of the body with out ";"s and "\n"s
 */
	public TESTTemplate(String t_name, String t_classifier, ArrayList<String> t_body)
	{
		testName=t_name;
		testClassifier=t_classifier;
		testBody=t_body;
	}
	public String getExecutableTestText()
	{
		// Assumes the following template
		/*//Testcase testName for testClassifier
		 * TEST(testClassifier, testName) {
		    testBody
		}*/
		String text="\n   //Testcase "+testName+" for "+testClassifier+"\n";
		text+="   TEST("+testClassifier+", "+testName+")\n   {\n";
		for(String line:testBody)
		{
			if(line.endsWith("{") || line.endsWith("}"))
				text+="      "+line+"\n";
			else
				text+="      "+line+";\n";
		}
		text+="   }\n";
		return text;
	}
}
