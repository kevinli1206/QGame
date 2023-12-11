Pair: stoic-eagles \
Commit: [0a182e3fb43eb286b375f3abf3a558354727236f](https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/tree/0a182e3fb43eb286b375f3abf3a558354727236f) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/448682ecb75fcc2c09bfef3165df55b4da2312d1/5/self-5.md \
Score: 80/125 \
Grader: Can Ivit

## Self Evaluation [20/20]
Thank you for accurate self evaluation.

## Programming [30/65]
- [-5] No data definition for the result of a single placement strategy.
- [-5] Concrete `IMove` implementations are not implemented properly and they do not have an interpretation.
- [-5] The result of `dag` and `ldasg` strategies should be one of: single placement, exchange, pass. The abstract iteration strategy should apply the underlying strategy (either `dag` or `ldasg`) as far as possible to obtain the longest possible series of placements. Thus the result of iteration strategy should be one of: sequence of placements, exchange, pass. The current design do not make this distinction.
- [-5] Strategies should not take in the game state in the constructor. Instead, game state should be passed into `applyStrategy`. This way, startegies will be stateless and players will not be coupled to a specific strategy implementation.
- [-15] No unit tests for `dag` and `ldasg` strategies.

## Design [30/40]
- [-5] The referee should explicitly check the legality of a requested placement before completing a turn.
- [-5] `isPlacementValid` method should take in a sequence of placements instead of a single placement.
