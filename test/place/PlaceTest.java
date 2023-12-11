package place;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import Common.place.Place;
import org.junit.Before;
import org.junit.Test;

/**
 * To test the methods in the Place class.
 */
public class PlaceTest {

  Place place1;
  Place place2;
  Place place3;
  Place place4;

  @Before
  public void initData() {
    place1 = new Place(0, 0);
    place2 = new Place(-100, 50);
    place3 = new Place(1, -9);
    place4 = new Place(0, 0);
  }

  @Test
  public void testGetters() {
    assertEquals(place1.getX(), 0);
    assertEquals(place1.getY(), 0);
    assertEquals(place2.getX(), -100);
    assertEquals(place2.getY(), 50);
    assertEquals(place3.getX(), 1);
    assertEquals(place3.getY(), -9);
  }

  @Test
  public void testEquals() {
    assertNotEquals(place1, place2);
    assertNotEquals(place1, place3);
    assertEquals(place1, place4);
    Place newPlace = new Place(place2.getX(), place2.getY());
    assertEquals(place2, newPlace);
  }

  @Test
  public void testHashCode() {
    assertEquals(place1.hashCode(), 961);
    assertEquals(place2.hashCode(), -2089);
    assertEquals(place3.hashCode(), 983);
  }

  @Test
  public void testGetCardinalNeighbors() {
    Place[] place1Neighbors = new Place[4];
    place1Neighbors[0] = new Place(0, 1);
    place1Neighbors[1] = new Place(0, -1);
    place1Neighbors[2] = new Place(1, 0);
    place1Neighbors[3] = new Place(-1, 0);
    assertArrayEquals(place1.getCardinalNeighbors(), place1Neighbors);
  }

  @Test
  public void testGetColumnNeighbors() {
    Place[] place1ColumnNeighbors = new Place[2];
    place1ColumnNeighbors[0] = new Place(0, 1);
    place1ColumnNeighbors[1] = new Place(0, -1);
    assertArrayEquals(place1.getColumnNeighbors(), place1ColumnNeighbors);
    Place[] place2ColumnNeighbors = new Place[2];
    place2ColumnNeighbors[0] = new Place(-100, 51);
    place2ColumnNeighbors[1] = new Place(-100, 49);
    assertArrayEquals(place2.getColumnNeighbors(), place2ColumnNeighbors);
    Place[] place3ColumnNeighbors = new Place[2];
    place3ColumnNeighbors[0] = new Place(1, -8);
    place3ColumnNeighbors[1] = new Place(1, -10);
    assertArrayEquals(place3.getColumnNeighbors(), place3ColumnNeighbors);
  }

  @Test
  public void testGetRowNeighbors() {
    Place[] place1RowNeighbors = new Place[2];
    place1RowNeighbors[0] = new Place(-1, 0);
    place1RowNeighbors[1] = new Place(1, 0);
    assertArrayEquals(place1.getRowNeighbors(), place1RowNeighbors);
    Place[] place2RowNeighbors = new Place[2];
    place2RowNeighbors[0] = new Place(-101, 50);
    place2RowNeighbors[1] = new Place(-99, 50);
    assertArrayEquals(place2.getRowNeighbors(), place2RowNeighbors);
    Place[] place3RowNeighbors = new Place[2];
    place3RowNeighbors[0] = new Place(0, -9);
    place3RowNeighbors[1] = new Place(2, -9);
    assertArrayEquals(place3.getRowNeighbors(), place3RowNeighbors);
  }

  @Test
  public void testGetNeighborUp() {
    assertEquals(place1.getNeighborUp(), new Place(0, -1));
    assertEquals(place2.getNeighborUp(), new Place(-100, 49));
    assertEquals(place3.getNeighborUp(), new Place(1, -10));
  }

  @Test
  public void testGetNeighborDown() {
    assertEquals(place1.getNeighborDown(), new Place(0, 1));
    assertEquals(place2.getNeighborDown(), new Place(-100, 51));
    assertEquals(place3.getNeighborDown(), new Place(1, -8));
  }

  @Test
  public void testGetNeighborRight() {
    assertEquals(place1.getNeighborRight(), new Place(1, 0));
    assertEquals(place2.getNeighborRight(), new Place(-99, 50));
    assertEquals(place3.getNeighborRight(), new Place(2, -9));
  }

  @Test
  public void testGetNeighborLeft() {
    assertEquals(place1.getNeighborLeft(), new Place(-1, 0));
    assertEquals(place2.getNeighborLeft(), new Place(-101, 50));
    assertEquals(place3.getNeighborLeft(), new Place(0, -9));
  }

}