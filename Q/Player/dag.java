package Player;

import java.util.List;

import Common.data.PrivateData;
import Common.data.PublicData;
import Common.place.Place;

/**
 * A dag strategy chooses the smallest tile that can be placed on the map and if there are ties for
 * location, then it picks the smallest location.
 */
public class dag extends AStrategy {

  /**
   * Abstract dag strategy constructor
   */


  @Override
  protected List<Place> listPossiblePlaces(List<Place> validPlacements, PublicData gameStateData) {
    return validPlacements;
  }

}
