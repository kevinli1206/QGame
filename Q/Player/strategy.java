package Player;

import Common.data.PrivateData;
import Common.data.PublicData;
import Player.MoveType.IMove;

/**
 * To represent strategies that a player can apply
 */
public interface strategy {

  /**
   * @return the move type and possibly a placement that this strategy decides to place
   */
  IMove applyStrategy(PublicData gameStateData, PrivateData playerData);

  /**
   * @return a move that this strategy will attempt to run over a player's entire turn
   */
  IMove applyStrategyIterator(PublicData gameStateData, PrivateData playerData);

  /**
   * resets the strategy's seen placements
   */
  void reset();

}
