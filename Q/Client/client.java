package Client;


import Player.IPlayer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * To represent a client who wishes to participate in the Q game.
 * Socket is closed when game is over.
 * CLIENT EXCEPTION HANDLING:
 * -- if JSON sent from the server is invalid, client gracefully shuts down by closing the socket.
 * -- if client got kicked by the server, client gracefully shuts down by closing the socket.
 */
public class client implements IClient, Runnable {
  private referee proxyReferee;
  private Socket socket;
  private final boolean quiet;
  private final static int WAIT_FOR_SERVER = 10000;

  /**
   * client constructor
   * 1. while server is not up, keep trying to connect to server
   * 2. once connected, make a new proxy referee and use the proxy referee to send name
   * @param port the port to connect to
   * @param host the IP address/domain name
   * @param quiet whether we want the debug port run
   * @param player the given player for this client
   */
  public client(int port, String host, boolean quiet, IPlayer player) throws IOException {
    this.quiet = quiet;
    long initialTime = System.currentTimeMillis();
    while (initialTime + WAIT_FOR_SERVER > System.currentTimeMillis()) {
      try {
        this.socket = new Socket(host, port);
        this.proxyReferee = new referee(player, this.socket.getOutputStream(),
            new InputStreamReader(this.socket.getInputStream()), quiet);
        this.sendName();
        break;
      }
      catch (IOException e) {
        this.printDebug("server not started up yet");
      }
    }
  }

  /**
   * Sends the name of this client to the player
   */
  @Override
  public void sendName() {
    try {
      this.proxyReferee.sendNameToServer();
    }
    catch (IOException e) {
      this.printDebug("Shutting down proxy referee");
    }
  }


  /**
   * Send a debug message for this client
   * @param message the message to send
   */
  private void printDebug(String message) {
    if (!this.quiet) {
      System.err.println(message);
    }
  }

  /**
   * Executes this command in a NEW thread
   */
  @Override
  public void run() {
    try {
      this.proxyReferee.runProxyReferee();
    }
    catch (Exception e) {
      this.printDebug("Client failed to connect");
      return;
    }
    try {
      this.printDebug("closing socket");
      this.socket.close();
    }
    catch (IOException e) {
      this.printDebug("closing socket failed");
    }
  }
}
