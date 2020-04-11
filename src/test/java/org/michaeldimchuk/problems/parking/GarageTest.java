package org.michaeldimchuk.problems.parking;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.michaeldimchuk.problems.parking.GaragePlan.LevelPlan;
import org.michaeldimchuk.problems.parking.GaragePlan.RowPlan;
import org.michaeldimchuk.problems.parking.GaragePlan.SpotPlan;

class GarageTest {

  private static final Type GARAGE_PLAN = new TypeToken<List<List<Map<VehicleCategory, Integer>>>>() {}.getType();

  private static final Map<VehicleCategory, Function<String, Vehicle>> vehicleFactories = ImmutableMap.of(
      VehicleCategory.SMALL, Motorcycle::new,
      VehicleCategory.STANDARD, Car::new,
      VehicleCategory.LARGE, Bus::new
  );

  Gson gson = new Gson();

  @Test
  void parkTest() {
    Garage garage = new Garage(buildGaragePlan());

    Vehicle car = newVehicle(VehicleCategory.STANDARD);
    Optional<ParkingAssignment> carAssignment = garage.park(car);
    assertThat(carAssignment).isPresent();
    verifyAssignment(carAssignment.get(), car);

    Vehicle bus = newVehicle(VehicleCategory.LARGE);
    Optional<ParkingAssignment> busAssignment = garage.park(bus);
    assertThat(busAssignment).isPresent();
    verifyAssignment(busAssignment.get(), bus);
  }

  private void verifyAssignment(ParkingAssignment assignment, Vehicle vehicle) {
    assertThat(assignment.getVehicle()).isSameAs(vehicle);
    assertThat(assignment.getSpots()).hasSize(vehicle.getSpotsRequired());
  }

  private Vehicle newVehicle(VehicleCategory category) {
    Function<String, Vehicle> factory = vehicleFactories.get(category);
    Preconditions.checkNotNull(factory, "Unsupported vehicle category " + category);
    return factory.apply(RandomStringUtils.randomAlphabetic(6));
  }

  private GaragePlan buildGaragePlan() {
    List<List<Map<VehicleCategory, Integer>>> levelPlans = buildLevelPlans();
    List<LevelPlan> levels = IntStream.range(0, levelPlans.size())
        .boxed()
        .map(level -> buildLevel(level, levelPlans.get(level)))
        .collect(ImmutableList.toImmutableList());
    return new GaragePlan(levels);
  }

  private LevelPlan buildLevel(int level, List<Map<VehicleCategory, Integer>> rowPlans) {
    List<RowPlan> rows = IntStream.range(0, rowPlans.size())
        .boxed()
        .map(row -> buildRow(rowPlans.get(row), row, level))
        .collect(ImmutableList.toImmutableList());
    return new LevelPlan(level, rows);
  }

  private RowPlan buildRow(Map<VehicleCategory, Integer> spots, int row, int level) {
    ImmutableList.Builder<SpotPlan> allSpots = ImmutableList.builder();
    spots.forEach((category, count) -> allSpots.addAll(buildSpots(level, row, category, count)));
    return new RowPlan(row, level, allSpots.build());
  }

  private List<SpotPlan> buildSpots(int level, int row, VehicleCategory category, int count) {
    return IntStream.range(0, count)
        .boxed()
        .map(spot -> new SpotPlan(spot, row, level, category))
        .collect(Collectors.toList());
  }

  private List<List<Map<VehicleCategory, Integer>>> buildLevelPlans() {
    InputStream stream = getClass().getResourceAsStream("parking-plan.json");
    Preconditions.checkNotNull(stream, "Unable to find parking-plan.json to configure test");
    return gson.fromJson(new InputStreamReader(stream), GARAGE_PLAN);
  }
}
