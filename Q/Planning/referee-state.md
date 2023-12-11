GameState Interface:

Fields:

- IMap represents the board for the Q game.
- PlayersInfo represents information about each player in the game including
their id, points, and tiles in their hand.
- List\<Tiles\> will represent the tiles in the referee's hand.

Public Operations:

- initializeGame(List\<Integer\>) will initialize the game with new playerState with the given ids
and also deal the playerState their starting hand and set up the game board.

- void runPassTurn() will run a pass turn.

- void runExchangeTurn() will run an exchange turn.

- void runPlaceTurn(List\<Entry\<Place, Tile\>\>) will run a place turn
with the given list of placements.

- boolean isPlacementValid(Tile, Place) will check if a placement is valid.

- int scoreTurn(List\<Entry\<Place, Tile\>\>) will score the given placements.

- void kickPlayer(int) will remove the player from the list of playerState in the game state

Interaction Protocol:

- Referee: initializeGame() will be called once we have enough playerState and will 
initialize the game with the player at each socket

- GameState: initializeGame(List<Integer> sockets) will build the initial board
and create playerState with the given ids.

- Referee: receivePlayerTurn() will receive a turn from the active player

- Referee: runPlayerTurn(String jsonString) will send the player's turn information to the game state

- GameState: runPassTurn() or runExchangeTurn() or runPlaceTurn(Placements) will execute the 
requested move

- Referee: If runPlayerTurn() throws an exception, kickPlayer(socket) will kick the
  active player and sendKickMessage() will inform the player that they've been kicked.

- GameState: kickPlayer(String) will remove the player with the given name from
    its list of playerState

- Referee: If runPlayerTurn() does not throw an exception, runScoreTurn() will be
called to score the player's turn and add it to their points.

- GameState: scoreTurn(Placements) will be called to score the list of placements.

- Referee: isGameOver() will be called after each turn to check if the game has ended.

- GameState: checkGameOver() will check if the game is over after each turn.

- Referee: If checkGameOver() is true, sendGameOver() will be called to send each player
the ending game message.

- Referee: If checkGameOver() is false, set the next player's turn and go back to receivePlayerTurn();



