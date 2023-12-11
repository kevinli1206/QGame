Interaction Protocol: These methods will run in sequence.

Referee: gameIsWaitingForPlayers() is broadcast out to the playerState to let
them know that a game is ready for playerState to join.

Player: registerForGame(age, id) is sent out to the referee to let them know that this player wishes
to register.

Referee: initializeGame() is called once the game has a valid number of playerState. The referee will
determine the order the playerState will go in by age, set up the map with an initial tile and deal
out starting tiles to each player.

Referee: sendGameStarted() will send a message to each player in the game letting them know
that the game has started

Referee: sendInfoToActivePlayer(Map, PlayerInfo, refTilesLeft, TilesInHand) is sent to each player when it is their turn.
This message will include the current game state, the ref tiles left, the turn order
of the playerState as well as their scores and the tiles in this player's hand.

Player: receiveInfoFromReferee() will take in the information sent by the referee
about the gamestate in sendInfoToActivePlayer

Player: runTurn(TurnType, optional(placements)) will send a TurnType which is one of pass, exchange or place
to the referee informing them of the move the player wants to make and possibly the placements
they want to put.

Referee: receivePlayerTurn(TurnType, optional(Placements)) will receive the move info
from the player.

Referee: runPlayerTurn(TurnType, optional(Placements)) will attempt to run the move on 
the current game state.

At this point, 2 things can occur.
If runPlayerTurn is successful then...
1. Referee: if checkGameOver() is false, run setNextTurn() which will set the active player's
turn to the next player and go back to the sendInfoToActivePlayer() method.
2. Referee: if checkGameOver is true, then sendGameOver() will communicate that the 
game has ended to all the playerState and inform them of their ranking.

If runPlayerTurn is rejected then...
1. Referee: kickPlayer() will remove the player who made an unsuccessful move from the game state
2. Referee: sendKickMessage() will inform the player that they are being kicked from the game
and the referee will no longer accept any input from that player
3. Referee: if checkGameOver() is false, run setNextTurn(), otherwise, sendGameOver()

Referee: checkGameOver() will check to see if the game has ended.

