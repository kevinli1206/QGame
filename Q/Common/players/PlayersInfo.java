package Common.players;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import java.util.Queue;
import Common.tiles.TileObject;

/**
 * To define information about the players in this Q game.
 */
public class PlayersInfo {

  private final Queue<Player> players;

  private static final int MAX_PLAYERS = 4;
  private static final int MIN_PLAYERS = 2;

  /**
   * PlayersInfo constructor
   *
   * @param players the list of players in this game
   */
  public PlayersInfo(Queue<Player> players) {
    this.players = players;
  }

  /**
   * PlayersInfo default constructor
   */
  public PlayersInfo() {
    this.players = new LinkedList<>();
  }

  /**
   * @return a list sorted by the order that these players will take their turns
   */
  public Queue<Player> getTurnOrder() {
    return new LinkedList<>(players);
  }

  /**
   *
   * @return the active player in this game
   * @throws IllegalArgumentException if there is no active player
   */
  public Player getActivePlayer() throws IllegalArgumentException {
    if (this.players.isEmpty()) {
      throw new IllegalArgumentException("No players initialized");
    }
    return this.players.peek();
  }

  /**
   * Set the currently active player's turn to the next player's turn.
   */
  public void setNextTurn() {
    Player oldPlayer = players.poll();
    players.add(oldPlayer);
  }

  /**
   * Set the names of each player in this players info
   * @param names the names of each player
   */
  public void setNames(List<String> names) {
    for (String name : names) {
      Player p = players.poll();
      p.setName(name);
      players.add(p);
    }
  }

  /**
   * Adds all the given players to this queue of players in the game
   * @param players the players to add to the game
   */
  public void setPlayers(List<Player> players) {
    if (players.size() > MAX_PLAYERS || players.size() < MIN_PLAYERS) {
      throw new IllegalArgumentException("Invalid player number");
    }
    this.players.addAll(players);
  }

  /**
   * Add the given tile to the given player's hand
   *
   * @param name the name of the player that the tile is being added to
   * @param to the tile to be added
   * @throws IllegalArgumentException if the tiles cannot be added to the player's hand or no player
   *                                  exists with the given name
   */
  public void addTile(String name, TileObject to) throws IllegalArgumentException {
    this.findPlayer(name).addTile(to);
  }

  /**
   * Remove the given tile from this given player's hand
   *
   * @param name the name of the player that the tile is being removed from
   * @param to the tile to be removed
   */
  public void removeTile(String name, TileObject to) {
    this.findPlayer(name).removeTile(to);
  }

  /**
   * @param name the name of the player we are removing all tiles from
   * @return the list of tiles that were removed from this player's hand
   */
  public List<TileObject> removeAll(String name) {
    return this.findPlayer(name).removeAll();
  }

  /**
   *
   * @param name the name of the player to find in this queue of players
   * @return the player whose name matches the name that is given
   * @throws IllegalArgumentException if no player exists with that name
   */
  private Player findPlayer(String name) throws IllegalArgumentException {
    for (Player p : this.players) {
      if (p.sameName(name)) {
        return p;
      }
    }
    throw new IllegalArgumentException("No player with matching name found");
  }

  /**
   * @return a list of entries of each player's names to their points
   */
  public Queue<Entry<String, Integer>> getPublicPlayerInfo() {
    Queue<Entry<String, Integer>> info = new LinkedList<>();
    for (Player player : this.players) {
      Entry<String, Integer> playerEntry = new SimpleEntry<>(player.getName(), player.getPoints());
      info.add(playerEntry);
    }
    return info;
  }

  /**
   * @param name the name of the player we are getting the hand for
   * @return the list of tiles the given player has
   */
  public List<TileObject> getPlayerHand(String name) {
    return this.findPlayer(name).getHand();
  }

  /**
   * Adds the given score amount to the player with the given name in this queue of players
   * @param name is the name of the player
   * @param score is the score amount to be added to the player
   */
  public void addPointsToPlayer(String name, int score) {
    this.findPlayer(name).addPoints(score);
  }

  /**
   * Remove the active player from this list of players.
   */
  public void removeActivePlayer() {
    this.players.remove();
  }

  /**
   * Remove the player with the given name from this list of players
   * @param name the name of the player to remove.
   */
  public void removePlayer(String name) {
    this.players.remove(this.findPlayer(name));
  }

  /**
   * @return a copy of this player's info
   */
  public PlayersInfo getCopy() {
    Queue<Player> playersCopy = new LinkedList<>();
    for (Player p : this.players) {
      playersCopy.add(p.makeCopy());
    }
    return new PlayersInfo(playersCopy);
  }

  /**
   * @return a buffered image that represents all information about this player info
   */
  public BufferedImage renderPlayersInfo() {
    BufferedImage allPlayerScoreImage = new BufferedImage(400,
        125 * (players.size() + 1), BufferedImage.TYPE_INT_ARGB);
    Graphics2D allPlayerScoreGraphic = allPlayerScoreImage.createGraphics();
    int currentHeight = 0;
    for (Player player : players) {
      BufferedImage currentPlayerImage = player.renderPlayer();
      allPlayerScoreGraphic.drawImage(currentPlayerImage, 0, currentHeight, null);
      currentHeight += 30;
    }
    allPlayerScoreGraphic.dispose();
    return allPlayerScoreImage;
  }
}
