package Player;

import java.util.List;

import Common.IMap;
import Common.data.PublicData;
import Common.tiles.TileObject;
import Player.MoveType.IMove;

/**
 * To represent players that want to play a Q Game
 */
public interface IPlayer {

  /**
   * @return the name of this player
   */
  String name();

  /**
   * Set an initial map and initial hand of tiles for this player
   * @param publicData the current state info
   * @param hand the initial hand of tiles
   */
  void setUp(PublicData publicData, List<TileObject> hand);

  /**
   * @param publicData the public data about the game state
   * @return the move being made by this player after applying its strategy
   */
  IMove takeTurn(PublicData publicData);

  /**
   * Set this player's hand equal to the given list of tiles
   * @param newHand the new hand of tiles
   */
  void newTiles(List<TileObject> newHand);

  /**
   * @param w true if they win.
   */
  void win(boolean w);

  IMap getMap();

  List<TileObject> getHand();
}
