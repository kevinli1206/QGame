package Player;

import Common.IMap;
import Common.data.PublicData;
import Common.tiles.TileObject;
import Player.MoveType.IMove;
import java.util.List;

public abstract class AbstractExceptionPlayer extends APlayer {
  protected final String exceptionMethod;

  /**
   * AbstractExceptionPlayer constructor with all parameters
   * @param exceptionMethod the method that will throw an exception
   * @param name the name of the player
   * @param map the map the player knows about
   * @param hand the player's current hand
   * @param strategy the strategy the player wants to use
   */
  public AbstractExceptionPlayer(String exceptionMethod, String name, IMap map,
      List<TileObject> hand, strategy strategy) {
    super(name, map, hand, strategy);
    switch (exceptionMethod) {
      case "setup" :
      case "take-turn":
      case "new-tiles":
      case "win":
        break;
      default:
        throw new IllegalArgumentException("Invalid exn");
    }
    this.exceptionMethod = exceptionMethod;
  }

  /**
   * Default AbstractExceptionPlayer constructor
   * @param name the name of the player
   * @param strategy the strategy the player wants to use
   */
  public AbstractExceptionPlayer(String exceptionMethod, String name, strategy strategy) {
    super(name, strategy);
    switch (exceptionMethod) {
      case "setup" :
      case "take-turn":
      case "new-tiles":
      case "win":
        break;
      default:
        throw new IllegalArgumentException("Invalid exn");
    }
    this.exceptionMethod = exceptionMethod;
  }

  @Override
  public void setUp(PublicData publicData, List<TileObject> hand) {
    if (this.exceptionMethod.equals("setup")) {
      this.abstractException();
    }
    super.setUp(publicData, hand);
  }

  @Override
  public IMove takeTurn(PublicData publicData) {
    if (this.exceptionMethod.equals("take-turn")) {
      this.abstractException();
    }
    return super.takeTurn(publicData);
  }

  @Override
  public void newTiles(List<TileObject> newHand) {
    if (this.exceptionMethod.equals("new-tiles")) {
      this.abstractException();
    }
    super.newTiles(newHand);
  }

  @Override
  public void win(boolean w) {
    if (this.exceptionMethod.equals("win")) {
      this.abstractException();
    }
    super.win(w);
  }

  protected abstract void abstractException();
}
