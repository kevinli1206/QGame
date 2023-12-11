package Common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import Common.data.PrivateData;
import Common.data.PublicData;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import Common.place.Place;
import Common.players.Player;
import Common.players.PlayersInfo;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import Player.IPlayer;

/**
 * To represent the referee's knowledge of this game state
 */
public class game_state implements IGameState {

  private final IMap map;
  private final PlayersInfo playersInfo;
  private final List<TileObject> refereeTiles;
  private final Random seed;
  private final int qbo;
  private final int fbo;

  private static final int Q_SEQUENCE = 6;
  private static final int TILES_IN_HAND = 6;
  private static final int SCORE_PLACE_ALL_TILES = 4;
  private static final int SCORE_Q_BONUS = 8;

  /**
   * game_state constructor
   */
  public game_state() {
    this.map = new map();
    this.playersInfo = new PlayersInfo();
    this.refereeTiles = new ArrayList<>();
    this.seed = new Random();
    this.qbo = SCORE_Q_BONUS;
    this.fbo = SCORE_PLACE_ALL_TILES;
  }

  /**
   * game_state constructor with seed
   */
  public game_state(int seed) {
    this.map = new map();
    this.playersInfo = new PlayersInfo();
    this.refereeTiles = new ArrayList<>();
    this.seed = new Random(seed);
    this.qbo = SCORE_Q_BONUS;
    this.fbo = SCORE_PLACE_ALL_TILES;
  }

  /**
   * game_state constructor with all inputs including score bonuses
   */
  public game_state(IMap map, PlayersInfo players, int numRefereeTiles) {
    this.seed = new Random();
    this.map = map;
    this.playersInfo = players;
    List<TileObject> initialRefTiles = this.initializeGameTiles();
    this.refereeTiles = initialRefTiles.subList(0,
        Math.min(numRefereeTiles, initialRefTiles.size()));
    this.qbo = SCORE_Q_BONUS;
    this.fbo = SCORE_PLACE_ALL_TILES;
  }


  /**
   * game_state constructor with all inputs and seed
   */
  public game_state(IMap map, PlayersInfo players, int numRefereeTiles, int seed) {
    this.seed = new Random(seed);
    this.map = map;
    this.playersInfo = players;
    List<TileObject> initialRefTiles = this.initializeGameTiles();
    this.refereeTiles = initialRefTiles.subList(0,
        Math.min(numRefereeTiles, initialRefTiles.size()));
    this.qbo = SCORE_Q_BONUS;
    this.fbo = SCORE_PLACE_ALL_TILES;
  }

  /**
   * game_state constructor with given map, players, and ref tiles
   */
  public game_state(IMap map, PlayersInfo players, List<TileObject> refereeTiles) {
    this.seed = new Random();
    this.map = map;
    this.playersInfo = players;
    this.refereeTiles = refereeTiles;
    this.qbo = SCORE_Q_BONUS;
    this.fbo = SCORE_PLACE_ALL_TILES;
  }

  /**
   * game_state constructor with given map, players, ref tiles, and score bonuses
   */
  public game_state(IMap map, PlayersInfo players, List<TileObject> refereeTiles, int qbo, int fbo) {
    this.seed = new Random();
    this.map = map;
    this.playersInfo = players;
    this.refereeTiles = refereeTiles;
    this.qbo = qbo;
    this.fbo = fbo;
  }

  @Override
  public void initializeGame(List<String> names) {
    refereeTiles.addAll(this.initializeGameTiles());
    this.initializePlayers(names);
    this.dealStartingTiles();
    map.initializeFirstTile(refereeTiles.remove(0), new Place(0, 0));
  }

  /**
   * Initialize the players in this game
   *
   * @param names the names of each player attempting to join this game
   */
  private void initializePlayers(List<String> names) {
    List<Player> players = new ArrayList<>();
    for (int i = 0; i < names.size(); i++) {
      Player p = new Player(names.get(i));
      players.add(p);
    }
    playersInfo.setPlayers(players);
  }

