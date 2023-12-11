- changing the bonus points again and allow more players to participate in a game
  - 1
  - They are both easy one line changes which entails changing a static variable's value.

- adding wildcard tiles
  - 2
  - We need to add a new "wild" enum to TileShape and TileColor and change the sameColor and sameShape functions to always be true when we have a "wild" tile.
  - Where ever we deal with creating a set of tiles we could handle wildcards separately by keeping a local variable accumulator and updating everytime we find a wildcard tile to avoid disregarding multiple wildcards.
  - Therefore, there would only be a few methods/variables that would need to be changed.

- imposing restrictions that enforce the rules of Qwirkle instead of Q
  - 5
  - We do not currently have a rulebook that enforces the rules of the game.
  - All of our methods in map, gamestate, and referee individually enforce the specific rules of the Q game.
  - Therefore, it would be a lot of work to change all the rules from the Q game to the Qwirkle game.