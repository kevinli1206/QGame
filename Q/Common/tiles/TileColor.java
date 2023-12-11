package Common.tiles;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * To represent all possible colors for a Tile object
 */
public enum TileColor {
  RED("red"),
  GREEN("green"),
  BLUE("blue"),
  YELLOW("yellow"),
  ORANGE("orange"),
  PURPLE("purple");

  private final String name;

  private static final Map<String, TileColor> TILE_COLOR_MAP;

  /**
   * Constructor for a TileColor
   *
   * @param name the name of this color enum
   */
  TileColor(String name) {
    this.name = name;
  }

  /**
   * Initialize the map of the color name to its value
   */
  static {
    Map<String, TileColor> map = new HashMap<>();
    for (TileColor color : TileColor.values()) {
      map.put(color.getName(), color);
    }
    TILE_COLOR_MAP = Collections.unmodifiableMap(map);
  }

  /**
   * @return the name representing this TileColor
   */
  public String getName() {
    return this.name;
  }

  /**
   * @param name the name of this TileColor enum
   * @return the mapped TileColor value from its given name
   */
  public static TileColor get(String name) {
    return TILE_COLOR_MAP.get(name);
  }


}