  @Override
  public PublicData getPublicData() {
    Map<Place, TileObject> currentBoard = this.map.getMapState();
    int refTilesLeft = this.refereeTiles.size();
    Queue<Entry<String, Integer>> publicPlayerInfo = playersInfo.getPublicPlayerInfo();
    return new PublicData(new map(currentBoard), refTilesLeft, publicPlayerInfo);
  }

  @Override
  public PrivateData getPrivateData(String name) {
    List<TileObject> playerHand = this.playersInfo.getPlayerHand(name);
    return new PrivateData(playerHand);
  }

  /**
   * Deal each player in this game their starting hand of tiles.
   */
  private void dealStartingTiles() {
    for (Player p : playersInfo.getTurnOrder()) {
      this.dealWholeHand(p.getName());
    }
  }

  /**
   * @return a list representing the referee's deck of tiles for this game state
   */
  private List<TileObject> initializeGameTiles() {
    List<TileObject> tos = new ArrayList<>();
    for (TileShape ts : TileShape.values()) {
      for (TileColor tc : TileColor.values()) {
        for (int i = 0; i < 30; i++) {
          tos.add(new TileObject(ts, tc));
        }
      }
    }
    Collections.shuffle(tos, this.seed);
    return tos;
  }

  /**
   * Add the first tile in the referee's hand to the player with the given name if there are still
   * tiles to give in this game
   *
   * @param name the name of the player we are adding the tile to
   */
  private void addTile(String name) {
    if (refereeTiles.isEmpty()) {
      return;
    }
    playersInfo.addTile(name, refereeTiles.remove(0));
  }

  /**
   * Deal a whole hand to the player with the given name in this game
   *
   * @param name the name of the player we are dealing a hand to
   */
  private void dealWholeHand(String name) {
    this.addNTilesToHand(name, TILES_IN_HAND);
  }

  /**
   * Deal back to this player the given number of tiles
   * @param name the name of the player
   * @param size the size of the hand to be dealt
   */
  private void dealBackTiles(String name, int size) {
    this.addNTilesToHand(name, size);
  }

  /**
   * Remove the given tile from the player with the given name and add it back to the referee's hand
   * in this game
   *
   * @param name the name of the player we are removing the tile from
   * @param to   the tile to remove from the player's hand
   */
  private void removeTile(String name, TileObject to) {
    playersInfo.removeTile(name, to);
  }

  /**
   * Remove all tiles from the player with the given name and add it to the referee's hand in this
   * game
   *
   * @param name the name of the player we are removing all tiles from
   */
  private int removeAllTiles(String name) {
    List<TileObject> removed = playersInfo.removeAll(name);
    refereeTiles.addAll(removed);
    return removed.size();
  }

  @Override
  public void runPassTurn(String name) throws IllegalArgumentException {
    if (!this.isCurrentActivePlayer(name)) {
      throw new IllegalArgumentException("Player " + name + " is moving out of turn.");
    }
  }

  @Override
  public void runExchangeTurn(String name) throws IllegalArgumentException {
    if (!this.isCurrentActivePlayer(name)) {
      throw new IllegalArgumentException("Player " + name + " is moving out of turn.");
    }
    if (this.refereeTiles.size() < this.playersInfo.getPlayerHand(name).size()) {
      throw new IllegalArgumentException("Illegal Exchange Move");
    }
    int size = this.removeAllTiles(name);
    this.dealBackTiles(name, size);
  }

  /**
   * EFFECT:
   * 1. mutates the map of the game state to have the tile placed
   * 2. removes the tile from the game state's knowledge of the player's hands
   */
  @Override
  public void runPlaceTurn(String name, List<Entry<Place, TileObject>> placements)
      throws IllegalArgumentException {
    if (!this.isCurrentActivePlayer(name)) {
      throw new IllegalArgumentException("Player " + name + " is moving out of turn.");
    }
    if (placements.isEmpty() || !this.map.sameRowOrColumn(placements, placements.get(0).getKey())) {
      throw new IllegalArgumentException("No placements found or invalid placement detected");
    }
    this.placeAllTiles(name, placements);
  }

