package Referee;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.*;
import java.util.concurrent.TimeoutException;
import Common.IGameState;
import Common.game_state;
import Player.IPlayer;
import Player.MoveType.ExchangeMove;
import Player.MoveType.IMove;
import Player.MoveType.PassMove;
import Player.MoveType.PlaceMove;

/**
 * A function to run Q games.
 * Referee Interaction Handling:
 * - Catches invalid inputs from its players and kicks them out of the game (safety bug)
 * - Catches player inputs that break rule logic and kicks them out of the game (business logic)
 * Remote Communication Handling:
 * - Player disconnect from their socket that they're using to communicate with the referee (DoS)
 * - Catch if a player takes too long to give their move and kicks them out of the game (DoS)
 */
public class referee implements IReferee {

  private final List<IObserver> observers;
  private final int perTurn;
  private final boolean quiet;
  private static final int TIME_TO_MAKE_MOVE = 6;


  /**
   * Referee constructor without observers
   */
  public referee() {
    this.observers = new ArrayList<>();
    this.perTurn = TIME_TO_MAKE_MOVE;
    this.quiet = false;
  }

  /**
   * Referee constructor with a possible observer and time to make a move
   * @param addObserver whether we want an observer
   * @param perTurn the time to make a move
   * @param quiet whether to print to error output
   */
  public referee(boolean addObserver, int perTurn, boolean quiet) {
    this.observers = new ArrayList<>();
    if (addObserver) {
      this.observers.add(new observer());
    }
    if (perTurn < 0 || perTurn > 6) {
      throw new IllegalArgumentException("Invalid per-turn");
    }
    this.perTurn = perTurn;
    this.quiet = quiet;
  }

  /**
   * Referee constructor with observers
   * @param observers the observers for this referee's game
   */
  public referee(List<IObserver> observers) {
    this.observers = observers;
    this.perTurn = TIME_TO_MAKE_MOVE;
    this.quiet = false;
  }

  @Override
  public Entry<List<String>, List<String>> runGame(List<IPlayer> players) {
    List<String> misbehavingPlayers = new ArrayList<>();
    List<String> names = this.addPlayerNames(players);
    IGameState gamestate = new game_state();
    gamestate.initializeGame(names);
    Map<String, IPlayer> playersMap = this.convertToMap(players);
    this.setUpPlayerHands(playersMap, gamestate, misbehavingPlayers);
    while (this.hasPlayers(playersMap)) {
      if (this.runRound(playersMap, gamestate, misbehavingPlayers)) {
        break;
      }
    }
    this.updateObservers(gamestate);
    this.sendGameOver();
    return this.endGame(playersMap, gamestate, misbehavingPlayers);
  }

  /**
   * @param players are the players in the game.
   * @return creates a sorted list of all the player's names that are playing in
   * this referee's game.
   */
  private List<String> addPlayerNames(List<IPlayer> players) {
    List<String> names = new ArrayList<>();
    for (IPlayer p : players) {
      names.add(p.name());
    }
    return names;
  }

  /**
   * Sends the initial map and sets up the hands of each player for this referee's game.
   * @param players   the map of names to players in the game.
   * @param gamestate is the initial game state.
   */
  protected void setUpPlayerHands(Map<String, IPlayer> players, IGameState gamestate,
                                  List<String> misbehavingPlayers) {
    int size = players.size();
    for (int i = 0; i < size; i++) {
      IPlayer player = players.get(gamestate.getPublicData().getPublicPlayerInfo().peek().getKey());
      try {
        this.setSetUp(player, gamestate);
        gamestate.setNextTurn();
      } catch (Exception e) {
        misbehavingPlayers.add(player.name());
        this.kickPlayer(player, gamestate);
      }
    }
  }

  /**
   * @param players the map of names to players.
   * @return true if this referee's game still has players in it.
   */
  private boolean hasPlayers(Map<String, IPlayer> players) {
    return !players.isEmpty();
  }

