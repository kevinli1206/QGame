Player Interface

Wish List of Public Operations(in Player Interface):

public void sendTurn(Turn) will send input to the referee/server about the
type of turn that the player wants to make and any inputs necessary to make the turn.

public void sendPassTurn() will send a turn of type pass to the server to indicate
that this player is passing their turn.

public void sendExchangeTurn() will send a turn of type exchange to the server to indicate
that this player wants to exchange its tiles.

public void sendPlaceTurn(List<Entry<Place, TileObject) will send a turn of type place to the 
server to indicate that this player wants to place tiles down. The input for this method will be 
a list to indicate order and the list will contain the tiles to be placed and the places to put them down.

public void receiveData() will wait to receive data from the server side.

public boolean checkTurn(String) will take data sent back from the server and return true
if that data represents that it is this player's turn.

public boolean checkGameEnded(String) will take data sent back from the server and return true
if that data represents that the game has ended.

public void register(String name) will register this player for the Q game with
the given name.







