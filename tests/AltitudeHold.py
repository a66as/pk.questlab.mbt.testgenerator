#Auto generated PyUnitTests for AltitudeHold

import unittest
import time
from dronekit import connect, VehicleMode
connection_string= "127.0.0.1:14552"
vehicle=connect(connection_string, wait_ready=True)
class TestAltitudeHold(unittest.TestCase):
#Testcase armTest for AltitudeHold
	def test_armTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = True
		time.sleep(5)
		self.assertTrue(vehicle.armed   == True)

#Testcase takeoffTest for AltitudeHold
	def test_takeoffTest(self):
		vehicle.simple_takeoff(20)
		time.sleep(22)
		self.assertTrue(vehicle.location.global_relative_frame.alt>19)

#Testcase holdaltitudeTest for AltitudeHold
	def test_holdaltitudeTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = True
		vehicle.simple_takeoff(20) 
		time.sleep(25) # waiting for alt hold
		self.assertTrue(vehicle.location.global_relative_frame.alt>18)
		self.assertTrue(vehicle.location.global_relative_frame.alt<21)

#Testcase autolandTest for AltitudeHold
	def test_autolandTest(self):
		vehicle.mode    = VehicleMode("LAND")
		time.sleep(22)
		self.assertTrue(vehicle.location.global_relative_frame.alt<1)

suite = unittest.TestLoader().loadTestsFromTestCase(TestAltitudeHold)
unittest.TextTestRunner(verbosity=2).run(suite)
vehicle.close()
