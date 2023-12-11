**If you use GitHub permalinks, make sure your link points to the most recent commit before the milestone deadline.**

## Self-Evaluation Form for Milestone 2

Indicate below each bullet which file/unit takes care of each task:

1. does your implementation come with a separate data representationn for tiles?
   - https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/tree/b36664b0901d54deb51112fa8bcfc52c2b8819a2/Q/Common/tiles
   
2. does your implementation come with a separate data representation for map coordinates?
   https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/tree/b36664b0901d54deb51112fa8bcfc52c2b8819a2/Q/Common/place
   - does it include an interpretation statement?
      -  https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/blob/b36664b0901d54deb51112fa8bcfc52c2b8819a2/Q/Common/place/IPlace.java#L3-L5
   
3. does your functionality for creating and extending maps come with
   signatures and purpose statements?
   - https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/blob/b36664b0901d54deb51112fa8bcfc52c2b8819a2/Q/Common/IMap.java#L10-L24

4. does your functionality for "determining all those places where a
   specific tile can be inserted so that it fits according to the
   matching rules of The Q Game" come with a signature and purpose
   statement? does it factor out the following, separate pieces of
   functionality?
    - https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/blob/b36664b0901d54deb51112fa8bcfc52c2b8819a2/Q/Common/IMap.java#L26-L32

    - finding all feasible places meaning free neighbor tiles
      - No we did not separate this functionality. We put this functionality in our getValidPlacements method instead of separating it
    - determining all (four) neighbors of a spot
      - https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/blob/b36664b0901d54deb51112fa8bcfc52c2b8819a2/Q/Common/map.java#L14-L21
         - We defined a private static member directions variable that we use to determine all the neighbors of any given spot
    - checking the basic "fits" rule from the Q description
      - https://github.khoury.northeastern.edu/CS4500-F23/spooky-monkeys/blob/b36664b0901d54deb51112fa8bcfc52c2b8819a2/Q/Common/map.java#L104-L123
#### Notes 

Remember that if you think the name of a method/function is _totally
obvious_, there is no need for a purpose statement.

The ideal feedback for each of these points is a GitHub perma-link to
the range of lines in a specific file or a collection of files.

A lesser alternative is to specify paths to files and, if files are
longer than a laptop screen, positions within files are appropriate
responses.

You may wish to add a sentence that explains how you think the
specified code snippets answer the request.

If you did *not* realize these pieces of functionality, say so.


