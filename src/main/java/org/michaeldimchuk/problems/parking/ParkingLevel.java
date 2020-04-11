package org.michaeldimchuk.problems.parking;

import static org.michaeldimchuk.utilities.Functions.bind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import lombok.Value;
import org.michaeldimchuk.problems.parking.GaragePlan.LevelPlan;
import org.michaeldimchuk.problems.parking.GaragePlan.RowPlan;
import org.michaeldimchuk.problems.parking.GaragePlan.SpotPlan;

@Value
class ParkingLevel {

  Map<ParkingSpot, ParkingAssignment> assignments = new HashMap<>();

  int level;

  List<List<ParkingSpot>> rows;

  ParkingLevel(LevelPlan level) {
    this.level = level.getId();
    rows = buildRows(level);
  }

  Optional<ParkingAssignment> park(Vehicle vehicle) {
    return rows.stream()
        .map(bind(this::tryPark, vehicle))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst();
  }

  /**
   * Greedily attempts to find a parking spot.
   */
  private Optional<ParkingAssignment> tryPark(List<ParkingSpot> spots, Vehicle vehicle) {
    for (int x = 0; x < spots.size(); x++) {
      ParkingSpot spot = spots.get(x);
      if (canPark(spot, vehicle)) {
        if (canFit(vehicle, spots, x)) {
          return Optional.of(park(spots, x, vehicle));
        }
      }
    }
    return Optional.empty();
  }

  private ParkingAssignment park(List<ParkingSpot> spots, int startIndex, Vehicle vehicle) {
    List<ParkingSpot> reservedSpots = IntStream.range(startIndex, startIndex + vehicle.getSpotsRequired())
        .boxed()
        .map(spots::get)
        .collect(ImmutableList.toImmutableList());
    return reserveSpots(new ParkingAssignment(reservedSpots, vehicle));
  }

  private ParkingAssignment reserveSpots(ParkingAssignment assignment) {
    assignment.getSpots().forEach(spot -> assignments.put(spot, assignment));
    return assignment;
  }

  private boolean canPark(ParkingSpot spot, Vehicle vehicle) {
    return isAvailable(spot) && spot.canFit(vehicle);
  }

  private boolean canFit(Vehicle vehicle, List<ParkingSpot> spots, int currentSpot) {
    if (vehicle.getSpotsRequired() == 1) {
      return true;
    }
    return hasEnoughSpots(spots, currentSpot, vehicle);
  }

  private boolean hasEnoughSpots(List<ParkingSpot> spots, int currentSpot, Vehicle vehicle) {
    int neededSpots = vehicle.getSpotsRequired();
    int lastNeededSpot = currentSpot + neededSpots;
    if (lastNeededSpot >= spots.size()) {
      return false;
    }
    for (int x = currentSpot + 1; x < lastNeededSpot; x++) {
      if (!canPark(spots.get(x), vehicle)) {
        return false;
      }
    }
    return true;
  }

  private boolean isAvailable(ParkingSpot spot) {
    return !assignments.containsKey(spot);
  }

  private List<List<ParkingSpot>> buildRows(LevelPlan level) {
    return level.getRows().stream()
        .map(this::buildSpots)
        .collect(ImmutableList.toImmutableList());
  }

  private List<ParkingSpot> buildSpots(RowPlan row) {
    return row.getSpots().stream()
        .map(this::buildSpot)
        .collect(ImmutableList.toImmutableList());
  }

  private ParkingSpot buildSpot(SpotPlan spot) {
    return ParkingSpot.builder()
        .category(spot.getCategory())
        .level(spot.getLevel())
        .row(spot.getRow())
        .spot(spot.getId())
        .build();
  }
}
