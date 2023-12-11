package Player;

import java.util.ArrayList;
import java.util.List;

import Common.IMap;
import Common.map;
import Common.data.PrivateData;
import Common.data.PublicData;
import Common.tiles.TileObject;
import Player.MoveType.IMove;

public abstract class APlayer implements IPlayer {
  protected final String name;
  protected IMap map;
  protected List<TileObject> hand;
  protected strategy strategy;

  /**
   * player constructor with all fields
   * @param name the name of the player
   * @param map the map the player knows about
   * @param hand the player's current hand
   * @param strategy the strategy the player wants to use
   */
  public APlayer(String name, IMap map, List<TileObject> hand, strategy strategy) {
    this.strategy = strategy;
    this.name = name;
    this.map = map;
    this.hand = hand;
  }

  /**
   * Default player constructor
   * @param name the name of the player
   * @param strategy the strategy the player wants to use
   */
  public APlayer(String name, strategy strategy) {
    this.strategy = strategy;
    this.name = name;
    this.map = new map();
    this.hand = new ArrayList<>();
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public void setUp(PublicData publicData, List<TileObject> hand) {
    this.map = publicData.getBoard();
    this.hand = hand;
  }

  @Override
  public IMove takeTurn(PublicData publicData) {
    this.strategy.reset();
    IMove turnMove = strategy.applyStrategyIterator(publicData, new PrivateData(hand));
    this.map = publicData.getBoard();
    return turnMove;
  }

  @Override
  public void newTiles(List<TileObject> newHand) {
    this.hand = newHand;
  }

  @Override
  public void win(boolean w) {}

  @Override
  public IMap getMap() {
    return this.map;
  }

  @Override
  public List<TileObject> getHand() {
    return new ArrayList<>(this.hand);
  }
}
