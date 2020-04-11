package org.michaeldimchuk.problems.parking;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public abstract class Vehicle {

  @NonNull
  VehicleCategory category;

  @NonNull
  String licensePlate;

  int spotsRequired;
}
