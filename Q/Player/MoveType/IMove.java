package Player.MoveType;

import java.util.List;
import java.util.Map.Entry;

import Common.place.Place;
import Common.tiles.TileObject;

/**
 * To represent all possible move types
 */
public interface IMove {

  /**
   * @return the list of possible placements for this move
   */
  List<Entry<Place, TileObject>> getPlacements();

}