  @Override
  public void setNextTurn() {
    this.playersInfo.setNextTurn();
  }

  @Override
  public void kickPlayer(String name) {
    this.addPlayerHandBackToRefereeTiles(name);
    this.playersInfo.removePlayer(name);
  }

  /**
   * Add the player's hand back to the referee's deck of tiles in this game
   * @param name the name of the player
   */
  private void addPlayerHandBackToRefereeTiles(String name) {
    List<TileObject> playerHand = this.playersInfo.getPlayerHand(name);
    this.refereeTiles.addAll(playerHand);
  }

  @Override
  public void scoreTurn(String name, List<Entry<Place, TileObject>> placements) {
    int score = calculateScore(name, placements);
    this.playersInfo.addPointsToPlayer(name, score);
  }

  /**
   * @param name       the name of the player
   * @param placements the list of placements to score
   * @return an integer representing the score for the given placements in this game
   */
  private int calculateScore(String name, List<Entry<Place, TileObject>> placements) {
    int score = 0;
    score += scoreNumTilesPlaced(placements);
    score += scoreContiguousSequence(placements);
    score += scorePlacedAllTiles(name);
    score += scoreAllQs(placements);
    return score;
  }

  /**
   * @param placements the tiles placed
   * @return the score for the number of tiles placed in this iteration of the game
   */
  private int scoreNumTilesPlaced(List<Entry<Place, TileObject>> placements) {
    return placements.size();
  }

  /**
   * @param placements are the tiles placed
   * @return the points received in this game state for each tile in a contiguous sequence
   */
  private int scoreContiguousSequence(List<Entry<Place, TileObject>> placements) {
    int score = 0;
    List<Entry<Place, TileObject>> seenPlacements = new ArrayList<>();
    for (Entry<Place, TileObject> e : placements) {
      score += scoreRowSequence(e, seenPlacements);
      score += scoreColumnSequence(e, seenPlacements);
      seenPlacements.add(e);
    }
    return score;
  }

  /**
   * @param currentPlacement the tile being placed
   * @param seenPlacements   a list of placements already considered
   * @return the number of points received in this game state for putting a tile down in a row
   * sequence
   */
  private int scoreRowSequence(Entry<Place, TileObject> currentPlacement,
      List<Entry<Place, TileObject>> seenPlacements) {
    Set<Entry<Place, TileObject>> rowSeq = map.getContiguousRowSequence(currentPlacement);
    if (rowSeq.size() <= 1) {
      return 0;
    }
    for (Entry<Place, TileObject> e : rowSeq) {
      if (seenPlacements.contains(e)) {
        return 0;
      }
    }
    return rowSeq.size();
  }

  /**
   * @param currentPlacement the tile being placed
   * @param seenPlacements   a list of placements already considered
   * @return the number of points received in this game state for putting a tile down in a column
   * sequence
   */
  private int scoreColumnSequence(Entry<Place, TileObject> currentPlacement,
      List<Entry<Place, TileObject>> seenPlacements) {
    Set<Entry<Place, TileObject>> colSeq = map.getContiguousColumnSequence(currentPlacement);
    if (colSeq.size() <= 1) {
      return 0;
    }
    for (Entry<Place, TileObject> e : colSeq) {
      if (seenPlacements.contains(e)) {
        return 0;
      }
    }
    return colSeq.size();
  }

  /**
   * @param name the name of the player
   * @return the bonus points received if the player placed all their tiles down in this game state
   */
  private int scorePlacedAllTiles(String name) {
    int score = 0;
    if (this.playersInfo.getPlayerHand(name).isEmpty()) {
      score += this.fbo;
    }
    return score;
  }

