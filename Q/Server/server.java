package Server;

import static Common.JSONParsingUtils.getJsonParserFromBufferedReader;

import Player.IPlayer;
import Referee.RefereeConfig;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * To represent a server for running our Q game
 */
public class server {
  private final ServerSocket servSocket;
  private final int serverTries;
  private final int serverWait;
  private final int waitForSignUp;
  private final boolean quiet;
  private final RefereeConfig refereeConfig;
  private static final int MIN_PLAYERS = 2;
  private static final int MAX_PLAYERS = 4;
  private static final int CONVERT_TO_MILLISECONDS = 1000;


  /**
   * Server constructor
   * @param port the port to listen for input on
   * @param serverTries the number of times the server will wait for client connections
   * @param serverWait the time to wait for connections during a single sign up period
   * @param waitForSignUp how long a player has to send their name after sign up
   * @param quiet whether we want the debug port run
   * @param refereeConfig the configurations for a referee
   */
  public server(int port, int serverTries, int serverWait, int waitForSignUp,
      boolean quiet, RefereeConfig refereeConfig) throws IOException {
    this.servSocket = new ServerSocket(port);
    this.serverTries = serverTries;
    this.serverWait = serverWait * CONVERT_TO_MILLISECONDS;
    this.waitForSignUp = waitForSignUp * CONVERT_TO_MILLISECONDS;
    this.quiet = quiet;
    this.refereeConfig = refereeConfig;
  }

  /**
   * @return the result of finding players for this server and running a game with those players
   */
  public Entry<List<String>, List<String>> runNewGame() {
    List<IPlayer> players = new ArrayList<>();
    for (int i = 0; i < serverTries; i++) {
      this.findPlayers(players);
      this.printDebug("round finished");
      if (players.size() >= MIN_PLAYERS) {
        return this.refereeConfig.createReferee().runGame(players);
      }
    }
    return new SimpleEntry<>(new ArrayList<>(), new ArrayList<>());
  }

  /**
   * @return the result of finding players for this server and running a game with those players
   */
  public Entry<List<String>, List<String>> runFromExistingState() {
    List<IPlayer> players = new ArrayList<>();
    for (int i = 0; i < this.serverTries; i++) {
      findPlayers(players);
      this.printDebug("round finished");
      if (players.size() >= MIN_PLAYERS) {
        return this.refereeConfig.createReferee().runExistingGame(
            players, this.refereeConfig.getGameState());
      }
    }
    return new SimpleEntry<>(new ArrayList<>(), new ArrayList<>());
  }

  /**
   * EFFECT: mutates the given list of players.
   * Connect players to play in this server's game
   * @param players the list of players
   */
  public void findPlayers(List<IPlayer> players) {
    long initialTimeMillis = System.currentTimeMillis();
    while (players.size() < MAX_PLAYERS
        && System.currentTimeMillis() - initialTimeMillis < this.serverWait) {
      try {
        this.servSocket.setSoTimeout(
            (int) (this.serverWait -
                (System.currentTimeMillis() - initialTimeMillis)));
        try {
          Socket client = servSocket.accept();
          client.setSoTimeout(this.waitForSignUp);
          BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

          JsonParser parser = getJsonParserFromBufferedReader(reader);
          parser.nextToken();
          JsonNode node = parser.readValueAsTree();
          String name = node.asText();
          IPlayer p = new player(name, client.getOutputStream(),
              new InputStreamReader(client.getInputStream()));
          players.add(p);
          this.printDebug("player added");
        } catch (IOException e) {
          this.printDebug("player failed to send name in time");
        }
      }
      catch (IOException e) {
          break;
      }
    }
  }

  /**
   * Send a debug message for this server
   * @param message the message to send
   */
  private void printDebug(String message) {
    if (!this.quiet) {
      System.err.println(message);
    }
  }

  /**
   * Close the server
   * @throws IOException if closing failed
   */
  public void closeServer() throws IOException {
    this.servSocket.close();
  }
}
