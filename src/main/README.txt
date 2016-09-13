#README.txt

(a)Name:
   ID:
   
(b)

i. A.I use two-dimension char array for the grid and char for the cell.
     It's light and convenient enough to finish the job.

   B.Method called during the generation of a new state is show below.
   
   
   
   
   
   
                                                        (cells iterator)(update cell data)
                                                                ^               ^               
                                          (state iterator)      |               |
                                                  ^             |               |
                                                  |             |               |
     new TwoPlayerGameOfLife(file)->initGrid()->next()->generateNextState()->update()->|
                                                  ^                                    |
                                                  |________________loop________________|
   
   
   
   
   
   
ii. I run my program with the given data in the example but get a different answer.Is my program 
iii.Actually I have other ways to implement the requirements but this seems to be the easiest and effective one.(primitive data structure,no other classes and packages)
v. Complexity rating : 4

