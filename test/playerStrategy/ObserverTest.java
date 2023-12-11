package playerStrategy;

import Common.IGameState;
import Common.game_state;
import Referee.observer;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the observer
 */
public class ObserverTest {
  observer obs1;
  observer obs2;
  observer obs3;

  IGameState gs1;

  @Before
  public void initData() {
    gs1 = new game_state();
    List<IGameState> listGs1 = new ArrayList<>();
    listGs1.add(gs1);
    obs1 = new observer();
    obs2 = new observer(listGs1);
    List<IGameState> listGs2 = new ArrayList<>();
    listGs2.add(gs1);
    listGs2.add(gs1);
    obs3 = new observer(listGs2);
  }

  @Test
  public void testInitialValues() {
    assertEquals(obs1.getGameStates().size(), 0);
    assertEquals(obs2.getGameStates().size(), 1);
    assertEquals(obs3.getGameStates().size(), 2);
    assertEquals(obs1.getCurrentState(), 0);
    assertEquals(obs2.getCurrentState(), 0);
    assertEquals(obs3.getCurrentState(), 0);
    assertFalse(obs1.getGameOver());
    assertFalse(obs2.getGameOver());
    assertFalse(obs3.getGameOver());
  }

  @Test
  public void testAddGameStates() {
    obs3.receiveState(new game_state());
    assertEquals(obs3.getGameStates().size(), 3);
    obs3.receiveState(new game_state());
    assertEquals(obs3.getGameStates().size(), 4);
  }

  @Test
  public void testSetGameOver() {
    obs1.setGameOver();
    assertTrue(obs1.getGameOver());
    obs2.setGameOver();
    assertTrue(obs2.getGameOver());
    obs3.setGameOver();
    assertTrue(obs3.getGameOver());
  }

  @Test
  public void testMoveCurrentState() {
    obs1.setNext();
    assertEquals(obs1.getCurrentState(), 0);
    obs1.setPrevious();
    assertEquals(obs1.getCurrentState(), 0);
    obs3.setNext();
    assertEquals(obs3.getCurrentState(), 1);
    obs3.setNext();
    assertEquals(obs3.getCurrentState(), 1);
    obs3.setPrevious();
    assertEquals(obs3.getCurrentState(), 0);
    obs3.setPrevious();
    assertEquals(obs3.getCurrentState(), 0);
  }
}
