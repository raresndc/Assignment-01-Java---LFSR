Finish the LFSR implementation started during the laboratory (available on github: https://github.com/catalin-boja/ism-sap-2024) by implementing

 a method that will do a full step (shift the register to the right, get the output bit as a pseudo bit, and insert to the left the bit from the tap sequence)
a method that will generate a byte array (byte[]) of pseudo random values with any size given as parameter. 
Reimplement the solution, this time with a BitSet instead of a integer to manage the LFSR register.

The solutions must be implemented in a single .java file (you can have any number of non public classes in that file or you can do everything in a single class)

For each implementation (lfsr with the integer and with the BitSet) provide a static method that will allow any user to generate any number of given pseudo bytes. Test them in main with a sequence of 20 bytes and 50 bytes and print them in hex on the screen (if you see patterns or all bits 1 or 0 .........it could mean something is not right). Test both implementations with same initial seed. The 2 sequences (20 bytes and 50 bytes) you get for each implementation must match.

Solutions with compiler errors will be evaluated with 1 point.

Finish LFSR implementation (2 pts)
1 pts for the full step method
1 pts for the method that generates a byte array with a given size
BitSet implementation (5 pts)
LFSR with integer random sequences look random (not al 1/0 or visible patterns) - 1pts
LFSR with BitSet random sequences look random (not al 1/0 or visible patterns) - 1pts
Upload the single .java file as your solution.