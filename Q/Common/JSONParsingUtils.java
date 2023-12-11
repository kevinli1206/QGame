package Common;

import Common.data.PublicData;
import Player.MoveType.ExchangeMove;
import Player.MoveType.PassMove;
import Player.MoveType.PlaceMove;
import Referee.RefereeConfig;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fasterxml.jackson.databind.node.TextNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;
import Common.place.Place;
import Common.players.Player;
import Common.players.PlayersInfo;
import Common.tiles.TileColor;
import Common.tiles.TileObject;
import Common.tiles.TileShape;
import Player.MoveType.IMove;
import Player.IPlayer;
import Player.ExceptionPlayer;
import Player.ExceptionPlayerWithCount;
import Player.CheatingPlayer;
import Player.player;
import Player.*;
import java.util.HashMap;
import java.util.Map;

/**
 * To parse JSON inputs
 */
public class JSONParsingUtils {

  private static final ObjectMapper om = new ObjectMapper();

  /**
   * @param node the node to parse from
   * @return the TileObject defined by the node for this parser
   */
  public static TileObject parseTileObject(JsonNode node) {
    String color = node.get("color").asText();
    String shape = node.get("shape").asText();
    return new TileObject(TileShape.get(shape), TileColor.get(color));
  }

  /**
   * parses the method sent to the proxy referee.
   */
  public static String parseMethodName(JsonNode node) {
    return node.textValue();
  }

  /**
   * @return the next array node to parse from
   */
  public static ArrayNode getJsonArrayFromJSON(JsonParser parser) throws IOException {
    parser.nextToken();
    return parser.readValueAsTree();
  }

  /**
   *
   * @return IP address of host in the client config
   */
  public static String getHostFromClientConfig(JsonNode clientConfig) {
    return clientConfig.get("host").asText();
  }

  /**
   *
   * @return wait time specified in client config
   */
  public static int getWaitFromClientConfig(JsonNode clientConfig) {
    return clientConfig.get("wait").asInt();
  }

  /**
   *
   * @return wait time specified in client config
   */
  public static boolean getQuietFromClientConfig(JsonNode clientConfig) {
    return clientConfig.get("quiet").asBoolean();
  }

  /**
   *
   * @return client config JSON object
   */
  public static JsonNode getClientOrServerConfigFromJSON(JsonParser parser) throws IOException {
    parser.nextToken();
    return parser.readValueAsTree();
  }

  /**
   * @return the number of tries for this server to find players
   */
  public static int getServerTriesFromServerConfig(JsonNode serverConfig) {
    return serverConfig.get("server-tries").asInt();
  }

  /**
   * @return the amount of time in seconds that the server waits for sign-up in one signup try
   */
  public static int getServerWaitFromServerConfig(JsonNode serverConfig) {
    return serverConfig.get("server-wait").asInt();
  }

  /**
   * @return the amount of time in seconds that the server waits for a client to send their name
   */
  public static int getServerWaitForSignupFromServerConfig(JsonNode serverConfig) {
    return serverConfig.get("wait-for-signup").asInt();
  }

  /**
   *
   * @return wait time specified in server config
   */
  public static boolean getQuietFromServerConfig(JsonNode serverConfig) {
    return serverConfig.get("quiet").asBoolean();
  }

  /**
   * @return the referee config for this server config
   */
  public static RefereeConfig getRefConfigFromServerConfig(JsonNode serverConfig) {
    return parseRefereeConfig(serverConfig.get("ref-spec"));
  }

  /**
   * @return the parsed referee config
   */
  public static RefereeConfig parseRefereeConfig(JsonNode refereeConfig) {
    Map<Place, TileObject> board = new HashMap<>(parseBoard(refereeConfig.get("state0").get("map")));
    IMap map = new map(board);
    List<TileObject> refTiles = getRefTilesFromStateNode(refereeConfig.get("state0"));
    List<Player> players = new ArrayList<>(parseAllPlayers(refereeConfig.get("state0").get("players")));
    boolean quiet = refereeConfig.get("quiet").asBoolean();
    int perTurn = refereeConfig.get("per-turn").asInt();
    JsonNode refStateConfig = refereeConfig.get("config-s");
    int qBonus = refStateConfig.get("qbo").asInt();
    int finishBonus = refStateConfig.get("fbo").asInt();
    IGameState gameState = new game_state(map, new PlayersInfo(new LinkedList<>(players)),
        refTiles, qBonus, finishBonus);
    boolean observe = refereeConfig.get("observe").asBoolean();
    return new RefereeConfig(gameState, quiet, perTurn, observe);
  }


