package org.michaeldimchuk.problems.parking;

import static org.michaeldimchuk.utilities.Functions.bind;

import java.util.List;
import java.util.Optional;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
@Getter(AccessLevel.NONE)
public class Garage {

  List<ParkingLevel> levels;

  public Garage(@NonNull GaragePlan plan) {
    levels = buildLevels(plan);
  }

  public Optional<ParkingAssignment> park(Vehicle vehicle) {
    return levels.stream()
        .map(bind(ParkingLevel::park, vehicle))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst();
  }

  private List<ParkingLevel> buildLevels(GaragePlan plan) {
    Preconditions.checkArgument(plan.getLevels().size() > 0, "A garage must have at least one level");
    return plan.getLevels().stream()
        .map(ParkingLevel::new)
        .collect(ImmutableList.toImmutableList());
  }
}
