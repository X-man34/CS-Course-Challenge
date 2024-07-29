# PLAN.md Challenge Project

## Sources Used
*List all external sources you used to help you on this project.  Provide links if relevant.*
[https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-String-input-with-the-Scanner-class](https://stackoverflow.com/questions/7877529/java-string-scanner-input-does-not-wait-for-info-moves-directly-to-next-stateme)

## Reflection
1. What OOP concepts do you believe were used in this lab? List the term and give an example of where it was used. 
There were two interfaces that were implemented for the post and textbook classes.
There were overloaded constuctors in the post class.
There was inheritance in several of the IO classes where I passed in a FileReader into a buffered reader constructor that took a Reader.
There was encapsulation becuase instance fields are labled private and only accessed in a limited manner through public methods.

3. Describe one bug you had to fix during this project.  What was the bug? How did you find it? How did you fix it? How did you test to make sure the bug was fixed correctly?
-Besides the normal small growing pains of a software project; the only real bug I encountered was Scanner scanning the printout that was supposed to be a prompt and instantly advancing to the next statement and not allowing the user to input input. The solution to this, as found at the link above. add a call to scanner.nextLine() before actually trying to read the input so the scanner would clear the printout from the input stream and block when asking for user input. 
