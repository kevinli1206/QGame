Data Representation:

First off, we need a class to represent our game state.
This class will contain player information and a board.
The player information class will keep track of a list of playerState that are currently in the game
and the active player.
Each player will have a list of tiles in their hand and a score along with an id.
The board will have a 2d representation of the place for playerState to put tiles.
We are choosing to represent the board with a Map<Integer, Map<Integer, Tile>> where
the outer Map key represents the row index and the inner Map key represents the column index.

Wish List of Public Operations(accessible to referee):

public void initializeGame(Tile, PlayerInfo) will create a game board with the given tile and a game with
the given playerState

public void runTurn(Player, Command) will run a single turn on the board given the command
that the player gives as input

public void distributeCards(Tiles, Player) will give out tiles to the specified player

public void placeTile(Tile, Place) will place a tile down at the specified place

public boolean checkValidMove(Player, Tile, Place) will check to see if the given player is allowed to
put the given tile in the given place on the board

public void scoreMove(Player, Tile, Place) will score the given move by the given player

public boolean hasGameEnded() will check to see if a condition that ends the game has been met

public void endGame() will end the current game and inform playerState of its outcome







