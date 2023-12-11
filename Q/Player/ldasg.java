package Player;

import java.util.ArrayList;
import java.util.List;

import Common.data.PrivateData;
import Common.data.PublicData;
import Common.place.Place;

/**
 * A ldasg strategy picks the smallest tile that can be placed, finds the most constrained place,
 * and if there are ties, then picks the smallest of those places to put the tile.
 */
public class ldasg extends AStrategy {

  @Override
  protected List<Place> listPossiblePlaces(List<Place> validPlacements, PublicData gameStateData) {
    return this.mostConstrainedPlaces(validPlacements, gameStateData);
  }

  /**
   * @param validPlacements the list of all valid placements
   * @return the placement that has the most existing neighbors out of the list
   * of valid placements chosen by this strategy.
   */
  private int maxNumberOfNeighbors(List<Place> validPlacements, PublicData gameStateData) {
    int max = 0;
    for (Place p : validPlacements) {
      int numberOfNeighbors = gameStateData.getBoard().getNumberOfExistingNeighbors(p);
      max = Math.max(max, numberOfNeighbors);
    }
    return max;
  }

  /**
   * @param placements is the list of valid places.
   * @return the subset list of places from placements that has the given number of neighbors chosen
   * by this strategy.
   */
  private List<Place> mostConstrainedPlaces(List<Place> placements, PublicData gameStateData) {
    int maxNumberOfNeighbors = maxNumberOfNeighbors(placements, gameStateData);
    List<Place> allMaxNeighbors = new ArrayList<>();
    for (Place p : placements) {
      if (gameStateData.getBoard().getNumberOfExistingNeighbors(p) == maxNumberOfNeighbors) {
        allMaxNeighbors.add(p);
      }
    }
    return allMaxNeighbors;
  }
}
