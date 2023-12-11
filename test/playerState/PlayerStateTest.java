package playerState;

import static org.junit.Assert.*;

import Common.players.Player;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;

/**
 * To test the methods in the Player class.
 */
public class PlayerStateTest {

  Player init1;
  Player init2;
  Player setFields1;
  Player setFields2;
  Player sameInit1Id;
  TileObject redStar;
  TileObject blueCircle;
  TileObject yellowClover;
  List<TileObject> tileList;

  @Before
  public void initData() {
    init1 = new Player("hi");
    init2 = new Player("bye");
    setFields1 = new Player("hello", 100, new ArrayList<>());
    redStar = new TileObject(TileShape.STAR, TileColor.RED);
    blueCircle = new TileObject(TileShape.CIRCLE, TileColor.BLUE);
    yellowClover = new TileObject(TileShape.CLOVER, TileColor.YELLOW);
    tileList = new ArrayList<>();
    tileList.add(redStar);
    tileList.add(blueCircle);
    tileList.add(yellowClover);
    setFields2 = new Player("goodbye", 20, tileList);
    sameInit1Id = new Player("hi", 400, tileList);
  }

  @Test
  public void testGetters() {
    assertEquals(init1.getName(), "hi");
    assertEquals(init2.getName(), "bye");
    assertEquals(setFields1.getName(), "hello");
    assertEquals(setFields2.getName(), "goodbye");
    assertEquals(init1.getPoints(), 0);
    assertEquals(init2.getPoints(), 0);
    assertEquals(setFields1.getPoints(), 100);
    assertEquals(setFields2.getPoints(), 20);
    assertEquals(init1.getHand(), new ArrayList<>());
    assertEquals(init2.getHand(), new ArrayList<>());
    assertEquals(setFields1.getHand(), new ArrayList<>());
    assertEquals(setFields2.getHand(), tileList);
  }

  @Test
  public void testEquals() {
    assertFalse(init1.equals(init2));
    assertFalse(setFields1.equals(setFields2));
    assertTrue(init1.equals(sameInit1Id));
  }

  @Test
  public void testSameName() {
    assertFalse(init1.sameName("hip"));
    assertTrue(init1.sameName("hi"));
    assertTrue(init2.sameName("bye"));
    assertTrue(setFields1.sameName("hello"));
    assertTrue(setFields2.sameName("goodbye"));
  }

  @Test
  public void testAddTile() {
    assertEquals(init1.getHand(), new ArrayList<>());
    init1.addTile(redStar);
    assertNotEquals(init1.getHand(), new ArrayList<>());
    List<TileObject> init1redstar = new ArrayList<>();
    init1redstar.add(redStar);
    assertEquals(init1.getHand(), init1redstar);

    List<TileObject> initTileList = new ArrayList<>(tileList);
    assertEquals(setFields2.getHand(), initTileList);
    setFields2.addTile(redStar);
    assertNotEquals(setFields2.getHand(), initTileList);
    initTileList.add(redStar);
    assertEquals(setFields2.getHand(), initTileList);
  }

//  @Test(expected = IllegalArgumentException.class)
//  public void testAddTileException() {
//    setFields2.addTile(blueCircle);
//    setFields2.addTile(redStar);
//    setFields2.addTile(yellowClover);
//    setFields2.addTile(blueCircle);
//  }

  @Test
  public void testRemoveTile() {
    assertEquals(init1.getHand(), new ArrayList<>());
    init1.addTile(blueCircle);
    List<TileObject> init1blueCircle = new ArrayList<>();
    init1blueCircle.add(blueCircle);
    assertEquals(init1.getHand(), init1blueCircle);
    init1.removeTile(blueCircle);
    assertEquals(init1.getHand(), new ArrayList<>());

    List<TileObject> initTileList = new ArrayList<>(tileList);
    assertEquals(setFields2.getHand(), initTileList);
    assertEquals(setFields2.getHand().size(), 3);
    setFields2.removeTile(redStar);
    assertEquals(setFields2.getHand().size(), 2);
    assertNotEquals(setFields2.getHand(), initTileList);
    initTileList.remove(redStar);
    assertEquals(setFields2.getHand(), initTileList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTileEmptyHandException() {
    init1.removeTile(blueCircle);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveTileDoesNotContainTileException() {
    setFields2.removeTile(blueCircle);
    setFields2.removeTile(blueCircle);
  }

  @Test
  public void testRemoveAll() {
    assertEquals(init1.getHand(), new ArrayList<>());
    assertEquals(init1.removeAll(), new ArrayList<>());
    assertEquals(init1.getHand(), new ArrayList<>());

    List<TileObject> initTileList = new ArrayList<>(tileList);
    assertEquals(setFields2.getHand(), initTileList);
    assertEquals(setFields2.getHand().size(), 3);
    assertEquals(setFields2.removeAll(), initTileList);
    assertEquals(setFields2.getHand().size(), 0);
    assertEquals(setFields2.getHand(), new ArrayList<>());
  }

}
