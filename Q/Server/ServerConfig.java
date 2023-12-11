package Server;

import Referee.RefereeConfig;
import java.io.IOException;

/**
 * To represent the configurations for a server
 */
public class ServerConfig {
  int port;
  int serverTries;
  int serverWait;
  int waitForSignUp;
  boolean quiet;
  RefereeConfig refereeConfig;

  /**
   * ServerConfig constructor
   * @param port the port to listen for input on
   * @param serverTries the number of times the server will wait for client connections
   * @param serverWait the time to wait for connections during a single sign up period
   * @param waitForSignUp how long a player has to send their name after sign up
   * @param quiet whether we want the debug port run
   * @param refereeConfig the configurations for a referee
   */
  public ServerConfig(int port, int serverTries, int serverWait, int waitForSignUp, boolean quiet, RefereeConfig refereeConfig) {
    if (port < 10000 || port > 60000) {
      throw new IllegalArgumentException("Invalid port number");
    }
    this.port = port;
    if (serverTries < 0 || serverTries > 10) {
      throw new IllegalArgumentException("Invalid server tries");
    }
    this.serverTries = serverTries;
    if (serverWait < 0 || serverWait > 30) {
      throw new IllegalArgumentException("Invalid server wait");
    }
    this.serverWait = serverWait;
    if (waitForSignUp < 0 || waitForSignUp > 10) {
      throw new IllegalArgumentException("Invalid wait for signup");
    }
    this.waitForSignUp = waitForSignUp;
    this.quiet = quiet;
    this.refereeConfig = refereeConfig;
  }

  /**
   * @return a server from this ServerConfig
   */
  public server buildServer() throws IOException {
    return new server(this.port, this.serverTries, this.serverWait,
        this.waitForSignUp, this.quiet, this.refereeConfig);
  }

}