  /**
   * @param node the node to parse from
   * @return the game state from the given JSON node for this parser
   */
  public static IGameState parseGameState(JsonNode node) {
    JsonNode nodeBoard = node.get("map");
    JsonNode nodePlayers = node.get("players");
    Map<Place, TileObject> board = new HashMap<>(parseBoard(nodeBoard));
    int numberOfRefereeTiles = node.get("tile*").asInt();
    List<Player> players = new ArrayList<>(parsePlayers(nodePlayers));
    return new game_state(new map(board), new PlayersInfo(new LinkedList<>(players)), numberOfRefereeTiles);
  }

  /**
   * @param methodNode the node to parse from
   * @return the game state
   */
  public static PublicData parsePublicDataFromParser(JsonNode methodNode) {
    return JSONParsingUtils.parseGameState(methodNode).getPublicData();
  }

  /**
   * @param tilesNode the node to parse from
   * @return the list of tiles from this parser
   */
  public static List<TileObject> parseHandFromParser(JsonNode tilesNode) {
    return JSONParsingUtils.parseRefereeTiles(tilesNode);
  }

  /**
   * @param booleanNode the node to parse from
   * @return the boolean from this parser
   */
  public static boolean parseBooleanFromParser(JsonNode booleanNode) {
    return booleanNode.asBoolean();
  }

  /**
   * @param move the move to parse
   * @return the move as a JChoice
   */
  public static JsonNode parseMove(IMove move) {
    if (move instanceof PassMove) {
      return new TextNode("pass");
    }
    else if (move instanceof ExchangeMove) {
      return new TextNode("replace");
    }
    else {
      return placementsToArrayNode(move.getPlacements());
    }
  }

  /**
   * @param placements the given placements
   * @return the placements as a JPlacements array
   */
  public static ArrayNode placementsToArrayNode(List<Entry<Place, TileObject>> placements) {
    ArrayNode placementsNode = om.createArrayNode();
    for (Entry<Place, TileObject> entry : placements) {
      placementsNode.add(placementToObjectNode(entry));
    }
    return placementsNode;
  }

  /**
   * @param placement the given placement
   * @return the placement as an ObjectNode
   */
  public static ObjectNode placementToObjectNode(Entry<Place, TileObject> placement) {
    ObjectNode placementNode = om.createObjectNode();
    placementNode.set("coordinate", placeToObjectNode(placement.getKey()));
    placementNode.set("1tile", tileToObjectNode(placement.getValue()));
    return placementNode;
  }

  /**
   * @param node the JState node to parse from
   * @return the state from the given JSON node for this parser
   */
  public static IGameState parseState(JsonNode node) {
    IMap mapState = getMapFromStateNode(node);
    List<TileObject> refTiles = getRefTilesFromStateNode(node);
    JsonNode nodePlayers = node.get("players");
    List<Player> players = new ArrayList<>(parseAllPlayers(nodePlayers));
    return new game_state(mapState, new PlayersInfo(new LinkedList(players)), refTiles);
  }

  /**
   * @return gets map from a given JState JsonNode
   */
  public static IMap getMapFromStateNode(JsonNode node) {
    JsonNode nodeMap = node.get("map");
    Map<Place, TileObject> map = new HashMap<>(parseBoard(nodeMap));
    return new map(map);
  }

  /**
   * @return list of referee tiles from given JState node
   */
  public static List<TileObject> getRefTilesFromStateNode(JsonNode node) {
    JsonNode nodeRefTiles = node.get("tile*");
    return parseRefereeTiles(nodeRefTiles);
  }



  /**
   * gets the list of players by parsing JActorSpecB
   */
  public static List<IPlayer> getPlayersFromJActorSpecB(JsonParser jsonParser) throws IOException {
    jsonParser.nextToken();
    JsonNode jActorsBNode = jsonParser.readValueAsTree();
    return JSONParsingUtils.parseJActorsB(jActorsBNode);
  }

