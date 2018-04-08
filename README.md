# TrainsAndTowns

INSTRUCTIONS TO RUN AND TEST THE APPLICATION:

1. Make sure that JRE or JDK is installed on the test machine. If not, install the JRE or JDK with the version 1.8+.
2. Check whether current directory (.) is in the CLASSPATH environment variable. If not, add . (single dot) to the CLASSPATH variable.
3. Download the solution .zip file inside from the GitHub to any location in a file system of the test machine.
4. Extract the .zip file to a current directory or directory in other location of the file system that has an access to run the Java apps.
5. .zip file contains solution folder named TrainsAndTowns. There are src folder which contains three Java source files (Graph.java,
Main.java, Vertex.java) and README.txt text file including instructions on how to run and test the application.
Navigate to the src folder under this extracted folder using Command Prompt.
6. Run the following commands to compile and run the application.
7. javac Main.java (to compile)
8. java Main Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7 (to run)
9. After entering the above commands, the program will display the testing instructions in the command prompt and
expect the user to provide input commands for testing. Detailed testing instructions appear on the screen, but brief explanation is
as following:

d route => d A-B-C (To test the distance of the route A, B, C)
tn v1 v2 stops mark[m, e] => tn C C 3 m (To test the trip number from C to C with a maximum of 3 stops)
						  => tn A C 4 e (To test the trip number from A to C with exactly 4 stops)
shrl v1 v2 => shrl A C (To test the shortest route length from A to C)
		   => shrl B B (To test the shortest route length from B to B)
drn v1 v2 distance mark[l, e] => drn C C 30 l (To test the different route number from C to C with a distance less than 30)
							  => drn C C 30 e (To test the different route number from C to C with a distance equal to 30)
e or q => e (To exit the program)
	   => q (To exit the program)

Note: I tried to write the code that maximum eliminates input errors which can occur while providing the program with input commands. 
These preventions are as follows: 

1. Program testing (input commands, not program arguments) is case insensitive. For example, 
d a-b-c, D A-b-C, D A-B-C, d a-b-C, tn c c 3 m, tn A c 4 e, Tn c A 4 E, TN C C 3 M, SHrl a c, SHRL B b, DRN a c 30 L, dRN B b 30 E 
are all valid input commands. 
2. The input commands acknowledged from the user are stored based on a space bar character. For this reason, there should be 
at least one space key character between command and its parameters in order to separate them from each other and accept as 
a single text input. Any other unnecessary white-space characters are removed from the commands and parameters while testing. 
To illustrate, d            A-b-C (d, 12 spaces, A-b-C), d  	a-b-c (d, 2 spaces 1 tab, a-b-c), tn   a	 C 4		 E 
(tn, 3 spaces, a, 1 tab 1 space, C, 1 space, 4, 2 tabs 1 space, E), TN 	C	 	C	 3		 	M 
(TN, 2 spaces, C, 1 tab 1 space 1 tab, C, 1 tab 1 space, 3, 2 tabs 1 space 1 tab, M), Shrl		   A	 			C 
(Shrl, 2 tabs 3 spaces, A, 1 tab 1 space 3 tabs, C) all are valid inputs to test the app. Pay special attention that there is at least 
one empty space between command and its parameters in each sample above. This is very important to divide inputs into two separate 
inputs as without the single space, for example, with the tab key as the input separation character between two inputs, the input is 
taken as the single input, not the two. That is, drn		c C		30	 l (drn, 2 tabs, c, space key, C, 2 tabs, 30, tab, space bar, l) 
is parsed as drn		c (1st input), C		30 (2nd input), and l (3rd input). Since there isn't such a command like "drn		c", 
the warning occurs on the screen informing the user about the wrong command and asks him/her to enter the command again. There has to be 
a space key between drn and c to separate the input into two and the tab is not considered as the separation character.
3. Program arguments that violate routing (edge) specifications are skipped and the ones that don't violate are taken as valid arguments
and the graph is constructed using valid arguments only. Regarding invalid arguments, warnings are displayed to the user on the screen.
Graph construction is done using arguments starting from the second place. 1st argument is just to provide some text to show what the
problem is related to. For instance, Graph:, Graph, or any other text. However, the other arguments should be entered as shown in the 8th
step above so as to be considered as valid arguments. That is, the last argument doesn't include comma (,), but the others have it.

END