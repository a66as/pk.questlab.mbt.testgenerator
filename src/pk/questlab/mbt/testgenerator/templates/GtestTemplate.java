package pk.questlab.mbt.testgenerator.templates;

import java.util.ArrayList;

public class GtestTemplate {
private String cutInclude;
private String suiteName;
private ArrayList<String> includes;
private String gtestInclude="#include \"gtest/gtest.h\"";
private ArrayList<TESTTemplate> testCases;
	/*
	 * @param String cut class under test
	 * @param ArrayList<String> incs includes the test is going to use excluding gtest and cut include
	 * @param ArrayList<TESTTemplate> list of test methods
	 */
	public GtestTemplate(String cut, ArrayList<String> incs, ArrayList<TESTTemplate> tests)
	{
		cutInclude="#include \""+cut+".h\"";
		includes = incs;
		testCases=tests;
		suiteName=cut;
		
	}
	/*
	 * This methods uses the private attributes of this class and returns a text to be written into a file
	 * @return String executable Gtest class text
	 */
	public String getExecutableSuiteText()
	{
		//assumes the following template
		/*// Auto generated GTest
		 * 	cutInclude
			gtestInclude
			includes
			namespace {
			testCases
			}
		 */
		String text="//Auto generated GTest for "+suiteName+"\n";
		text+="\n"+gtestInclude+"\n";
		for(String inc:includes)
		{
			text+=inc+"\n";
		}
		text+="\nnamespace\n{";
		for(TESTTemplate test:testCases)
		{
			text+=test.getExecutableTestText();
		}
		text+="\n}";
		return text;
	}
	public String getSuiteName()
	{
		return suiteName;
	}
}
