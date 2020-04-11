package org.michaeldimchuk.problems.parking;

public class Car extends Vehicle {
  public Car(String licensePlate) {
    super(VehicleCategory.STANDARD, licensePlate, 1);
  }
}
