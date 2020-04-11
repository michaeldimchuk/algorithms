package org.michaeldimchuk.problems.parking;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class ParkingSpot {

  VehicleCategory category;

  int level;

  int row;

  int spot;

  boolean canFit(Vehicle vehicle) {
    return category.compareTo(vehicle.getCategory()) >= 0;
  }
}
