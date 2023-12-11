package Player;

import java.util.List;

import Common.IMap;
import Common.tiles.TileObject;

/**
 * To define a local player for the Q Game.
 */
public class player extends APlayer {

  /**
   * player constructor with all fields
   * @param name the name of the player
   * @param map the map the player knows about
   * @param hand the player's current hand
   * @param strategy the strategy the player wants to use
   */
  public player(String name, IMap map, List<TileObject> hand, strategy strategy) {
    super(name, map, hand, strategy);
  }

  /**
   * Default player constructor
   * @param name the name of the player
   * @param strategy the strategy the player wants to use
   */
  public player(String name, strategy strategy) {
    super(name, strategy);
  }
}
