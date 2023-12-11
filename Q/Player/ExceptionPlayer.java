package Player;

import java.util.List;

import Common.IMap;
import Common.data.PublicData;
import Common.tiles.TileObject;
import Player.MoveType.IMove;

/**
 * A stupid test player who will be kicked from the game by a
 * referee if an exception method is called.
 */
public class ExceptionPlayer extends AbstractExceptionPlayer {

  /**
   * ExceptionPlayer constructor with all parameters
   * @param exceptionMethod the method that will throw an exception
   * @param name the name of the player
   * @param map the map the player knows about
   * @param hand the player's current hand
   * @param strategy the strategy the player wants to use
   */
  public ExceptionPlayer(String exceptionMethod, String name, IMap map,
                    List<TileObject> hand, strategy strategy) {
    super(exceptionMethod, name, map, hand, strategy);
  }

  /**
   * Default ExceptionPlayer constructor
   * @param exceptionMethod the method that throws an exception
   * @param name the name of the player
   * @param strategy the strategy the player wants to use
   */
  public ExceptionPlayer(String exceptionMethod, String name, strategy strategy) {
    super(exceptionMethod, name, strategy);
  }

  @Override
  protected void abstractException() {
    throw new RuntimeException("Exception Player exception");
  }
}
