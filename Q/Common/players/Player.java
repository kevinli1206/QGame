package Common.players;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Common.tiles.TileObject;

/**
 * To represent knowledge that a game state knows about this player's state in the Q game.
 */
public class Player {

  private String name;
  private int points;
  private final List<TileObject> hand;

  /**
   * Player constructor
   *
   * @param name   the distinct name of this player
   * @param points the points this player currently has
   * @param hand   the list of tiles this player currently has
   */
  public Player(String name, int points, List<TileObject> hand) {
    this.name = name;
    this.points = points;
    this.hand = hand;
  }

  /**
   * Player constructor with all
   *
   * @param points the points this player currently has
   * @param hand   the list of tiles this player currently has
   */
  public Player(int points, List<TileObject> hand, String name) {
    this.name = name;
    this.points = points;
    this.hand = hand;
  }

  /**
   * Player constructor with all but name parameters
   *
   * @param points the points this player currently has
   * @param hand   the list of tiles this player currently has
   */
  public Player(int points, List<TileObject> hand) {
    this.name = name;
    this.points = points;
    this.hand = hand;
  }

  /**
   * Default Player Constructor with their name
   *
   * @param name the name of this player
   */
  public Player(String name) {
    this.name = name;
    this.points = 0;
    this.hand = new ArrayList<>();
  }

  public String getName() {
    return this.name;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Player)) {
      return false;
    }

    Player other = (Player) o;
    return this.getName().equals(other.getName());
  }

  /**
   * makes a copy of this player
   */
  public Player makeCopy() {
    return new Player(this.name, this.points, new ArrayList<>(this.hand));
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /**
   * Adds the given tile to this player's hand
   *
   * @param to the tile to be added.
   * @throws IllegalArgumentException if this player has at least 6 tiles already.
   */
  public void addTile(TileObject to) throws IllegalArgumentException {
//    if (this.hand.size() >= 6) {
//      throw new IllegalArgumentException("This player already has at least 6 tiles.");
//    }
    this.hand.add(to);
  }

  /**
   * Remove the given tile from this player's hand
   *
   * @param to the tile to be removed.
   * @throws IllegalArgumentException if this player doesn't have the current tile.
   */
  public void removeTile(TileObject to) throws IllegalArgumentException {
    if (!this.hasTile(to)) {
      throw new IllegalArgumentException("This player does not have this tile.");
    }
    this.hand.remove(to);
  }

  /**
   * @return a list of tiles that were removed from this player's hand
   */
  public List<TileObject> removeAll() {
    List<TileObject> removed = new ArrayList<>(this.hand);
    this.hand.clear();
    return removed;
  }

  /**
   * @param name the name to check against
   * @return true if this player's name and the given name match
   */
  public boolean sameName(String name) {
    return this.name.equals(name);
  }

  public int getPoints() {
    return this.points;
  }

  public List<TileObject> getHand() {
    return new ArrayList<>(this.hand);
  }

  /**
   * @param tile the tile to check for in this player's hand
   * @return true if this player's hand has the given tile
   */
  public boolean hasTile(TileObject tile) {
    return this.hand.contains(tile);
  }

  /**
   * Adds the given score amount to this player
   * @param score is the score amount to be added to this player
   */
  public void addPoints(int score) {
    this.points += score;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Render this player's score
   */
  public BufferedImage renderPlayer() {
    BufferedImage playerScoreImage =
        new BufferedImage(500, 125, BufferedImage.TYPE_INT_ARGB);
    Graphics2D playerScoreGraphic = playerScoreImage.createGraphics();
    playerScoreGraphic.setColor(Color.RED);
    playerScoreGraphic.drawString("Player " + name + ": " + points, 125, 62);
    playerScoreGraphic.dispose();
    return playerScoreImage;
  }
}
