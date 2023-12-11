package tiles;

import static org.junit.Assert.*;

import Common.tiles.TileShape;
import org.junit.Before;
import org.junit.Test;

public class TileShapeTest {

  TileShape star;
  TileShape eightstar;
  TileShape square;
  TileShape circle;
  TileShape clover;
  TileShape diamond;

  @Before
  public void initData() {
    star = TileShape.STAR;
    eightstar = TileShape.EIGHT_STAR;
    square = TileShape.SQUARE;
    circle = TileShape.CIRCLE;
    clover = TileShape.CLOVER;
    diamond = TileShape.DIAMOND;
  }

  @Test
  public void testGetName() {
    assertEquals(star.getName(), "star");
    assertEquals(eightstar.getName(), "8star");
    assertEquals(square.getName(), "square");
    assertEquals(circle.getName(), "circle");
    assertEquals(clover.getName(), "clover");
    assertEquals(diamond.getName(), "diamond");
  }

  @Test
  public void testGet() {
    assertEquals(TileShape.get("star"), star);
    assertEquals(TileShape.get("8star"), eightstar);
    assertEquals(TileShape.get("square"), square);
    assertEquals(TileShape.get("circle"), circle);
    assertEquals(TileShape.get("clover"), clover);
    assertEquals(TileShape.get("diamond"), diamond);
  }
}