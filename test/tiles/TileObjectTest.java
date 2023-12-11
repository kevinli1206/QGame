package tiles;

import static org.junit.Assert.*;

import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import org.junit.Before;
import org.junit.Test;

public class TileObjectTest {

  TileObject redstar;
  TileObject green8star;
  TileObject bluesquare;
  TileObject yellowcircle;
  TileObject orangeclover;
  TileObject purplediamond;

  @Before
  public void initData() {
    redstar = new TileObject(TileShape.STAR, TileColor.RED);
    green8star = new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN);
    bluesquare = new TileObject(TileShape.SQUARE, TileColor.BLUE);
    yellowcircle = new TileObject(TileShape.CIRCLE, TileColor.YELLOW);
    orangeclover = new TileObject(TileShape.CLOVER, TileColor.ORANGE);
    purplediamond = new TileObject(TileShape.DIAMOND, TileColor.PURPLE);
  }

  @Test
  public void testEquals() {
    assertTrue(redstar.equals(new TileObject(TileShape.STAR, TileColor.RED)));
    assertFalse(redstar.equals(green8star));
    assertFalse(redstar.equals(new TileObject(TileShape.EIGHT_STAR, TileColor.RED)));
    assertFalse(redstar.equals(new TileObject(TileShape.STAR, TileColor.BLUE)));
    assertTrue(green8star.equals(new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN)));
    assertFalse(green8star.equals(bluesquare));
    assertTrue(bluesquare.equals(new TileObject(TileShape.SQUARE, TileColor.BLUE)));
    assertFalse(bluesquare.equals(yellowcircle));
    assertTrue(yellowcircle.equals(new TileObject(TileShape.CIRCLE, TileColor.YELLOW)));
    assertFalse(yellowcircle.equals(orangeclover));
    assertTrue(orangeclover.equals(new TileObject(TileShape.CLOVER, TileColor.ORANGE)));
    assertFalse(orangeclover.equals(purplediamond));
    assertTrue(purplediamond.equals(new TileObject(TileShape.DIAMOND, TileColor.PURPLE)));
  }

  @Test
  public void testHashCode() {
    assertEquals(redstar.hashCode(), (new TileObject(TileShape.STAR, TileColor.RED).hashCode()));
    assertNotEquals(redstar.hashCode(),
        (new TileObject(TileShape.EIGHT_STAR, TileColor.RED)).hashCode());
    assertNotEquals(redstar.hashCode(),
        (new TileObject(TileShape.STAR, TileColor.BLUE)).hashCode());
    assertNotEquals(green8star.hashCode(),
        (new TileObject(TileShape.STAR, TileColor.RED).hashCode()));
    assertEquals(green8star.hashCode(),
        (new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN).hashCode()));
    assertNotEquals(yellowcircle.hashCode(),
        (new TileObject(TileShape.EIGHT_STAR, TileColor.GREEN).hashCode()));
    assertEquals(yellowcircle.hashCode(),
        (new TileObject(TileShape.CIRCLE, TileColor.YELLOW).hashCode()));
  }

  @Test
  public void getColor() {
    assertEquals(redstar.getColor(), TileColor.RED);
    assertEquals(green8star.getColor(), TileColor.GREEN);
    assertEquals(bluesquare.getColor(), TileColor.BLUE);
    assertEquals(yellowcircle.getColor(), TileColor.YELLOW);
    assertEquals(orangeclover.getColor(), TileColor.ORANGE);
    assertEquals(purplediamond.getColor(), TileColor.PURPLE);
  }

  @Test
  public void getShape() {
    assertEquals(redstar.getShape(), TileShape.STAR);
    assertEquals(green8star.getShape(), TileShape.EIGHT_STAR);
    assertEquals(bluesquare.getShape(), TileShape.SQUARE);
    assertEquals(yellowcircle.getShape(), TileShape.CIRCLE);
    assertEquals(orangeclover.getShape(), TileShape.CLOVER);
    assertEquals(purplediamond.getShape(), TileShape.DIAMOND);
  }

  @Test
  public void isSameColor() {
    assertFalse(redstar.isSameColor(green8star));
    assertFalse(bluesquare.isSameColor(yellowcircle));
    assertFalse(orangeclover.isSameColor(purplediamond));
    assertTrue(redstar.isSameColor(new TileObject(TileShape.CIRCLE, TileColor.RED)));
    assertTrue(yellowcircle.isSameColor(new TileObject(TileShape.EIGHT_STAR, TileColor.YELLOW)));
    assertTrue(orangeclover.isSameColor(new TileObject(TileShape.DIAMOND, TileColor.ORANGE)));
  }

  @Test
  public void isSameShape() {
    assertFalse(redstar.isSameShape(green8star));
    assertFalse(bluesquare.isSameShape(yellowcircle));
    assertFalse(orangeclover.isSameShape(purplediamond));
    assertTrue(redstar.isSameShape(new TileObject(TileShape.STAR, TileColor.ORANGE)));
    assertTrue(yellowcircle.isSameShape(new TileObject(TileShape.CIRCLE, TileColor.BLUE)));
    assertTrue(orangeclover.isSameShape(new TileObject(TileShape.CLOVER, TileColor.YELLOW)));
  }

  @Test
  public void testGetImageFileName() {
    assertEquals(redstar.getImageFileName(), "redstar.png");
    assertEquals(green8star.getImageFileName(), "green8star.png");
    assertEquals(bluesquare.getImageFileName(), "bluesquare.png");
    assertEquals(yellowcircle.getImageFileName(), "yellowcircle.png");
    assertEquals(orangeclover.getImageFileName(), "orangeclover.png");
    assertEquals(purplediamond.getImageFileName(), "purplediamond.png");
  }
}