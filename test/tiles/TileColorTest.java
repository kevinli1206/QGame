package tiles;

import static org.junit.Assert.*;

import Common.tiles.TileColor;
import org.junit.Before;
import org.junit.Test;

public class TileColorTest {

  TileColor red;
  TileColor green;
  TileColor blue;
  TileColor yellow;
  TileColor orange;
  TileColor purple;

  @Before
  public void initData() {
    red = TileColor.RED;
    green = TileColor.GREEN;
    blue = TileColor.BLUE;
    yellow = TileColor.YELLOW;
    orange = TileColor.ORANGE;
    purple = TileColor.PURPLE;
  }

  @Test
  public void testGetName() {
    assertEquals(red.getName(), "red");
    assertEquals(green.getName(), "green");
    assertEquals(blue.getName(), "blue");
    assertEquals(yellow.getName(), "yellow");
    assertEquals(orange.getName(), "orange");
    assertEquals(purple.getName(), "purple");
  }

  @Test
  public void testGet() {
    assertEquals(TileColor.get("red"), red);
    assertEquals(TileColor.get("green"), green);
    assertEquals(TileColor.get("blue"), blue);
    assertEquals(TileColor.get("yellow"), yellow);
    assertEquals(TileColor.get("orange"), orange);
    assertEquals(TileColor.get("purple"), purple);
  }
}