Pair: stoic-eagles \
Commit: [4155c73773060de3cced598b9029dfe90bff77a6](https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/tree/4155c73773060de3cced598b9029dfe90bff77a6) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/e88f64a543bcbc41bd42f887ceb7a935ef600913/6/self-6.md \
Score: 191/210 \
Grader: Jessica Su

171/180 Programming Task:\
-5 points: Does not inform losers that they didn't win the game.\
-4 points: Testing can be more thorough. Will a player get kicked out if they take too long to respond? What if a player throws an error?

Great job separating pieces of functionality into composite helper functions.

Notes: A Gamestate has PlayersInfo. Referees have IPlayers. What are the benefits of having player data segmented in two places: PlayersInfo and IPlayer? Right now, when the Referee removes a player, the game state needs to remove the corresponding playerInfo and we have to make this change in two places. IPlayer and PlayersInfo are a parallel data structure. When you make a change to one data structure, you have to make a change to another. Instead, we can have an IPlayer inside the GameState so that we don't have parallel data structures. Think about how to encapsulate playerInfo when we have IPlayer in the GameState.


20/30 Design Task:\
-10 points: Referee should instantiate an observer that receives game states after a turn. The referee should notify the observer of a new game state after every turn. The observer should be able to display the past or current map, score, and players in the game. The game state does not have enough information about when a turn has finished to be able to update the observer. "When a turn has finished" include scoring the turn, kicking out the player if the turn was invalid, and determining whether this turn resulted in the game ending. 
