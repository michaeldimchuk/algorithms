package org.michaeldimchuk.problems.parking;

public class Bus extends Vehicle {
  public Bus(String licensePlate) {
    super(VehicleCategory.LARGE, licensePlate, 5);
  }
}
