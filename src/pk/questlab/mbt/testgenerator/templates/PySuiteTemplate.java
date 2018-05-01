package pk.questlab.mbt.testgenerator.templates;

import java.util.ArrayList;

public class PySuiteTemplate {
private String cutInclude;
private String suiteName;
private ArrayList<String> includes;
private String pyunittestInclude="import unittest";
private ArrayList<PyTestTemplate> testCases;
	/*
	 * @param String cut class under test
	 * @param ArrayList<String> incs includes the test is going to use excluding gtest and cut include
	 * @param ArrayList<TESTTemplate> list of test methods
	 */
	/*
	 * #print "Start simulator (SITL)"
connection_string = "127.0.0.1:14552"

# Import DroneKit-Python
from dronekit import connect, VehicleMode
import time
# Import UnitTest-Python
import unittest
# Connect to the Vehicle.
#print("Connecting to vehicle on: %s" % (connection_string,))
vehicle=connect(connection_string, wait_ready=True)
class TestScenario(unittest.TestCase):
    def test_arm_and_takeoff(self):
    	while not vehicle.is_armable:
        	#print "Waiting for vehicle to initialise..."
        	time.sleep(1)

    	#print "Arming motors"
    	# Copter should arm in GUIDED mode
    	vehicle.mode    = VehicleMode("GUIDED")
    	vehicle.armed   = True

    	# Confirm vehicle armed before attempting to take off
    	while not vehicle.armed:
        	print "Waiting for arming..."
        	time.sleep(1)

    	print "Taking off!"
    	vehicle.simple_takeoff(20) # Take off to target altitude
		#Wait until the vehicle reaches a safe height before processing the goto (otherwise the command
		#after Vehicle.simple_takeoff will execute immediately).
        while True:
			#print "Altitude: ", vehicle.location.global_relative_frame.alt
			#Break and return from function just below target altitude.
			if vehicle.location.global_relative_frame.alt>=20*0.95:
			   #print "Reached target altitude"
		    		break
		 	time.sleep(1)
        self.assertTrue(vehicle.location.global_relative_frame.alt>17)
    def test_auto_land(self):
    	vehicle.mode = VehicleMode("LAND")
    	time.sleep(12)
	print "%s" % vehicle.location.global_relative_frame.alt
    	self.assertTrue(vehicle.location.global_relative_frame.alt<10)
# Get some vehicle attributes (state)
#print "Get some vehicle attribute values:"
#print " GPS: %s" % vehicle.gps_0
#print " Battery: %s" % vehicle.battery
#print " Last Heartbeat: %s" % vehicle.last_heartbeat
#print " Is Armable?: %s" % vehicle.is_armable
#print " System status: %s" % vehicle.system_status.state
#print " Mode: %s" % vehicle.mode.name    # settable
#running the takeoff test
#print "ALT: %s" % vehicle.location.global_relative_frame.alt
#assert (vehicle.location.global_relative_frame.alt > 18)
#vehicle.mode    = VehicleMode("LAND")
#time.sleep(30)
#assert (vehicle.location.global_relative_frame.alt < 3)
#print "ALT: %s" % vehicle.location.global_relative_frame.alt
# Close vehicle object before exiting script
#print("Completed")
suite = unittest.TestLoader().loadTestsFromTestCase(TestScenario)
unittest.TextTestRunner(verbosity=2).run(suite)
vehicle.close()
	 */
	public PySuiteTemplate(String cut, ArrayList<String> incs, ArrayList<PyTestTemplate> testMethods)
	{
		cutInclude="from dronekit import connect, VehicleMode, LocationGlobalRelative ";
		includes = incs;
		incs.add("import time");
		incs.add("from dronekit import connect, VehicleMode"); // for this case
		incs.add("vehicle=connect(connection_string, wait_ready=True)"); // for this case
		testCases=testMethods;
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
		String text="#Auto generated PyUnitTests for "+suiteName+"\n";
		text+="\n"+pyunittestInclude+"\n";
		for(String inc:includes)
		{
			text+=inc+"\n";
		}
		text+="class Test"+suiteName+"(unittest.TestCase):";
		for(PyTestTemplate test:testCases)
		{
			text+=test.getExecutableTestText();
		}
		text+="\nsuite = unittest.TestLoader().loadTestsFromTestCase(Test"+suiteName+")\n";
		text+="unittest.TextTestRunner(verbosity=2).run(suite)\n";
		// for this case only
		text+="vehicle.close()";
		return text;
	}
	public String getSuiteName()
	{
		return suiteName;
	}
}

