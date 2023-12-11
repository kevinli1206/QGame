import static org.junit.Assert.*;

import Common.IMap;
import Common.map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import Common.place.Place;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
/**
 * To test the methods in the map class
 */
public class mapTest {

  IMap map1;
  IMap map2;
  IMap map3;
  TileObject redcircle;
  TileObject yellowdiamond;
  TileObject blueeightstart;
  TileObject purplecircle;
  TileObject redclover;
  TileObject orangeclover;
  TileObject redstar;
  TileObject redsqare;
  TileObject red8star;
  TileObject reddiamond;

  Place place00;
  Place place01;
  Place place10;
  Place place11;
  Place place02;
  Place place0_1;
  Place place0_2;
  Place place_10;
  Place place_11;
  Place place_1_1;
  Place place1_1;
  Place place_21;
  Place place_20;
  Place place_12;
  Place place_22;
  Place place12;

  Place place1_2;


  Set<Place> set1;
  Set<Place> set2;
  Set<Place> set3;
  Set<Place> set4;


  @Before
  public void initData() {
    map1 = new map();
    map2 = new map();
    map3 = new map();
    redcircle = new TileObject(TileShape.CIRCLE, TileColor.RED);
    redsqare = new TileObject(TileShape.SQUARE, TileColor.RED);
    red8star = new TileObject(TileShape.EIGHT_STAR, TileColor.RED);
    reddiamond = new TileObject(TileShape.DIAMOND, TileColor.RED);
    redclover = new TileObject(TileShape.CLOVER, TileColor.RED);
    redstar = new TileObject(TileShape.STAR, TileColor.RED);
    yellowdiamond = new TileObject(TileShape.DIAMOND, TileColor.YELLOW);
    blueeightstart = new TileObject(TileShape.EIGHT_STAR, TileColor.BLUE);
    purplecircle = new TileObject(TileShape.CIRCLE, TileColor.PURPLE);
    orangeclover = new TileObject(TileShape.CLOVER, TileColor.ORANGE);
    place00 = new Place(0, 0);
    place01 = new Place(0, 1);
    place10 = new Place(1, 0);
    place11 = new Place(1, 1);
    place0_1 = new Place(0, -1);
    place0_2 = new Place(0, -2);
    place_10 = new Place(-1, 0);
    place02 = new Place(0, 2);
    place_11 = new Place(-1, 1);
    place_1_1 = new Place(-1, -1);
    place1_1 = new Place(1, -1);
    place_21 = new Place(-2, 1);
    place_20 = new Place(-2, 0);
    place_12 = new Place(-1, 2);
    place_22 = new Place(-2, 2);
    place12 = new Place(1, 2);
    place1_2 = new Place(1, -2);

    set1 = new HashSet<>();
    set2 = new HashSet<>();
    set3 = new HashSet<>();
    set4 = new HashSet<>();
    set1.add(place01);
    set1.add(place10);
    set1.add(place0_1);
    set1.add(place_10);
    set2.add(place02);
    set2.add(place_11);
    set2.add(place11);
    set4.add(place10);
    set4.add(place0_1);
    set4.add(place_10);
    set4.add(place02);
    set4.add(place_11);
    set4.add(place11);
  }

