Hi. Making this just so you know what all the boards I have made for testing are.
solved-board.su --> This board is a completely solved board that will return True for isFullSolution and will return False for reject. 
partial-board.su --> This board is an almost completed board (one element is not solved). This will return False for both isFullSolution and reject.
reject-row.su --> Tests the "row reject" portion of method reject. Returns True.
reject-col.su --> Tests the "col reject" portion of method reject. Returns True.
reject-3x3.su --> Tests the "3x3 reject" portion of method reject. Returns True.
6-satan.su --> I thought that 5-evil.su wasn't hard enough so I searched up what is supposedly the worlds hardest sudoku puzzle and put it in. I just used it to run my code through the paces with "java Sudoku 6-satan.su". It works. This also is used in the next method to see if it would perform a next without an extend being called. It does not. 

testIsFullSolution --> This checks all the boards given along with solved-board.su and partial-board.su

testReject --> Checks all the boards given along with solved-board.su, reject-row.su, reject-col.su, and reject-3x3.su

testExtend --> Uses the boards given to confirm that the extend method is working. Prints out the unedited board and then the board after Extend. First instance of 0 is turned into a 1

testNext --> Uses the boards given to confirm that the next method works after a single run of extend works on any given board. Prints after Extend and then after Next. It also checks to see if the code would make changes to a board that didn't have an extend run prior to next. Since a run of next when no valid space to change is found causes it to return null, this prints "No assignment". 


  