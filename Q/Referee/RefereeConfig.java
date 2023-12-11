package Referee;

import Common.IGameState;

/**
 * To represent the configurations for a referee
 */
public class RefereeConfig {

  private final IGameState gameState;
  private final boolean quiet;
  private final int perTurn;
  private final boolean observe;

  /**
   * RefereeConfig constructor
   * @param gameState the gamestate
   * @param quiet whether we want the debug port run
   * @param perTurn the seconds to wait for player input
   * @param observe whether we want an observer attached or not
   */
  public RefereeConfig(IGameState gameState, boolean quiet, int perTurn, boolean observe) {
    this.gameState = gameState;
    this.quiet = quiet;
    this.perTurn = perTurn;
    this.observe = observe;
  }

  /**
   * @return a referee from this config's fields
   */
  public IReferee createReferee() {
    return new referee(this.observe, this.perTurn, this.quiet);
  }

  public IGameState getGameState() {
    return this.gameState;
  }
}