  /**
   * @param nodeRefTiles the jsonArray with all the referee tiles
   * @return the list of referee tile objects for this parser
   */
  public static List<TileObject> parseRefereeTiles(JsonNode nodeRefTiles) {
    List<TileObject> refTiles = new ArrayList<>();
    Iterator<JsonNode> placementsIterator = nodeRefTiles.elements();
    while (placementsIterator.hasNext()) {
      TileObject tile = parseTileObject(placementsIterator.next());
      refTiles.add(tile);
    }
    return refTiles;
  }

  /**
   * @param node the node to parse from
   * @return the list of players from the given node for this parser
   */
  public static List<Player> parseAllPlayers(JsonNode node) {
    List<Player> players = new ArrayList<>();

    Iterator<JsonNode> playersIterator = node.elements();
    while (playersIterator.hasNext()) {
      JsonNode activePlayer = playersIterator.next();
      List<TileObject> hand = new ArrayList<>();
      int score = activePlayer.get("score").asInt();
      String name = activePlayer.get("name").asText();
      Iterator<JsonNode> activePlayerIterator = activePlayer.get("tile*").elements();
      while (activePlayerIterator.hasNext()) {
        TileObject tile = parseTileObject(activePlayerIterator.next());
        hand.add(tile);
      }
      players.add(new Player(score, hand, name));
    }

    return players;
  }

  /**
   * @param node the node to parse from
   * @return the list of real players from the given node for this parse
   */
  public static List<IPlayer> parseJActors(JsonNode node) {
    List<IPlayer> realPlayers = new ArrayList<>();
    Iterator<JsonNode> realPlayersIterator = node.elements();
    while (realPlayersIterator.hasNext()) {
      JsonNode currentPlayerNode = realPlayersIterator.next();
      realPlayers.add(parseJActorSpec(currentPlayerNode));
    }
    return realPlayers;
  }

  /**
   * @param node the node to parse from
   * @return the list of real players from the given node for this parse
   */
  public static List<IPlayer> parseJActorsA(JsonNode node) {
    List<IPlayer> realPlayers = new ArrayList<>();
    Iterator<JsonNode> realPlayersIterator = node.elements();
    while (realPlayersIterator.hasNext()) {
      JsonNode currentPlayerNode = realPlayersIterator.next();
      realPlayers.add(parseJActorSpecA(currentPlayerNode));
    }
    return realPlayers;
  }

  /**
   * @param node the node to parse from
   * @return the list of real players from the given node for this parse
   */
  public static List<IPlayer> parseJActorsB(JsonNode node) {
    List<IPlayer> realPlayers = new ArrayList<>();
    Iterator<JsonNode> realPlayersIterator = node.elements();
    while (realPlayersIterator.hasNext()) {
      JsonNode currentPlayerNode = realPlayersIterator.next();
      realPlayers.add(parseJActorSpecB(currentPlayerNode));
    }
    return realPlayers;
  }

  /**
   * @param node the node to parse from
   * @return the player from the given node for this parser
   */
  public static IPlayer parseJActorSpec(JsonNode node) {
    int size = node.size();
    String name = node.get(0).asText();
    String strategy = node.get(1).asText();

    strategy playerStrategy;
    if (strategy.equals("ldasg")) {
      playerStrategy = new ldasg();
    }
    else {
      playerStrategy = new dag();
    }
    if (size == 3) {
      String extension = node.get(2).asText();
      return new ExceptionPlayer(extension, name, playerStrategy);
    }
    else {
      return new player(name, playerStrategy);
    }
  }

  /**
   * @param node the node to parse from
   * @return the player from the given node for this parser
   */
  public static IPlayer parseJActorSpecA(JsonNode node) {
    int size = node.size();
    String name = node.get(0).asText();
    String strategy = node.get(1).asText();
    strategy playerStrategy;
    if (strategy.equals("ldasg")) {
      playerStrategy = new ldasg();
    }
    else {
      playerStrategy = new dag();
    }
    if (size == 3) {
      String extension = node.get(2).asText();
      return new ExceptionPlayer(extension, name, playerStrategy);
    }
    else if (size == 4) {
      if (!node.get(2).asText().equals("a cheat")) {
        throw new IllegalArgumentException("Invalid JActorSpecA");
      }
      String cheat = node.get(3).asText();
      return new CheatingPlayer(cheat, name, playerStrategy);
    }
    else {
      return new player(name, playerStrategy);
    }
  }

