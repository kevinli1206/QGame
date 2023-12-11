package Common;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import Common.data.PrivateData;
import Common.data.PublicData;
import Common.place.Place;
import Common.tiles.TileObject;
import Player.IPlayer;

/**
 * To represent a referee's knowledge of this game state that handles operations involving the game.
 */
public interface IGameState {

  /**
   * Initialize this game with new players with the given names
   *
   * @param names the names of the players in the game.
   */
  void initializeGame(List<String> names);

  /**
   * @return a public data object that contains all public information about this game state
   */
  PublicData getPublicData();

  /**
   * @param name the player whose private data we are getting
   * @return a private data object that contains all private information about a player in this game
   */
  PrivateData getPrivateData(String name);

  /**
   * Complete a pass turn action in this game
   *
   * @param name the name of the player
   * @throws IllegalArgumentException if the supplied player's name is not the active player
   */
  void runPassTurn(String name) throws IllegalArgumentException;

  /**
   * Complete an exchange turn action in this game
   *
   * @param name the name of the player
   * @throws IllegalArgumentException if the supplied player's name is not the active player
   */
  void runExchangeTurn(String name);

  /**
   * Complete a place turn action in this game
   *
   * @param name the name of the player
   * @param placements a map of places to tiles that are being placed on the board
   * @throws IllegalArgumentException if the supplied player's name is not the active player
   */
  void runPlaceTurn(String name, List<Entry<Place, TileObject>> placements)
          throws IllegalArgumentException;

  /**
   * @param tile is the tile to be placed
   * @param place the location to place the tile
   * @return true if the placement is valid according to the rules of this game
   */
  boolean isPlacementValid(TileObject tile, Place place);

  /**
   * Display the state of this game graphically
   */
  void renderCurrentState();

  /**
   * @return the GUI representation of this game state as a BufferedImage
   */
  BufferedImage getGameStateImage();

  /**
   * Accepts a list of placements that have been placed in this game and the name of the player
   * requesting moves, and adds the total turn score to the player.
   *
   * @param name is the player's name.
   * @param placements is the list of tile placements the player made.
   */
  void scoreTurn(String name, List<Entry<Place, TileObject>> placements);

  /**
   * Set the current turn to the next player in this game
   */
  void setNextTurn();


  /**
   * Remove the player with this name from the game and add their tiles back to
   * the referee's hand.
   */
  void kickPlayer(String name);

  /**
   * Add the given number of tiles to the hand of the player with the given name in this game
   *
   * @param name the name of the player
   * @param n the number of tiles to add to the player's hand
   */
  void addNTilesToHand(String name, int n);

  /**
   * @param players the list of real players for this game state
   * @return if the real players' names match the names of the players in this game state
   */
  boolean matchingPlayers(List<IPlayer> players);

  /**
   * Set the names of the players in this game to the given names
   * @param names the given names
   */
  void initializePlayerNames(List<String> names);

  /**
   * @return the maximum player score in the list of players in this gamestate
   */
  int getMaxScore();

  /**
   * @return a copy of this gamestate
   */
  IGameState makeCopy();

  /**
   * @return a copy of this gamestate's referee tiles
   */
  List<TileObject> getRefTiles();

}