  /**
   * @param players            the map of name to players playing in the game
   * @param gamestate          the current game state
   * @param misbehavingPlayers all the players that violated the rules
   * @return true if this referee's game has ended after running a single round
   */
  protected boolean runRound(Map<String, IPlayer> players, IGameState gamestate,
                             List<String> misbehavingPlayers) {
    List<IMove> roundMoves = new ArrayList<>();
    boolean isGameOver = false;
    int size = gamestate.getPublicData().getPublicPlayerInfo().size();
    for (int i = 0; i < size; i++) {
      IPlayer player = players.get(gamestate.getPublicData().getPublicPlayerInfo().peek().getKey());
      String name = player.name();
      int oldHandSize = gamestate.getPrivateData(name).getPlayerHand().size();
      this.updateObservers(gamestate);
      if (!this.runTurn(player, roundMoves, gamestate)) {
        handlePlayerException(player, players, gamestate, misbehavingPlayers);
      } else {
        isGameOver = this.isGameOverAfterTurn(name, gamestate);
        if (isGameOver) {
          break; // does not recompute current active player if curPlayer places all tiles and ends the game
        }
        // mutates game state and recomputes the current active player
        if (!this.completePlayerTurn(player, gamestate, oldHandSize, roundMoves)) {
          handlePlayerException(player, players, gamestate, misbehavingPlayers);
        }
      }
    }
    return this.isGameOverAfterRound(roundMoves) || isGameOver;
  }

  private void handlePlayerException(IPlayer player, Map<String, IPlayer> players,
                                     IGameState gamestate, List<String> misbehavingPlayers) {
    misbehavingPlayers.add(player.name());
    this.kickPlayer(player, gamestate);
  }

