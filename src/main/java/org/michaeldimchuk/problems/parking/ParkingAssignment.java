package org.michaeldimchuk.problems.parking;

import java.util.List;

import lombok.Value;

@Value
public class ParkingAssignment {

  List<ParkingSpot> spots;

  Vehicle vehicle;
}