  /**
   * @param node the node to parse from
   * @return the player from the given node for this parser
   */
  public static IPlayer parseJActorSpecB(JsonNode node) {
    int size = node.size();
    String name = node.get(0).asText();
    String strategy = node.get(1).asText();
    strategy playerStrategy;
    if (strategy.equals("ldasg")) {
      playerStrategy = new ldasg();
    }
    else {
      playerStrategy = new dag();
    }
    if (size == 3) {
      String extension = node.get(2).asText();
      return new ExceptionPlayer(extension, name, playerStrategy);
    }
    else if (size == 4) {
      if (node.get(2).asText().equals("a cheat")) {
        String cheat = node.get(3).asText();
        return new CheatingPlayer(cheat, name, playerStrategy);
      }
      else {
        String extension = node.get(2).asText();
        int count = node.get(3).asInt();
        return new ExceptionPlayerWithCount(extension, name, playerStrategy, count);
      }
    }
    else {
      return new player(name, playerStrategy);
    }
  }

  /**
   * @param node the node to parse from
   * @return a map of places to tile objects based on the given node for this parser
   */
  public static Map<Place, TileObject> parseBoard(JsonNode node) {
    Map<Place, TileObject> board = new HashMap<>();

    Iterator<JsonNode> mapIterator = node.elements();
    while (mapIterator.hasNext()) {
      JsonNode jRow = mapIterator.next();
      Iterator<JsonNode> rowIterator = jRow.elements();

      int rowIndex = rowIterator.next().asInt();
      while (rowIterator.hasNext()) {
        JsonNode jCell = rowIterator.next();
        int columnIndex = jCell.get(0).asInt();
        TileObject tileObj = parseTileObject(jCell.get(1));
        board.put(new Place(columnIndex, rowIndex), tileObj);
      }
    }
    return board;
  }

  /**
   * @param node the node to parse from. only parses the tiles of the FIRST ACTIVE PLAYER
   * @return a list of players parsed from the node for this parser
   */
  public static List<Player> parsePlayers(JsonNode node) {
    List<Player> players = new ArrayList<>();

    Iterator<JsonNode> playersIterator = node.elements();
    JsonNode activePlayer = playersIterator.next();

    List<TileObject> hand = new ArrayList<>();
    int score = activePlayer.get("score").asInt();
    String name = activePlayer.get("name").asText();

    Iterator<JsonNode> activePlayerIterator = activePlayer.get("tile*").elements();
    while (activePlayerIterator.hasNext()) {
      TileObject tile = parseTileObject(activePlayerIterator.next());
      hand.add(tile);
    }

    players.add(new Player(score, hand, name));

    while (playersIterator.hasNext()) {
      Player p = new Player(playersIterator.next().asInt(), new ArrayList<>());
      players.add(p);
    }

    return players;
  }

  /**
   * @param jsonParser the parser to use
   * @return the value of reading void
   * @throws IOException if reading failed
   */
  public static String parseVoid(JsonParser jsonParser) throws IOException {
    jsonParser.nextToken();
    JsonNode node = jsonParser.readValueAsTree();
    return node.asText();
  }

  /**
   * gets the game state from the jsonParser by parsing JState
   */
  public static IGameState getStateFromJState(JsonParser jsonParser) throws IOException {
    jsonParser.nextToken();
    JsonNode stateNode = jsonParser.readValueAsTree();
    return JSONParsingUtils.parseState(stateNode);
  }

  /**
   * gets the list of players by parsing JActorSpecA
   */
  public static List<IPlayer> getPlayersFromJActorSpec(JsonParser jsonParser) throws IOException {
    jsonParser.nextToken();
    JsonNode jActorsANode = jsonParser.readValueAsTree();
    return JSONParsingUtils.parseJActorsA(jActorsANode);
  }


  /**
   * @return a jsonParser with a given inputstream
   */
  public static JsonParser getJsonParserFromInputStream(InputStream inputStream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    JsonFactory jsonFactory = new JsonFactory();
    jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    ObjectMapper om = new ObjectMapper(jsonFactory);
    JsonParser jp = jsonFactory.createParser(reader);
    jp.setCodec(om);
    return jp;
  }

