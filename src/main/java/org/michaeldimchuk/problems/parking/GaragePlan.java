package org.michaeldimchuk.problems.parking;

import java.util.List;

import lombok.Value;

@Value
public class GaragePlan {

  List<LevelPlan> levels;

  @Value
  public static class LevelPlan {

    int id;

    List<RowPlan> rows;
  }

  @Value
  public static class RowPlan {

    int id;

    int level;

    List<SpotPlan> spots;
  }

  @Value
  public static class SpotPlan {

    int id;

    int row;

    int level;

    VehicleCategory category;
  }
}
