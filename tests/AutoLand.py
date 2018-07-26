#Auto generated PyUnitTests for AutoLand

import unittest
import time
from dronekit import connect, VehicleMode
connection_string= "127.0.0.1:14552"
vehicle=connect(connection_string, wait_ready=True)
class TestAutoLand(unittest.TestCase):
#Testcase armTest for AutoLand
	def test_armTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = True
		time.sleep(3)
		self.assertTrue(vehicle.armed == True)

#Testcase takeoffTest for AutoLand
	def test_takeoffTest(self):
		vehicle.simple_takeoff(20)
		time.sleep(22)
		self.assertTrue(vehicle.location.global_relative_frame.alt>19)

#Testcase autolandTest for AutoLand
	def test_autolandTest(self):
		vehicle.mode = VehicleMode("LAND")
		time.sleep(25)
		self.assertTrue(vehicle.location.global_relative_frame.alt<1)

suite = unittest.TestLoader().loadTestsFromTestCase(TestAutoLand)
unittest.TextTestRunner(verbosity=2).run(suite)
vehicle.close()
