package Common.tiles;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * To represent all possible shapes for a Tile object
 */
public enum TileShape {
  STAR("star"),
  EIGHT_STAR("8star"),
  SQUARE("square"),
  CIRCLE("circle"),
  CLOVER("clover"),
  DIAMOND("diamond");

  private final String name;

  private static final Map<String, TileShape> TILE_SHAPE_MAP;

  /**
   * Constructor for a TileShape
   *
   * @param name the name of this enum
   */
  TileShape(String name) {
    this.name = name;
  }

  /**
   * Initialize the map of this shape name to its value
   */
  static {
    Map<String, TileShape> map = new HashMap<>();
    for (TileShape shape : TileShape.values()) {
      map.put(shape.getName(), shape);
    }
    TILE_SHAPE_MAP = Collections.unmodifiableMap(map);
  }

  /**
   * @return the name of this TileShape
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name the name of this TileShape enum
   * @return the mapped TileShape value from its given name
   */
  public static TileShape get(String name) {
    return TILE_SHAPE_MAP.get(name);
  }

}