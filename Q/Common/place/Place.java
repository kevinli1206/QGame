package Common.place;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * To define a location in space in terms of its x and y coordinates.
 */
public class Place {

  private final int x;

  private final int y;

  /**
   * Constructor for Place
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Place(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Place)) {
      return false;
    }

    Place other = (Place) o;
    return this.getX() == other.getX() && this.getY() == other.getY();
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  /**
   * @return an array of all 4 directional neighbors for this place
   */
  public Place[] getCardinalNeighbors() {
    Place[] neighbors = new Place[4];

    neighbors[0] = this.getNeighborDown();
    neighbors[1] = this.getNeighborUp();
    neighbors[2] = this.getNeighborRight();
    neighbors[3] = this.getNeighborLeft();
    return neighbors;
  }

  /**
   * @return an array of this place's column neighbors
   */
  public Place[] getColumnNeighbors() {
    Place[] columnNeighbors = new Place[2];
    columnNeighbors[0] = this.getNeighborDown();
    columnNeighbors[1] = this.getNeighborUp();
    return columnNeighbors;
  }

  /**
   * @return an array of this place's row neighbors
   */
  public Place[] getRowNeighbors() {
    Place[] rowNeighbors = new Place[2];
    rowNeighbors[0] = this.getNeighborLeft();
    rowNeighbors[1] = this.getNeighborRight();
    return rowNeighbors;
  }

  /**
   * @param places the list of places
   * @return true if this place is a neighbor of any of the given places
   */
  public boolean isNeighborOf(List<Place> places) {
    for (Place place: places) {
      for (Place neighbor : place.getCardinalNeighbors()) {
        if (this.equals(neighbor)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @return the place that is one up from this place
   */
  public Place getNeighborUp() {
    return new Place(this.x, this.y - 1);
  }

  /**
   * @return the place that is one down from this place
   */
  public Place getNeighborDown() {
    return new Place(this.x, this.y + 1);
  }

  /**
   * @return the place that is one right from this place
   */
  public Place getNeighborRight() {
    return new Place(this.x + 1, this.y);
  }

  /**
   * @return the place that is one left from this place
   */
  public Place getNeighborLeft() {
    return new Place(this.x - 1, this.y);
  }

  /**
   * Render this place's image
   */
  public BufferedImage renderPlace() {
    BufferedImage placeImage =
        new BufferedImage(125, 125, BufferedImage.TYPE_INT_ARGB);
    Graphics2D placeImageGraphic = placeImage.createGraphics();
    placeImageGraphic.setColor(Color.BLACK);
    placeImageGraphic.drawString(this.x + ", " + this.y, 0, 62);
    placeImageGraphic.dispose();
    return placeImage;
  }
}
