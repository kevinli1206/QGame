The commit we tagged for your submission is 4155c73773060de3cced598b9029dfe90bff77a6.
**If you use GitHub permalinks, they must refer to this commit or your self-eval will be rejected.**
Navigate to the URL below to create permalinks and check that the commit hash in the final permalink URL is correct:

https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/tree/4155c73773060de3cced598b9029dfe90bff77a6

## Self-Evaluation Form for Milestone 6

Indicate below each bullet which piece of your code takes care of each task:

1. the five pieces of player functionality
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/Player/IPlayer.java#L15-L43

2. `setting up players` functionality in the referee component
    https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/Referee/referee.java#L68-L78 

3. `running a game` functionality in the referee component
    https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/Referee/referee.java#L40-L54

4. `managing a round` functionality in the referee component
    (This must be factored out to discover the end-of-game condition.)
    https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/Referee/referee.java#L88-L118

5. `managing an individual turn` functionality in the referee component
    https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/Referee/referee.java#L129-L154

6. `informing survivors of the outcome` functionality in the referee component
    https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/Referee/referee.java#L204-L220

7. unit tests for the `referee`:

   - five distinct unit tests for the overall `referee` functionality
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/test/referee/refereeTest.java#L177-L203
   
   We only have 4 distinct unit tests for overall referee functionality.
   
   - unit tests for the abvove pieces of functionality 
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/test/referee/refereeTest.java#L1-L204
   
   We only have unit tests for the running a game component in the referee. The other pieces of functionality we have are private methods within our referee class as we only need to use those methods when running a complete game and thus, we do not have tests for those.
   
   The player tests are below.
   https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/test/player/playerTest.java#L126-L190
   
8. the explanation of what is considered ill-behaved player and how the referee deals with it.
    https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/4155c73773060de3cced598b9029dfe90bff77a6/Q/Referee/IReferee.java#L9-L17

The ideal feedback for each of these points is a GitHub perma-link to
the range of lines in a specific file or a collection of files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.


