The commit we tagged for your submission is 0a182e3fb43eb286b375f3abf3a558354727236f.
**If you use GitHub permalinks, they must refer to this commit or your self-eval will be rejected.**
Navigate to the URL below to create permalinks and check that the commit hash in the final permalink URL is correct:

https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/tree/0a182e3fb43eb286b375f3abf3a558354727236f

## Self-Evaluation Form for Milestone 5

Indicate below each bullet which piece of your code takes care of each task:

1. a data definition (inc. interpretation) for the result of a strategy

   We do not have a data definition for the result of a strategy. We were confused on how the iterator and the strategy differed so we only implemented the iterator.

2. the `dag` strategy 

https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/0a182e3fb43eb286b375f3abf3a558354727236f/Q/Player/dag.java#L1-L23

3. the `ldasg` strategy 

https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/0a182e3fb43eb286b375f3abf3a558354727236f/Q/Player/ldasg.java#L1-L52

4. a data definition (inc. interpretation) for the result of a strategy iterator

https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/0a182e3fb43eb286b375f3abf3a558354727236f/Q/Player/strategy.java#L10-L15

5. unit tests for the `dag` strategy
   - one for a 'pass' decision
   - one for a 'replace all tiles' decision
   - one for a 'place this tile there' decision

   As mentioned above, we confused the programming task for strategy vs iterator so we only have tests for the iterator.

6. unit tests for the `ldaag` strategy
   - one for a 'pass' decision
   - one for a 'replace all tiles' decision
   - one for a 'place this tile there' decision

   As mentioned above, we confused the programming task for strategy vs iterator so we only have tests for the iterator.

7. unit tests for the strategy iteration functionality 
   - one for a 'pass' decision
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/0a182e3fb43eb286b375f3abf3a558354727236f/Q/test/playerStrategy/strategyTest.java#L188-L194
   - one for a 'replace all tiles' decision
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/0a182e3fb43eb286b375f3abf3a558354727236f/Q/test/playerStrategy/strategyTest.java#L180-L186
   - one for a _sequence of_ 'place this tile there' decision
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/0a182e3fb43eb286b375f3abf3a558354727236f/Q/test/playerStrategy/strategyTest.java#L250-L259

8. how does your design abstract the common strategy iteration functionality
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/0a182e3fb43eb286b375f3abf3a558354727236f/Q/Player/AStrategy.java#L36-L67
   The listPossiblePlaces method is delegated to each individual strategy to run.

9. does your design abstract the common search through the sorted tiles?
   (for a bonus)
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/0a182e3fb43eb286b375f3abf3a558354727236f/Q/Player/AStrategy.java#L100-L114
   
The ideal feedback for each of these points is a GitHub perma-link to
the range of lines in a specific file or a collection of files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.


