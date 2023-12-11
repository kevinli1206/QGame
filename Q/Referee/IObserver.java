package Referee;

import Common.IGameState;

/**
 * To represent an observer for the game
 */
public interface IObserver {
    /**
     * Adds the given game state to this observer's list of game states
     * and also saves each game state to the testing directory.
     * @param gameState the game state to be added
     */
    void receiveState(IGameState gameState);

    /**
     * Observer is notified that the game is now over
     */
    void setGameOver();


}