  /**
   * @return a jsonParser with a given BufferedReader
   */
  public static JsonParser getJsonParserFromBufferedReader(BufferedReader reader) throws IOException {
    JsonFactory jsonFactory = new JsonFactory();
    jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    jsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
    ObjectMapper om = new ObjectMapper(jsonFactory);
    JsonParser jp = jsonFactory.createParser(reader);
    jp.setCodec(om);
    return jp;
  }

  /**
   * @param node the node to parse from
   * @return a list of placements parsed from the given node for this parser
   */
  public static List<Entry<Place, TileObject>> parsePlacements(JsonNode node) {
    List<Entry<Place, TileObject>> placements = new ArrayList<>();
    Iterator<JsonNode> placementsIterator = node.elements();
    while (placementsIterator.hasNext()) {
      JsonNode placement = placementsIterator.next();
      Place p = parseCoordinateObject(placement.get("coordinate"));
      TileObject tile = parseTileObject(placement.get("1tile"));
      placements.add(new SimpleEntry<>(p, tile));
    }
    return placements;
  }

  /**
   * @param node the node to parse from
   * @return a place from the given coordinates parsed from the given node for this parser
   */
  public static Place parseCoordinateObject(JsonNode node) {
    int row = node.get("row").asInt();
    int col = node.get("column").asInt();
    return new Place(col, row);
  }

  /**
   * @param node the node to parse from
   * @return the name of the strategy parsed from the given node for this parser
   */
  public static String parseStrategy(JsonNode node) {
    return node.asText();
  }

  /**
   * @param publicData public data about the current gamestate
   * @param newHand the new hand to be given to the player
   * @return the function call of setup as an array node
   */
  public static ArrayNode parseSetUp(PublicData publicData, List<TileObject> newHand) {
    ArrayNode setUpFunctionNode = om.createArrayNode();
    setUpFunctionNode.add("setup");
    setUpFunctionNode.add(parseSetUpArguments(publicData, newHand));
    return setUpFunctionNode;
  }

  /**
   * @param publicData public data about the current gamestate
   * @param hand the current player's hand
   * @return the function call of take turn as an array node
   */
  public static ArrayNode parseTakeTurn(PublicData publicData, List<TileObject> hand) {
    ArrayNode setUpFunctionNode = om.createArrayNode();
    setUpFunctionNode.add("take-turn");
    setUpFunctionNode.add(parseTakeTurnArguments(publicData, hand));
    return setUpFunctionNode;
  }

  /**
   * @param hand the new hand to be set
   * @return the function call of new-tiles as an array node
   */
  public static ArrayNode parseNewTiles(List<TileObject> hand) {
    ArrayNode newTilesFunctionNode = om.createArrayNode();
    newTilesFunctionNode.add("new-tiles");
    newTilesFunctionNode.add(parseNewTileArguments(hand));
    return newTilesFunctionNode;
  }

  /**
   * @param win if this player won
   * @return the function call of win as an array node
   */
  public static ArrayNode parseWin(boolean win) {
    ArrayNode winFunctionNode = om.createArrayNode();
    winFunctionNode.add("win");
    winFunctionNode.add(parseWinArguments(win));
    return winFunctionNode;
  }


  /**
   * @param jsonParser the json parser
   * @return the move taken
   * @throws IOException if reading/writing failed
   */
  public static IMove parseJChoice(JsonParser jsonParser) throws IOException {
    jsonParser.nextToken();
    JsonNode jsonNode = jsonParser.readValueAsTree();
    if (jsonNode.asText().equals("pass")) {
      return new PassMove();
    } else if (jsonNode.asText().equals("replace")) {
      return new ExchangeMove();
    }
    else {
      return new PlaceMove(parsePlacements(jsonNode));
//    } else {
//      throw new IllegalArgumentException();
    }
  }

  /**
   * @param publicData public data about the current gamestate
   * @param newHand the new hand to be given to the player
   * @return the arguments of a setup function call as an array node
   */
  public static ArrayNode parseSetUpArguments(PublicData publicData,
      List<TileObject> newHand) {
    ArrayNode setUpFunctionArgumentsNode = om.createArrayNode();
    setUpFunctionArgumentsNode.add(parsePublicData(publicData, newHand));
    setUpFunctionArgumentsNode.add(listOfTilesToArrayNode(newHand));
    return setUpFunctionArgumentsNode;
  }

