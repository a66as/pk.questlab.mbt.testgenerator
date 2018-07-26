#Auto generated PyUnitTests for PlanExecuteMission

import unittest
import time
from dronekit import connect, VehicleMode, LocationGlobalRelative, LocationGlobal, Command
import math
from pymavlink import mavutil
connection_string= "127.0.0.1:14552"
vehicle=connect(connection_string, wait_ready=True)
def get_location_metres(original_location, dNorth, dEast):
    """
    Returns a LocationGlobal object containing the latitude/longitude `dNorth` and `dEast` metres from the 
    specified `original_location`. The returned Location has the same `alt` value
    as `original_location`.

    The function is useful when you want to move the vehicle around specifying locations relative to 
    the current vehicle position.
    The algorithm is relatively accurate over small distances (10m within 1km) except close to the poles.
    For more information see:
    http://gis.stackexchange.com/questions/2951/algorithm-for-offsetting-a-latitude-longitude-by-some-amount-of-meters
    """
    earth_radius=6378137.0 #Radius of "spherical" earth
    #Coordinate offsets in radians
    dLat = dNorth/earth_radius
    dLon = dEast/(earth_radius*math.cos(math.pi*original_location.lat/180))

    #New position in decimal degrees
    newlat = original_location.lat + (dLat * 180/math.pi)
    newlon = original_location.lon + (dLon * 180/math.pi)
    return LocationGlobal(newlat, newlon,original_location.alt)
# LOADFILE is a manual code
def LOADFILE(aLocation, aSize):
	    """
	    Adds a takeoff command and four waypoint commands to the current mission. 
	    The waypoints are positioned to form a square of side length 2*aSize around the specified LocationGlobal (aLocation).

	    The function assumes vehicle.commands matches the vehicle mission state 
	    (you must have called download at least once in the session and after clearing the mission)
	    """	

	    cmds = vehicle.commands

	    print(" Clear any existing commands")
	    cmds.clear() 
	    
	    print(" Define/add new commands.")
	    # Add new commands. The meaning/order of the parameters is documented in the Command class. 
	     
	    #Add MAV_CMD_NAV_TAKEOFF command. This is ignored if the vehicle is already in the air.
	    cmds.add(Command( 0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_TAKEOFF, 0, 0, 0, 0, 0, 0, 0, 0, 10))

	    #Define the four MAV_CMD_NAV_WAYPOINT locations and add the commands
	    point1 = get_location_metres(aLocation, aSize, -aSize)
	    point2 = get_location_metres(aLocation, aSize, aSize)
	    point3 = get_location_metres(aLocation, -aSize, aSize)
	    point4 = get_location_metres(aLocation, -aSize, -aSize)
	    cmds.add(Command( 0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0, 0, 0, 0, point1.lat, point1.lon, 11))
	    cmds.add(Command( 0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0, 0, 0, 0, point2.lat, point2.lon, 12))
	    cmds.add(Command( 0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0, 0, 0, 0, point3.lat, point3.lon, 13))
	    cmds.add(Command( 0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0, 0, 0, 0, point4.lat, point4.lon, 14))
	    #add dummy waypoint "5" at point 4 (lets us know when have reached destination)
	    cmds.add(Command( 0, 0, 0, mavutil.mavlink.MAV_FRAME_GLOBAL_RELATIVE_ALT, mavutil.mavlink.MAV_CMD_NAV_WAYPOINT, 0, 0, 0, 0, 0, 0, point4.lat, point4.lon, 14))    

	    print(" Upload new commands to vehicle")
	    cmds.upload()

class TestPlanExecuteMission(unittest.TestCase):
#Testcase armTest for PlanExecuteMission
	def test_armTest(self):
		vehicle.mode    = VehicleMode("GUIDED")
		vehicle.armed = True
		time.sleep(10)
		self.assertTrue(vehicle.armed == True)

#Testcase loadmissionfileTest for PlanExecuteMission
	def test_loadmissionfileTest(self):
		LOADFILE(vehicle.location.global_frame,50)
		vehicle.mode= VehicleMode("GUIDED")
		vehicle.armed   = True
		vehicle.simple_takeoff(10)
		time.sleep(20)
		vehicle.mode= VehicleMode("AUTO")
		time.sleep(3)
		self.assertTrue(vehicle.mode.name=="AUTO")

suite = unittest.TestLoader().loadTestsFromTestCase(TestPlanExecuteMission)
unittest.TextTestRunner(verbosity=2).run(suite)
vehicle.close()
