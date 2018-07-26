#Auto generated PyUnitTests for SneakPath

import unittest
import time
from dronekit import connect, VehicleMode, LocationGlobalRelative
connection_string= "127.0.0.1:14552"
vehicle=connect(connection_string, wait_ready=True)
class TestSneakPath(unittest.TestCase):

#Testcase FLYINGdisarmSneakTest for SneakPath
	def test_FLYINGdisarmSneakTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = True
		time.sleep(2)
		vehicle.simple_takeoff(20)
		time.sleep(22)
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = False
		time.sleep(2)
		self.assertFalse(vehicle.armed== False)

#Testcase DISARMEDtakeoffSneakTest for SneakPath
	def test_DISARMEDtakeoffSneakTest(self):
		vehicle.armed   = False
		time.sleep(2)
		vehicle.simple_takeoff(20)
		time.sleep(22)
		self.assertFalse(vehicle.location.global_relative_frame.alt<19)

#Testcase DISARMEDflytolocationSneakTest for SneakPath
	def test_DISARMEDflytolocationSneakTest(self):
		vehicle.armed   = False
		time.sleep(3)
		vehicle.simple_goto(LocationGlobalRelative(-35.361354, 149.165218, 20)) 
		time.sleep(10)
		self.assertFalse(vehicle.armed   == False)

#Testcase DISARMEDreturntolaunchlocationSneakTest for SneakPath
	def test_DISARMEDreturntolaunchlocationSneakTest(self):
		vehicle.armed   = False
		time.sleep(3)
		vehicle.mode    = VehicleMode("RTL")
		time.sleep(5)

#Testcase DISARMEDholdaltitudeSneakTest for SneakPath
	def test_DISARMEDholdaltitudeSneakTest(self):
		vehicle.armed   = False
		time.sleep(2)
		vehicle.simple_takeoff(20)
		time.sleep(10)
		self.assertFalse(vehicle.location.global_relative_frame.alt<10)

#Testcase ALTHOLDtakeoffSneakTest for SneakPath
	def test_ALTHOLDtakeoffSneakTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed = True
		vehicle.simple_takeoff(20)
		time.sleep(22)
		time.sleep(2)
		vehicle.simple_takeoff(25)
		time.sleep(4)
		self.assertFalse(vehicle.location.global_relative_frame.alt>20)

#Testcase ALTHOLDdisarmSneakTest for SneakPath
	def test_ALTHOLDdisarmSneakTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed = True
		time.sleep(2)
		vehicle.simple_takeoff(20)
		time.sleep(10)
		vehicle.armed   = False
		time.sleep(2)
		self.assertFalse(vehicle.armed== False)

suite = unittest.TestLoader().loadTestsFromTestCase(TestSneakPath)
unittest.TextTestRunner(verbosity=2).run(suite)
vehicle.close()
