package Player;

import Common.IMap;
import Common.data.PublicData;
import Common.tiles.TileObject;
import Player.MoveType.IMove;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * A player for testing purposes whose given method will go into an infinite loop
 * once its calls hit the given count.
 */
public class ExceptionPlayerWithCount extends AbstractExceptionPlayer {

  private int count;

  private int currentCalls;

  /**
   * ExceptionPlayerWithCount constructor with all parameters
   * @param exceptionMethod the method that will throw an exception
   * @param name the name of the player
   * @param map the map the player knows about
   * @param hand the player's current hand
   * @param strategy the strategy the player wants to use
   * @param count the count until the method goes into an infinite loop
   */
  public ExceptionPlayerWithCount(String exceptionMethod, String name, IMap map,
      List<TileObject> hand, strategy strategy, int count) {
    super(exceptionMethod, name, map, hand, strategy);
    if (count < 1 || count > 7) {
      throw new IllegalArgumentException("Invalid count");
    }
    this.count = count;
    this.currentCalls = 0;
  }

  /**
   * Default ExceptionPlayer constructor
   * @param name the name of the player
   * @param strategy the strategy the player wants to use
   */
  public ExceptionPlayerWithCount(String exceptionMethod, String name, strategy strategy, int count) {
    super(exceptionMethod, name, strategy);
    if (count < 1 || count > 7) {
      throw new IllegalArgumentException("Invalid count");
    }
    this.count = count;
    this.currentCalls = 0;
  }

  @Override
  protected void abstractException() {
    this.currentCalls += 1;
    if (currentCalls == this.count) {
      while (!Thread.currentThread().isInterrupted());
    }
  }
}
