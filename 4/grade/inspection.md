Pair: stoic-eagles \
Commit: [cc22ac073f4889a2b33658ebf9c5ab18e5044561](https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/tree/cc22ac073f4889a2b33658ebf9c5ab18e5044561) \
Self-eval: https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/b7b3d4bdbb43293da63666f9b6819f75fd803a33/4/self-4.md \
Score: 60/70 \
Grader: Derek Leung

inspection: [55/60] points

- [20/20] points, for helpful self-eval

[35/40] points, distributed as follows:

signatures and purpose statements of the following methods/functions

- [5/5] points for rendering the referee state.

- [0/5] points for scoring a placement. Scoring function shouldn't be responsible for adding points to the player's score. It should return an int of score.

unit tests for ’scoring a placement’:

- [5/5] points for for at least one should cover the bonus for a "Q"

- [5/5] points for for at least one should cover the case when there is no bonus

factoring of ’scoring a placement’. The function consists of four counting tasks:

- [5/5] points for ’length of placement’

- [5/5] points for ’bonus for finishing’

- [5/5] points for ’segments extended along the line (row, column) of placements’

- [5/5] points for ’segments extended orthogonal to the line (row, column) of placements’
- Consider defining constants for your point bonuses so instead of just having 6 there to improve code clarity.

design: [5/10] points

- [5/10] points, a referee must call the following methods in order: ‘take turn‘ then ‘accept tiles‘. The second call must be optional.
