package org.michaeldimchuk.problems.parking;

public class Motorcycle extends Vehicle {
  public Motorcycle(String licensePlate) {
    super(VehicleCategory.SMALL, licensePlate, 1);
  }
}
