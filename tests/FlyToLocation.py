#Auto generated PyUnitTests for FlyToLocation

import unittest
import time
from dronekit import connect, VehicleMode, LocationGlobalRelative
connection_string= "127.0.0.1:14552"
vehicle=connect(connection_string, wait_ready=True)
class TestFlyToLocation(unittest.TestCase):
#Testcase armTest for FlyToLocation
	def test_armTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = True
		time.sleep(3)
		self.assertTrue(vehicle.armed ==True)

#Testcase takeoffTest for FlyToLocation
	def test_takeoffTest(self):
		vehicle.simple_takeoff(20)

#Testcase flytolocationTest for FlyToLocation
	def test_flytolocationTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = True
		time.sleep(3)
		vehicle.simple_takeoff(20)
		time.sleep(40)
		vehicle.simple_goto(LocationGlobalRelative(-35.361354, 149.165218, 20)) 
		

suite = unittest.TestLoader().loadTestsFromTestCase(TestFlyToLocation)
unittest.TextTestRunner(verbosity=2).run(suite)
vehicle.close()