  @Test(expected = Test.None.class)
  public void testInitializeTileSuccess() {
    map1.initializeFirstTile(redcircle, place00);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInitializeTileFail() {
    map2.initializeFirstTile(redcircle, place01);
    map2.initializeFirstTile(redcircle, place01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceTileFail() {
    map1.placeTile(redcircle, place00);
  }

  @Test
  public void testPlaceTileSuccess() {
    map1.initializeFirstTile(redcircle, place00);
    map2.initializeFirstTile(yellowdiamond, place00);
    map3.initializeFirstTile(blueeightstart, place00);

    map1.placeTile(redcircle, place01);
    map1.placeTile(blueeightstart, place11);
    map2.placeTile(yellowdiamond, place0_1);
    map2.placeTile(yellowdiamond, place0_2);

  }

  @Test
  public void testGetValidPlacements() {
    assertEquals(map1.getValidPlacements(purplecircle), set3);
    assertEquals(map1.getValidPlacements(redclover), set3);
    assertEquals(map1.getValidPlacements(purplecircle), set3);
    map1.initializeFirstTile(redcircle, new Place(0, 0));
    map2.initializeFirstTile(yellowdiamond, new Place(0, 0));
    map3.initializeFirstTile(blueeightstart, new Place(0, 0));
    assertEquals(map1.getValidPlacements(orangeclover), set3);
    assertEquals(map2.getValidPlacements(orangeclover), set3);
    assertEquals(map3.getValidPlacements(orangeclover), set3);
    assertEquals(map1.getValidPlacements(purplecircle), set1);
    assertEquals(map1.getValidPlacements(redclover), set1);
    map1.placeTile(redclover, place01);
    assertEquals(map1.getValidPlacements(orangeclover), set2);
    assertEquals(map1.getValidPlacements(redcircle), set4);
    map1.placeTile(orangeclover, place_11);
    assertTrue(map1.getValidPlacements(redclover).contains(place_10));
    map1.placeTile(orangeclover, place_21);
    assertTrue(map1.getValidPlacements(redclover).contains(place_10));
    map1.placeTile(orangeclover, place_20);
    assertFalse(map1.getValidPlacements(redclover).contains(place_10));
  }

  @Test
  public void testGetMapState() {
    assertEquals(map1.getMapState(), new HashMap<>());
    map1.initializeFirstTile(redcircle, place00);
    HashMap<Place, TileObject> map1board = new HashMap<>();
    map1board.put(place00, redcircle);
    assertEquals(map1.getMapState(), map1board);
  }

  @Test
  public void testGetMinXCoordinateOnBoard() {
    map1.initializeFirstTile(redcircle, place00);
    map1.placeTile(redclover, place01);
    map1.placeTile(redstar, place11);
    assertEquals(map1.getMinXCoordinateOnMap(), 0);
    map1.placeTile(redclover, place_10);
    assertEquals(map1.getMinXCoordinateOnMap(), -1);
  }

  @Test
  public void testGetMinYCoordinateOnBoard() {
    map1.initializeFirstTile(redcircle, place00);
    map1.placeTile(redclover, place01);
    map1.placeTile(redstar, place11);
    assertEquals(map1.getMinYCoordinateOnMap(), 0);
    map1.placeTile(redclover, place_10);
    assertEquals(map1.getMinYCoordinateOnMap(), 0);
    map1.placeTile(redclover, place0_1);
    assertEquals(map1.getMinYCoordinateOnMap(), -1);
  }

  @Test
  public void testGetMaxXCoordinateOnBoard() {
    map1.initializeFirstTile(redcircle, place00);
    assertEquals(map1.getMaxXCoordinateOnMap(), 0);
    map1.placeTile(redclover, place01);
    map1.placeTile(redstar, place11);
    assertEquals(map1.getMaxXCoordinateOnMap(), 1);
  }

  @Test
  public void testGetMaxYCoordinateOnBoard() {
    map1.initializeFirstTile(redcircle, place00);
    assertEquals(map1.getMaxYCoordinateOnMap(), 0);
    map1.placeTile(redclover, place01);
    map1.placeTile(redstar, place11);
    assertEquals(map1.getMaxYCoordinateOnMap(), 1);
    map1.placeTile(redclover, place02);
    assertEquals(map1.getMaxYCoordinateOnMap(), 2);
  }

  @Test
  public void testGetContiguousColumnSequence() {
    map1.initializeFirstTile(redcircle, place00);
    map1.placeTile(redclover, place01);
    map1.placeTile(redstar, place11);
    Set<Entry<Place, TileObject>> testOne = new HashSet<>();
    testOne.add(new SimpleEntry<>(place11, redstar));
    for (Entry<Place, TileObject> e : testOne) {
      Set<Entry<Place, TileObject>> expectedSeq = new HashSet<>();
      expectedSeq.add(new SimpleEntry<>(place11, redstar));
      assertEquals(map1.getContiguousColumnSequence(e), expectedSeq);
    }
    map1.placeTile(reddiamond, place0_1);
    map1.placeTile(redsqare, place1_1);
    map1.placeTile(redclover, place1_2);
    Set<Entry<Place, TileObject>> testNotConnected = new HashSet<>();
    testNotConnected.add(new SimpleEntry<>(place1_1, redsqare));
    for (Entry<Place, TileObject> e : testNotConnected) {
      Set<Entry<Place, TileObject>> expectedSeqNotConnected = new HashSet<>();
      expectedSeqNotConnected.add(new SimpleEntry<>(place1_1, redsqare));
      expectedSeqNotConnected.add(new SimpleEntry<>(place1_2, redclover));
      assertEquals(map1.getContiguousColumnSequence(e), expectedSeqNotConnected);
    }
    Set<Entry<Place, TileObject>> testBetween = new HashSet<>();
    testBetween.add(new SimpleEntry<>(place00, redcircle));
    for (Entry<Place, TileObject> e : testBetween) {
      Set<Entry<Place, TileObject>> expectedSeqBetween = new HashSet<>();
      expectedSeqBetween.add(new SimpleEntry<>(place01, redclover));
      expectedSeqBetween.add(new SimpleEntry<>(place0_1, reddiamond));
      expectedSeqBetween.add(new SimpleEntry<>(place00, redcircle));
      assertEquals(map1.getContiguousColumnSequence(e), expectedSeqBetween);
    }
  }

  @Test
  public void testGetContiguousRowSequence() {
    map1.initializeFirstTile(redcircle, place00);
    map1.placeTile(redclover, place01);
    map1.placeTile(redstar, place11);
    Set<Entry<Place, TileObject>> testPlacements = new HashSet<>();
    testPlacements.add(new SimpleEntry<>(place11, redstar));
    for (Entry<Place, TileObject> e : testPlacements) {
      Set<Entry<Place, TileObject>> expectedSeq = new HashSet<>();
      expectedSeq.add(new SimpleEntry<>(place01, redclover));
      expectedSeq.add(new SimpleEntry<>(place11, redstar));
      assertEquals(map1.getContiguousRowSequence(e), expectedSeq);
      assertEquals(map1.getContiguousRowSequence(e).size(), 2);
    }

    Set<Entry<Place, TileObject>> testOne = new HashSet<>();
    testOne.add(new SimpleEntry<>(place00, redcircle));
    for (Entry<Place, TileObject> e : testOne) {
      Set<Entry<Place, TileObject>> expectedSeqOne = new HashSet<>();
      expectedSeqOne.add(new SimpleEntry<>(place00, redcircle));
      assertEquals(map1.getContiguousRowSequence(e), expectedSeqOne);
    }
    map1.placeTile(reddiamond, place12);
    map1.placeTile(redsqare, place_11);
    map1.placeTile(redcircle, place_12);
    map1.placeTile(reddiamond, place_22);
    Set<Entry<Place, TileObject>> testNotConnected = new HashSet<>();
    testNotConnected.add(new SimpleEntry<>(place_12, redcircle));
    for (Entry<Place, TileObject> e : testNotConnected) {
      Set<Entry<Place, TileObject>> expectedSeqNotConnected = new HashSet<>();
      expectedSeqNotConnected.add(new SimpleEntry<>(place_22, reddiamond));
      expectedSeqNotConnected.add(new SimpleEntry<>(place_12, redcircle));
      assertEquals(map1.getContiguousRowSequence(e), expectedSeqNotConnected);
    }
    Set<Entry<Place, TileObject>> testBetween = new HashSet<>();
    testBetween.add(new SimpleEntry<>(place01, redclover));
    for (Entry<Place, TileObject> e : testBetween) {
      Set<Entry<Place, TileObject>> expectedBetween = new HashSet<>();
      expectedBetween.add(new SimpleEntry<>(place01, redclover));
      expectedBetween.add(new SimpleEntry<>(place11, redstar));
      expectedBetween.add(new SimpleEntry<>(place_11, redsqare));
      assertEquals(map1.getContiguousRowSequence(e), expectedBetween);
    }
  }

  @Test
  public void testGetNumberOfExistingNeighbors() {
    assertEquals(map1.getNumberOfExistingNeighbors(place01), 0);
    assertEquals(map1.getNumberOfExistingNeighbors(place10), 0);
    assertEquals(map1.getNumberOfExistingNeighbors(place_10), 0);
    assertEquals(map1.getNumberOfExistingNeighbors(place0_1), 0);
    map1.initializeFirstTile(redcircle, place00);
    assertEquals(map1.getNumberOfExistingNeighbors(place01), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place10), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place_10), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place0_1), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place00), 0);
    map1.placeTile(redclover, place01);
    assertEquals(map1.getNumberOfExistingNeighbors(place01), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place10), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place_10), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place0_1), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place00), 1);
    map1.placeTile(redclover, place11);
    assertEquals(map1.getNumberOfExistingNeighbors(place01), 2);
    assertEquals(map1.getNumberOfExistingNeighbors(place10), 2);
    assertEquals(map1.getNumberOfExistingNeighbors(place_10), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place0_1), 1);
    assertEquals(map1.getNumberOfExistingNeighbors(place00), 1);
  }

}