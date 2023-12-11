package Common.tiles;

import java.util.Objects;

/**
 * To represent a tile object with a shape and color
 */
public class TileObject {
  private final TileShape shape;
  private final TileColor color;

  /**
   * Constructor for a TileObject
   *
   * @param shape the shape of this tile object
   * @param color the color of this tile object
   */
  public TileObject(TileShape shape, TileColor color) {
    this.shape = shape;
    this.color = color;
  }

  public TileColor getColor() {
    return this.color;
  }

  public TileShape getShape() {
    return this.shape;
  }

  /**
   * @param tile the given tile to be checked against
   * @return true if this tile has the same color as the given tile
   */
  public boolean isSameColor(TileObject tile) {
    return this.getColor() == tile.getColor();
  }

  /**
   * @param tile the given tile to be checked against
   * @return true if this tile has the same shape as the given tile
   */
  public boolean isSameShape(TileObject tile) {
    return this.getShape() == tile.getShape();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TileObject)) {
      return false;
    }

    TileObject other = (TileObject) o;

    return this.isSameShape(other) && this.isSameColor(other);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.color, this.shape);
  }

  @Override
  public String toString() {
    return "{\"color\" : " + this.color.getName() + ", \"shape\" : " + this.shape.getName() + "}";
  }

  /**
   * Get the string representing this tile's file name
   */
  public String getImageFileName() {
    return this.color.getName() + this.shape.getName() + ".png";
  }

}