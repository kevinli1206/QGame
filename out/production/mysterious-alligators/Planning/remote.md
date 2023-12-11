Server Class:
- actively listens for players to join the port.
- newProxyPlayer() wil create a new proxy player for that actual player who signed on

Client Class:
- Represents a client who may wish to play our game
- String name() send the server their name

Referee Class:
- runGame(List<IPlayer>) will run a game with the given players

Proxy Player Class implements IPlayer:
- Reader: to read JSON inputs
- Writer: to write data as JSON 
- int port: the connecting port
- Contains all Player API Methods


Proxy Referee Class:
- Reader: to read JSON inputs
- Writer: to write data as JSON
- int port: the connecting port

Real Player Class:

- Contains all Player API Methods

Gather Player Protocol
- Server: will be initialized and wait for players to connect
- Client: Once a client connects to the server, name() will send the server their name
- Server: Once a server receives a name, they will call newProxyPlayer() to create a proxy player for the client that connected
- Server: Once the signing up period is over, the server will call referee to run a game with the given proxy players


Run Game Protocol
- SettingUp:
- Referee: will tell each ProxyPlayer to setUp(Map, Hand)
- ProxyPlayer: encode(Map, Hand) will encode the given setup method to JSON
- ProxyPlayer: dispatch(JSON) will dispatch a JSON message saying this player needs to setup with the given player info
- ProxyReferee: receive() will receive the setUp JSON message from the ProxyPlayer
- ProxyReferee: decode(JSON) will convert the message into real data
- RealPlayer: setUp(Map, Hand) will be called by the proxy referee with the converted JSON values
- ProxyReferee: encode(Map, Hand) will encode the given result to JSON
- ProxyReferee: dispatch(JSON) will send the results of setUp to the ProxyPlayer
- ProxyPlayer: receive() will receive the results of setUp
- ProxyPlayer: decode(JSON) will decode the results from the ProxyReferee
- ProxyPlayer: setUp(Map, Hand) method will finish running and return void
- ProxyPlayer: if setUp(Map, Hand) throws an error or takes too long to run,
- Referee: kickPlayer()
- RunningRounds will be called while the game is not over:
- Referee: will tell all Proxy Players to takeTurn(PublicData)
- ProxyPlayer: encode(PublicData) will encode the given takeTurn method to JSON
- ProxyPlayer: dispatch(JSON) will dispatch a JSON message saying this player needs to take a turn
- ProxyReferee: receive() will receive the takeTurn JSON message from the ProxyPlayer
- ProxyReferee: decode(JSON) will convert the message into real data
- RealPlayer: takeTurn(PublicData) will take a turn
- ProxyReferee: encode(PublicData) will encode the given result to JSON
- ProxyReferee: dispatch(JSON) will send the results of takeTurn to the ProxyPlayer
- ProxyPlayer: receive() will get the results of takeTurn from the ProxyReferee
- ProxyPlayer: decode(JSON) will decode the results from the ProxyReferee
- ProxyPlayer: takeTurn(PublicData) will finish running and return an IMove
- Referee: will make the IMove on its gamestate
- ProxyPlayer: if takeTurn(publicData) throws an error or takes too long to run,
- Referee: kickPlayer()

Report Results Protocol

- Referee: will tell each Proxy Player win(boolean) once the game has ended
- ProxyPlayer: encode(boolean) will encode the given win method to JSON
- ProxyPlayer: dispatch(JSON) will dispatch a JSON message saying this player won or not
- ProxyReferee: receive() will receive the takeTurn JSON message from the ProxyPlayer
- ProxyReferee: decode(JSON) will convert the message into real data
- RealPlayer: win(boolean) will call win to be informed of the results
- ProxyReferee: encode(boolean) will encode the given result to JSON
- ProxyReferee: dispatch(JSON) will send the results of to ProxyPlayer
- ProxyPlayer: receive() will get the results from the ProxyReferee
- ProxyPlayer: decode(JSON) will decode the results from the ProxyReferee
- ProxyPlayer: win(boolean) will finish running
- Referee: if win(boolean) throws an error or takes too long to run,
- Referee: kickPlayer()
- 
- Server: shutdown() will close all the ports and shutdown the server after the referee has sent the results to each player