  /**
   * @param currentPlacement is the current tile placement.
   * @param seenPlacements   is a list of placements already considered.
   * @return true if a new Q is completed with the current placement in this game state
   */
  private boolean checkRowQ(Entry<Place, TileObject> currentPlacement,
      List<Entry<Place, TileObject>> seenPlacements) {
    Set<Entry<Place, TileObject>> rowSeq = map.getContiguousRowSequence(currentPlacement);
    for (Entry<Place, TileObject> e : rowSeq) {
      if (seenPlacements.contains(e)) {
        return false;
      }
    }
    return rowSeq.size() == Q_SEQUENCE && isSequenceQ(rowSeq);
  }

  /**
   * @param currentPlacement is the current tile placement.
   * @param seenPlacements   is a list of placements already considered.
   * @return true if a new Q is completed with the current placement in this game state
   */
  private boolean checkColQ(Entry<Place, TileObject> currentPlacement,
      List<Entry<Place, TileObject>> seenPlacements) {
    Set<Entry<Place, TileObject>> colSeq = map.getContiguousColumnSequence(currentPlacement);
    for (Entry<Place, TileObject> e : colSeq) {
      if (seenPlacements.contains(e)) {
        return false;
      }
    }
    return colSeq.size() == Q_SEQUENCE && isSequenceQ(colSeq);
  }

  /**
   * @param sequence is the sequence of tiles.
   * @return true if the sequence in this game state contains all colors or shapes.
   */
  private boolean isSequenceQ(Set<Entry<Place, TileObject>> sequence) {
    Set<TileColor> colors = new HashSet<>();
    Set<TileShape> shapes = new HashSet<>();
    for (Entry<Place, TileObject> e : sequence) {
      colors.add(e.getValue().getColor());
      shapes.add(e.getValue().getShape());
    }
    return colors.size() == Q_SEQUENCE || shapes.size() == Q_SEQUENCE;
  }

  /**
   * @param placements are the tiles placed in a turn.
   * @return the cumulative Q score for a turn in this game state.
   */
  private int scoreAllQs(List<Entry<Place, TileObject>> placements) {
    int score = 0;
    List<Entry<Place, TileObject>> seenPlacements = new ArrayList<>();
    for (Entry<Place, TileObject> e : placements) {
      if (checkRowQ(e, seenPlacements)) {
        score += this.qbo;
      }
      if (checkColQ(e, seenPlacements)) {
        score += this.qbo;
      }
      seenPlacements.add(e);
    }
    return score;
  }

  /**
   * @param name the name of the player
   * @return true if this player is the active player in this game state
   */
  private boolean isCurrentActivePlayer(String name) {
    return this.playersInfo.getActivePlayer().sameName(name);
  }

  /**
   * Attempt to make every placement of tiles provided by the list of entries in this game state
   *
   * @param name       is the name of the player making the placement
   * @param placements a map of places to tiles that are being put on the board
   * @throws IllegalArgumentException if any of the placements are invalid.
   */
  private void placeAllTiles(String name, List<Entry<Place, TileObject>> placements)
      throws IllegalArgumentException {
    if (!this.validPlayerPlacements(name, placements)) {
      throw new IllegalArgumentException("Invalid Placement");
    }
    for (Entry<Place, TileObject> entry : placements) {
      Place tilePlace = entry.getKey();
      TileObject tileObject = entry.getValue();
      setPlayerPlaceTile(name, tilePlace, tileObject);
    }
  }

  /**
   * @param name       is the name of the player making the move
   * @param placements the placements attempted to be made
   * @return true if all the placements are valid for this game state
   */
  private boolean validPlayerPlacements(String name, List<Entry<Place, TileObject>> placements) {
    IMap mapCopy = new map(this.map.getMapState());
    List<TileObject> playerHandCopy = new ArrayList<>(this.playersInfo.getPlayerHand(name));
    for (Entry<Place, TileObject> entry : placements) {
      Place tilePlace = entry.getKey();
      TileObject tileObject = entry.getValue();
      if (!playerHandCopy.contains(tileObject) ||
          !mapCopy.getValidPlacements(tileObject).contains(tilePlace)) {
        return false;
      }
      mapCopy.placeTile(tileObject, tilePlace);
      playerHandCopy.remove(tileObject);
    }
    return true;
  }