  /**
   * @param publicData public data about the current gamestate
   * @param newHand the new hand to be given to the player
   * @return the arguments of a take turn function call as an array node
   */
  public static ArrayNode parseTakeTurnArguments(PublicData publicData,
      List<TileObject> newHand) {
    ArrayNode takeTurnFunctionArgumentsNode = om.createArrayNode();
    takeTurnFunctionArgumentsNode.add(parsePublicData(publicData, newHand));
    return takeTurnFunctionArgumentsNode;
  }

  /**
   * @param hand the new hand to be given to the player
   * @return the arguments of a new tiles function call as an array node
   */
  public static ArrayNode parseNewTileArguments(List<TileObject> hand) {
    ArrayNode newTilesFunctionArgumentsNode = om.createArrayNode();
    newTilesFunctionArgumentsNode.add(listOfTilesToArrayNode(hand));
    return newTilesFunctionArgumentsNode;
  }

  /**
   * @param win if this player has won
   * @return the arguments of a win function call as an array node
   */
  public static ArrayNode parseWinArguments(boolean win) {
    ArrayNode winFunctionArgumentsNode = om.createArrayNode();
    winFunctionArgumentsNode.add(win);
    return winFunctionArgumentsNode;
  }

  /**
   * @param publicData public data about the current gamestate
   * @param currentHand the player's current hand
   * @return a JPub object node from the given inputs
   */
  public static ObjectNode parsePublicData(PublicData publicData, List<TileObject> currentHand) {
    ObjectNode jPubObjectNode = om.createObjectNode();
    ArrayNode mapNode = mapToArrayNode(publicData.getBoard().getMapState());
    ArrayNode playersNode = publicPlayerInfoToArrayNode(publicData.getPublicPlayerInfo(), currentHand);
    jPubObjectNode.put("map", mapNode);
    jPubObjectNode.put("tile*", publicData.getTilesLeft());
    jPubObjectNode.put("players", playersNode);
    return jPubObjectNode;
  }

  /**
   * @param namesAndScores the queue of player names to scores
   * @param currentHand the current player's hand
   * @return the ArrayNode representing the current player
   * and the scores of the rest of the players
   */
  public static ArrayNode publicPlayerInfoToArrayNode(Queue<Entry<String, Integer>> namesAndScores,
      List<TileObject> currentHand) {
    ArrayNode playersNode = om.createArrayNode();
    int current = 0;
    for (Entry<String, Integer> nameAndScore : namesAndScores) {
      if (current == 0) {
        playersNode.add(playerToObjectNode(nameAndScore, currentHand));
      }
      else {
        playersNode.add(nameAndScore.getValue());
      }
      current++;
    }
    return playersNode;
  }


  /**
   * @return the given place move as an object node of its placements for this parser
   */
  public static ObjectNode placeMoveToObjectNode(IMove move) {
    ObjectNode placeMove = om.createObjectNode();

    placeMove.set("coordinate", placeToObjectNode(move.getPlacements().get(0).getKey()));
    placeMove.set("1tile", tileToObjectNode(move.getPlacements().get(0).getValue()));

    return placeMove;
  }

  /**
   * @param gameState the given game state
   * @return the JState json representation of a game state
   */
  public static ObjectNode gameStateToObjectNode(IGameState gameState) {
    ObjectNode gameStateNode = om.createObjectNode();
    ArrayNode mapNode = mapToArrayNode(gameState.getPublicData().getBoard().getMapState());
    ArrayNode refTilesNode = listOfTilesToArrayNode(gameState.getRefTiles());
    ArrayNode playersNode = playersToArrayNode(gameState);
    gameStateNode.put("map", mapNode);
    gameStateNode.put("tile*", refTilesNode);
    gameStateNode.put("players", playersNode);
    return gameStateNode;
  }

  /**
   * @param refereeTiles the given referee's tiles
   * @return the array representation of the referee's tiles
   */
  public static ArrayNode listOfTilesToArrayNode(List<TileObject> refereeTiles) {
    ArrayNode refTilesNode = om.createArrayNode();
    for (TileObject to : refereeTiles) {
      refTilesNode.add(tileToObjectNode(to));
    }
    return refTilesNode;
  }

