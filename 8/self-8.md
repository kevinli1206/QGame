The commit we tagged for your submission is 9773f24d61b4b081b2ee8c05649f074429bbabfd.
**If you use GitHub permalinks, they must refer to this commit or your self-eval will be rejected.**
Navigate to the URL below to create permalinks and check that the commit hash in the final permalink URL is correct:

https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/tree/9773f24d61b4b081b2ee8c05649f074429bbabfd

## Self-Evaluation Form for Milestone 8

Indicate below each bullet which file/unit takes care of each task:

- concerning the modifications to the referee: 

  - is the referee programmed to the observer's interface
    or is it hardwired?
    
    Hardwired, we do not have an observer interface.
    
    https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/9773f24d61b4b081b2ee8c05649f074429bbabfd/Q/Referee/observer.java#L22

  - if an observer is desired, is every state per player turn sent to
    the observer? Where? 
    
    https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/9773f24d61b4b081b2ee8c05649f074429bbabfd/Q/Referee/referee.java#L113-L142
    
    On line 128, each state before a player's turn is sent to the observer.

  - if an observer is not desired, how does the referee avoid calls to
    the observer?
    
    https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/9773f24d61b4b081b2ee8c05649f074429bbabfd/Q/Referee/referee.java#L33-L51
    
    If an observer is not desired, the referee empty constructor will be called to create an empty list of observers. Therefore, there are no observers for a referee to send game states to.

- concerning the implementation of the observer:

  - does the purpose statement explain how to program to the
    observer's interface? 
    
    No

  - does the purpose statement explain how a user would use the
    observer's view? Or is it explained elsewhere? 
    
    https://github.khoury.northeastern.edu/CS4500-F23/mysterious-alligators/blob/9773f24d61b4b081b2ee8c05649f074429bbabfd/Q/Referee/observer.java#L18-L21
    
    No, we define what an observer does but not how someone would use it.

The ideal feedback for each of these three points is a GitHub
perma-link to the range of lines in a specific file or a collection of
files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.