  /**
   * Puts the given tile from the player's hand on the board of this game state
   * EFFECT:
   * 1. mutates the map of the game state to have the tile placed
   * 2. removes the tile from the game state's knowledge of the player's hands
   * @param name  the name of the player placing the tile
   * @param place the coordinate to put the tile
   * @param tile  the tile to be placed
   *
   */
  private void setPlayerPlaceTile(String name, Place place, TileObject tile) {
    this.map.placeTile(tile, place);
    this.removeTile(name, tile);
  }

  @Override
  public void addNTilesToHand(String name, int n) {
    for (int i = 0; i < n; i++) {
      this.addTile(name);
    }
  }

  @Override
  public boolean isPlacementValid(TileObject tile, Place place) {
    return this.map.getValidPlacements(tile).contains(place);
  }

  @Override
  public boolean matchingPlayers(List<IPlayer> players) {
    if (players.size() != this.playersInfo.getPublicPlayerInfo().size()) {
      return false;
    }
    List<Entry<String, Integer>> gameStatePlayers =
            new ArrayList<>(this.playersInfo.getPublicPlayerInfo());
    for (int i = 0; i < players.size(); i++) {
      if (!players.get(i).name().equals(gameStatePlayers.get(i).getKey())) {
        return false;
      }
    }
    return true;
  }

  @Override
  public IGameState makeCopy() {
    List<TileObject> refTilesCopy = new ArrayList<>(this.refereeTiles);
    IMap mapCopy = new map(this.map.getMapState());
    PlayersInfo playersInfoCopy = this.playersInfo.getCopy();
    return new game_state(mapCopy, playersInfoCopy, refTilesCopy);
  }

  @Override
  public List<TileObject> getRefTiles() {
    return new ArrayList<>(this.refereeTiles);
  }

  @Override
  public void initializePlayerNames(List<String> names) {
    this.playersInfo.setNames(names);
  }

  @Override
  public int getMaxScore() {
    int max = 0;
    for (Player p : this.playersInfo.getTurnOrder()) {
      max = Math.max(p.getPoints(), max);
    }
    return max;
  }

  /**
   * gets the buffered image of this game state containing map, ref tiles, and scores
   * TODO: add in cur player's hand
   */

  @Override
  public BufferedImage getGameStateImage() {
    BufferedImage mapBuffer = this.map.renderMap();
    BufferedImage playerInfoBuffer = this.playersInfo.renderPlayersInfo();
    BufferedImage refTilesBuffer = this.renderRemainingRefTiles();

    int canvasWidth = mapBuffer.getWidth();
    int canvasHeight = mapBuffer.getHeight() + playerInfoBuffer.getHeight();

    BufferedImage gameStateCanvas = new BufferedImage(canvasWidth, canvasHeight,
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D gameStateGraphics = gameStateCanvas.createGraphics();

    gameStateGraphics.drawImage(mapBuffer, 0, 0,
        null);
    gameStateGraphics.drawImage(refTilesBuffer, 0,
        mapBuffer.getHeight(), null);
    gameStateGraphics.drawImage(playerInfoBuffer, -125,
        mapBuffer.getHeight() + refTilesBuffer.getHeight(), null);
    return gameStateCanvas;
  }

  @Override
  public void renderCurrentState() {
    BufferedImage gameStateCanvas = this.getGameStateImage();
    try {
      ImageIO.write(gameStateCanvas, "png", new File("game_state.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Render the image of number of referee tiles remaining in this game state
   */
  private BufferedImage renderRemainingRefTiles() {
    BufferedImage refereeTilesRemainingImage =
        new BufferedImage(400, 125, BufferedImage.TYPE_INT_ARGB);
    Graphics2D refereeTilesRemainingGraphic = refereeTilesRemainingImage.createGraphics();
    refereeTilesRemainingGraphic.setColor(Color.RED);
    refereeTilesRemainingGraphic.drawString(
        "Tiles Left: " + this.refereeTiles.size(), 0, 125);
    refereeTilesRemainingGraphic.dispose();
    return refereeTilesRemainingImage;
  }

}
