#Auto generated PyUnitTests for ArmDisarm

import unittest
import time
from dronekit import connect, VehicleMode
connection_string= "127.0.0.1:14552"
vehicle=connect(connection_string, wait_ready=True)
class TestArmDisarm(unittest.TestCase):
#Testcase armTest for ArmDisarm
	def test_armTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed   = True
		time.sleep(3)
		self.assertTrue(vehicle.armed== True)

#Testcase disarmTest for ArmDisarm
	def test_disarmTest(self):
		vehicle.armed   = False
		time.sleep(3)
		self.assertTrue(vehicle.armed== False)

suite = unittest.TestLoader().loadTestsFromTestCase(TestArmDisarm)
unittest.TextTestRunner(verbosity=2).run(suite)
vehicle.close()