  /**
   * EFFECT:
   * 1. recompute current active player by moving current active player to the back of the queue
   * 2. add tiles to game state's knowledge of the player
   * @param player    the player whose turn is being completed
   * @param gamestate the current game state
   * @param oldHandSize the player's old size of their hand
   * @param roundMoves the list of moves in the round
   * @return true if dealing tiles back to the player if they need them and
   * setting the next player's turn for this referee's game is successful
   */
  private boolean completePlayerTurn(IPlayer player, IGameState gamestate, int oldHandSize,
      List<IMove> roundMoves) {
    try {
      if (!(roundMoves.get(roundMoves.size() - 1) instanceof PassMove)) {
        this.addTilesBackToPlayerHand(player.name(), gamestate, oldHandSize);
        this.setNewTiles(player, gamestate);
      }
      gamestate.setNextTurn();
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * @param name      the name of the current player
   * @param gamestate the current game state
   * @return true if the player's hand is empty after a turn in this referee's game
   */
  private boolean isGameOverAfterTurn(String name, IGameState gamestate) {
    return gamestate.getPrivateData(name).getPlayerHand().isEmpty();
  }

  /**
   * @param player     is the active player.
   * @param roundMoves the list of moves that have been made in a round
   * @param gamestate  the current game state.
   * @return true if the player successfully completes their turn in this referee's game.
   * EFFECT: mutates roundMoves to add in the move of the current turn
   */
  protected boolean runTurn(IPlayer player, List<IMove> roundMoves, IGameState gamestate) {
    try {
      IMove playerTurn = this.getPlayerMove(player, gamestate);
      roundMoves.add(playerTurn);
      if (playerTurn instanceof PassMove) {
        gamestate.runPassTurn(player.name());
      } else if (playerTurn instanceof ExchangeMove) {
        gamestate.runExchangeTurn(player.name());
      } else {
        gamestate.runPlaceTurn(player.name(), playerTurn.getPlacements()); // mutates game state's map and removes tile from game state player
        gamestate.scoreTurn(player.name(), playerTurn.getPlacements());
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * Updating the observers with the current game state
   * @param gamestate the current game state
   */
  private void updateObservers(IGameState gamestate) {
    IGameState gameStateCopy = gamestate.makeCopy();
    List<IObserver> observersCopy = new ArrayList<>(this.observers); // NOT a deep copy
    for (IObserver o : observersCopy) {
      try {
        this.communicateToObserver(o, gameStateCopy);
      } catch (Exception e) {
        this.observers.remove(o);
      }

    }
  }

  /**
   * Attempts to communicate the game state to the observer and throws a timeOutException
   * if the attempt takes too long ot reach the observer.
   */
  private void communicateToObserver(IObserver observer, IGameState gamestate)
          throws InterruptedException, ExecutionException, TimeoutException {
    ExecutorService sendToObserver = Executors.newSingleThreadExecutor();
    Future<Void> futureObserverRes =
            (Future<Void>) sendToObserver.submit(() -> observer.receiveState(gamestate));
    sendToObserver.shutdown();
    try {
      futureObserverRes.get(perTurn, TimeUnit.SECONDS);
    }
    catch (Exception e) {
      futureObserverRes.cancel(true);
      throw new TimeoutException();
    }
  }

  /**
   * send game over info to the observers
   */
  private void sendGameOver() {
    for (IObserver o : this.observers) {
      o.setGameOver();
    }
  }

  /**
   * @param player    the player making the move
   * @param gamestate the current gamestate
   * @return the move that this player wants to make given the gamestate in this referee's game
   */
  private IMove getPlayerMove(IPlayer player, IGameState gamestate)
          throws InterruptedException, ExecutionException, TimeoutException {
    ExecutorService executePlayerMove = Executors.newSingleThreadExecutor();
    Future<IMove> futurePlayerMove =
            executePlayerMove.submit(() -> player.takeTurn(gamestate.getPublicData()));
    executePlayerMove.shutdown();
    try {
      return futurePlayerMove.get(perTurn, TimeUnit.SECONDS);
    }
    catch (Exception e) {
      futurePlayerMove.cancel(true);
      throw new TimeoutException();
    }
  }

  /**
   * Try to give the player new tiles
   * @param player    the player
   * @param gamestate the current gamestate
   */
  private void setNewTiles(IPlayer player, IGameState gamestate)
      throws InterruptedException, ExecutionException, TimeoutException {
    ExecutorService executePlayerNewTiles = Executors.newSingleThreadExecutor();
    Future<Void> futureNewTiles =
        (Future<Void>) executePlayerNewTiles.submit(
            () -> player.newTiles(gamestate.getPrivateData(player.name()).getPlayerHand()));
    executePlayerNewTiles.shutdown();
    try {
      futureNewTiles.get(perTurn, TimeUnit.SECONDS);
    }
    catch (Exception e) {
      futureNewTiles.cancel(true);
      throw new TimeoutException();
    }
  }

  /**
   * Try to set up the player
   * @param player    the player
   * @param gamestate the current gamestate
   */
  private void setSetUp(IPlayer player, IGameState gamestate)
      throws InterruptedException, ExecutionException, TimeoutException {
    ExecutorService executeSetUp = Executors.newSingleThreadExecutor();
    Future<Void> futureSetUp =
        (Future<Void>) executeSetUp.submit(
            () -> player.setUp(gamestate.getPublicData(),
                gamestate.getPrivateData(player.name()).getPlayerHand()));
    executeSetUp.shutdown();
    try {
      futureSetUp.get(perTurn, TimeUnit.SECONDS);
    }
    catch (Exception e) {
      futureSetUp.cancel(true);
      throw new TimeoutException();
    }
  }

  /**
   * Try to inform the player of a win.
   * @param player    the player
   * @param didPlayerWin if the current player won
   */
  private void informWin(IPlayer player, boolean didPlayerWin)
      throws InterruptedException, ExecutionException, TimeoutException {
    ExecutorService executeWin = Executors.newSingleThreadExecutor();
    Future<Void> futureWin =
        (Future<Void>) executeWin.submit(
            () -> player.win(didPlayerWin));
    executeWin.shutdown();
    try {
      futureWin.get(perTurn, TimeUnit.SECONDS);
    }
    catch (Exception e) {
      futureWin.cancel(true);
      throw new TimeoutException();
    }
  }


  /**
   * EFFECT: adds tiles back to the game state's knowlege of the player's hand
   * Adds referee tiles back to the players hand in this referee's game.
   * @param name      the player's name.
   * @param gamestate the current game state.
   */
  private void addTilesBackToPlayerHand(String name, IGameState gamestate, int oldHandSize) {
    int activePlayerHandSize = gamestate.getPrivateData(name).getPlayerHand().size();
    gamestate.addNTilesToHand(name, oldHandSize - activePlayerHandSize);
  }

  /**
   * Kicks the given player out of this referee's game by removing it from the list of all players.
   * @param player    the misbehaving player
   * @param gamestate the current game state
   */
  private void kickPlayer(IPlayer player, IGameState gamestate) {
    gamestate.kickPlayer(player.name());
  }

  /**
   * @param roundMoves all the moves made/attempted in a round
   * @return true if all players pass or exchange in a round of moves in this referee's game
   */
  private boolean isGameOverAfterRound(List<IMove> roundMoves) {
    for (IMove move : roundMoves) {
      if (move instanceof PlaceMove) {
        return false;
      }
    }
    return true;
  }

  /**
   * @param players            the map of names to players to send end of game messages to
   * @param misbehavingPlayers all the players that violated the rules
   * @return a tuple where the first entry is the list of winning players and
   * the second entry is the list of losing players for this referee's game
   */
  protected Entry<List<String>, List<String>> endGame(Map<String, IPlayer> players,
      IGameState gamestate, List<String> misbehavingPlayers) {
    List<String> winningPlayers = new ArrayList<>(); // winning players that has successfully been informed
    int maxScore = gamestate.getMaxScore();
    this.informWinners(players, gamestate, winningPlayers, misbehavingPlayers, maxScore);
    this.informLosers(players, gamestate, misbehavingPlayers, maxScore);
    return new SimpleEntry<>(winningPlayers, misbehavingPlayers);
  }

  /**
   * Inform the winner(s) of the game run by this referee that they've won
   * EFFECT: mutates game state's queue should a player be kicked
   * EFFECT: accumulates given list of winningPlayers and misbehavingPlayers
   * @param players            the players in the game
   * @param gamestate          the current game state
   * @param winningPlayers     are the players with the maximum score
   * @param misbehavingPlayers are the players that got kicked
   * @param maxScore           the max score in the game
   */
  private void informWinners(Map<String, IPlayer> players, IGameState gamestate,
      List<String> winningPlayers, List<String> misbehavingPlayers, int maxScore) {
    int size = gamestate.getPublicData().getPublicPlayerInfo().size();
    for (int i = 0; i < size; i++) {
      IPlayer player = players.get(gamestate.getPublicData().getPublicPlayerInfo().peek().getKey());
      int playerScore = gamestate.getPublicData().getPlayerScore(player.name());
      if (!this.attemptToInformWinners(player, winningPlayers, maxScore, playerScore)) {
        this.handlePlayerException(player, players, gamestate, misbehavingPlayers);
      }
      else {
        gamestate.setNextTurn();
      }
    }
  }

  /**
   * Inform the loser(s) of the game run by this referee that they have lost
   * EFFECT: mutates game state's queue should a player be kicked
   * EFFECT: accumulates given list of winningPlayers and misbehavingPlayers
   * @param players the players in the game
   * @param gamestate the current game state
   * @param misbehavingPlayers are the players that got kicked
   */
  private void informLosers(Map<String, IPlayer> players, IGameState gamestate,
      List<String> misbehavingPlayers, int maxScore) {
    int size = gamestate.getPublicData().getPublicPlayerInfo().size();
    for (int i = 0; i < size; i++) {
      IPlayer player = players.get(gamestate.getPublicData().getPublicPlayerInfo().peek().getKey());
      int playerScore = gamestate.getPublicData().getPlayerScore(player.name());
      if (!this.attemptToInformLosers(player, maxScore, playerScore)) {
        this.handlePlayerException(player, players, gamestate, misbehavingPlayers);
      }
      else {
        gamestate.setNextTurn();
      }
    }
  }

  /**
   * @param player the player to inform
   * @param winningPlayers the list of winning players
   * @param maxScore the maxScore of the game
   * @param playerScore this player's score
   * @return true if a player is successfully informed
   */
  private boolean attemptToInformWinners(IPlayer player, List<String> winningPlayers, int maxScore,
      int playerScore) {
    if (maxScore == playerScore) {
      try {
        this.informWin(player, true);
        winningPlayers.add(player.name());
      }
      catch (Exception e) {
        return false;
      }
    }
    return true;
  }

  /**
   * @param player the player to inform
   * @param maxScore the max score of the game
   * @param playerScore the current player's score
   * @return true if a player is successfully informed
   */
  private boolean attemptToInformLosers(IPlayer player, int maxScore, int playerScore) {
    if (maxScore != playerScore) {
      try {
        this.informWin(player, false);
      }
      catch (Exception e) {
        return false;
      }
    }
    return true;
  }

  @Override
  public Entry<List<String>, List<String>> runExistingGame(List<IPlayer> players, IGameState gamestate) {
    List<String> misbehavingPlayers = new ArrayList<>();
    Map<String, IPlayer> playersMap = this.convertToMap(players);
    if (!gamestate.matchingPlayers(players)) {
      return new SimpleEntry<>(new ArrayList<>(), new ArrayList<>());
    }
    this.setUpPlayerHands(playersMap, gamestate, misbehavingPlayers);
    while (!gamestate.getPublicData().getPublicPlayerInfo().isEmpty()) {
      if (this.runRound(playersMap, gamestate, misbehavingPlayers)) {
        break;
      }
    }
    this.updateObservers(gamestate);
    this.sendGameOver();
    return this.endGame(playersMap, gamestate, misbehavingPlayers);
  }

  /**
   * @param players the given list of players
   * @return a mapping of player names to player
   */
  private Map<String, IPlayer> convertToMap(List<IPlayer> players) {
    Map<String, IPlayer> map = new HashMap<>();
    for (IPlayer p : players) {
      map.put(p.name(), p);
    }
    return map;
  }
}
