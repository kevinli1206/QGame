The commit we tagged for your submission is cc22ac073f4889a2b33658ebf9c5ab18e5044561.
**If you use GitHub permalinks, they must refer to this commit or your self-eval will be rejected.**
Navigate to the URL below to create permalinks and check that the commit hash in the final permalink URL is correct:

https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/tree/cc22ac073f4889a2b33658ebf9c5ab18e5044561

## Self-Evaluation Form for Milestone 4

Indicate below each bullet which method takes care of each task:

1 'rendering the referee state' 
https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/cc22ac073f4889a2b33658ebf9c5ab18e5044561/Q/Common/game_state.java#L481-L505

2. 'scoring a placement' 
https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/cc22ac073f4889a2b33658ebf9c5ab18e5044561/Q/Common/game_state.java#L220-L228

3. The 'scoring a placement' functionality clearly performs four different checks: 
  - 'length of placement'
  https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/cc22ac073f4889a2b33658ebf9c5ab18e5044561/Q/Common/game_state.java#L230-L237
  - 'bonus for finishing'
  https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/cc22ac073f4889a2b33658ebf9c5ab18e5044561/Q/Common/game_state.java#L295-L307
  - 'segments extended along the line (row, column) of placements'
  https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/cc22ac073f4889a2b33658ebf9c5ab18e5044561/Q/Common/game_state.java#L255-L273
  - 'segments extended orthogonal to the line (row, column) of placements'
  https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/cc22ac073f4889a2b33658ebf9c5ab18e5044561/Q/Common/game_state.java#L275-L293
  - indicate which of these are factored out into separate
    methods/functions and where.
    
    All of them are factored out into separate methods. Additionally, we have another method for checking if the placements made a Q.
    https://github.khoury.northeastern.edu/CS4500-F23/stoic-eagles/blob/cc22ac073f4889a2b33658ebf9c5ab18e5044561/Q/Common/game_state.java#L358-L376
   
The ideal feedback for each of these points is a GitHub perma-link to
the range of lines in a specific file or a collection of files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.