  /**
   * @param gameState the given game state
   * @return the array representation of players in the given game state
   */
  public static ArrayNode playersToArrayNode(IGameState gameState) {
    ArrayNode playersNode = om.createArrayNode();
    Queue<Entry<String, Integer>> pubPlayerInfo = gameState.getPublicData().getPublicPlayerInfo();
    for (Entry<String, Integer> nameAndScore : pubPlayerInfo) {
      playersNode.add(playerToObjectNode(nameAndScore,
          gameState.getPrivateData(nameAndScore.getKey()).getPlayerHand()));
    }
    return playersNode;
  }

  /**
   * @param nameAndScore the given name and score of a player
   * @param playerHand the player's hand
   * @return the object representation of a player
   */
  public static ObjectNode playerToObjectNode(Entry<String, Integer> nameAndScore,
      List<TileObject> playerHand) {
    ObjectNode playerNode = om.createObjectNode();
    playerNode.put("score", nameAndScore.getValue());
    playerNode.put("name", nameAndScore.getKey());
    playerNode.put("tile*", listOfTilesToArrayNode(playerHand));
    return playerNode;
  }

  /**
   * @return the ObjectNode representation of a place by its row and column indices for this parser
   */
  public static ObjectNode placeToObjectNode(Place place) {
    ObjectNode placeNode = om.createObjectNode();
    placeNode.put("row", place.getY());
    placeNode.put("column", place.getX());
    return placeNode;
  }

  /**
   * Get the node representing the given board for this parser
   */
  public static ArrayNode mapToArrayNode(Map<Place, TileObject> board) {
    ArrayNode mapNode = om.createArrayNode();

    Map<Integer, Map<Integer, TileObject>> rowToColToTile = new TreeMap<>();

    for (Entry<Place, TileObject> entry : board.entrySet()) {
      if (!rowToColToTile.containsKey(entry.getKey().getY())) {
        rowToColToTile.put(entry.getKey().getY(), new TreeMap<>());
      }
      rowToColToTile.get(entry.getKey().getY()).put(entry.getKey().getX(), entry.getValue());
    }

    for (Entry<Integer, Map<Integer, TileObject>> entry: rowToColToTile.entrySet()) {
      ArrayNode rowNode = rowToArrayNode(entry);
      mapNode.add(rowNode);
    }

    return mapNode;
  }

  /**
   * Get the node representing the given row entry for this parser
   */
  public static ArrayNode rowToArrayNode(Entry<Integer, Map<Integer, TileObject>> entry) {
    ArrayNode rowNode = om.createArrayNode();

    rowNode.add(entry.getKey());
    for (Entry<Integer, TileObject> colEntry : entry.getValue().entrySet()) {
      ArrayNode colNode = colToArrayNode(colEntry);
      rowNode.add(colNode);
    }

    return rowNode;
  }

  /**
   * Get the node representing the given column entry for this parser
   */
  public static ArrayNode colToArrayNode(Entry<Integer, TileObject> entry) {
    ArrayNode colNode = om.createArrayNode();

    colNode.add(entry.getKey());
    ObjectNode tileNode = tileToObjectNode(entry.getValue());
    colNode.add(tileNode);

    return colNode;
  }

  /**
   * Get the node representing the given tile entry for this parser
   */
  public static ObjectNode tileToObjectNode(TileObject tile) {
    ObjectNode tileNode = om.createObjectNode();

    tileNode.put("color", tile.getColor().getName());
    tileNode.put("shape", tile.getShape().getName());

    return tileNode;
  }

  /**
   * @param winnersAndKickedPlayers the list of winning players and list of misbehaving players
   * @return the node representing the list of winners and dropout players for this parser
   */
  public static ArrayNode endGameOutputToArrayNode(
          Entry<List<String>, List<String>> winnersAndKickedPlayers) {
    ArrayNode endResults = om.createArrayNode();
    endResults.add(parseListOfNames(winnersAndKickedPlayers.getKey()));
    endResults.add(parseListOfNames(winnersAndKickedPlayers.getValue()));
    return endResults;
  }

  /**
   * @param names the list of names
   * @return the node representing the list of names for this parser
   */
  public static ArrayNode parseListOfNames(List<String> names) {
    ArrayNode namesList = om.createArrayNode();
    for (String name : names) {
      namesList.add(name);
    }
    return namesList;
  }
}
