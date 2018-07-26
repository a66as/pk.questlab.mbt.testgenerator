#Auto generated PyUnitTests for BackToLaunch

import unittest
import time
from dronekit import connect, VehicleMode, LocationGlobalRelative
connection_string= "127.0.0.1:14552"
vehicle=connect(connection_string, wait_ready=True)
class TestBackToLaunch(unittest.TestCase):
#Testcase armTest for BackToLaunch
	def test_armTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = True
		time.sleep(4)
		self.assertTrue(vehicle.armed == True)

#Testcase flytolocationTest for BackToLaunch
	def test_flytolocationTest(self):
		vehicle.simple_takeoff(20)
		time.sleep(22)
		vehicle.simple_goto(LocationGlobalRelative(-35.361354, 149.165218, 20)) 
		time.sleep(20)

#Testcase returntolaunchlocationTest for BackToLaunch
	def test_returntolaunchlocationTest(self):
		vehicle.mode    = VehicleMode("RTL")
		time.sleep(22)
		self.assertTrue(vehicle.mode.name == "RTL")

suite = unittest.TestLoader().loadTestsFromTestCase(TestBackToLaunch)
unittest.TextTestRunner(verbosity=2).run(suite)
vehicle.close()
