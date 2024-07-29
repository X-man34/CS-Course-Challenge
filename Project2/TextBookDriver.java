import java.util.Scanner;

/**
 * 
 * This is the main class for the Text Book social media "platform"
 * This class contains the driver code inclduing the menu and user interface. 
 * 
 * The text file implementation is robust and can survive really anything as long as the program stays running including deleting all the files
 * If post files are delted when the program is not running or if it crashed some data will be lost and the program attempts to reconstruct comments in corrupted post files (although its a weak attempt at best.)
 * @author Caleb Hottes
 */
public class TextBookDriver {

    /**
     * 
     * Run this method to run the application
     * 
     * @param args no command line args in this application
     * 
     */
    public static void main(String[] args) {
        final String seperator = "-----------------------------------------------------------------------------------------------------------------------";//this should be a public static final instance variable but the rules disallow it. 
        TextBook textBook = new TextBook();
        Scanner scammer = new Scanner(System.in);//name for this object is intentional :)
        System.out.println("Welcome to TextBook, a CS 121 sanctioned social media platform!\n");
        System.out.print("Please enter your username, spaces will be removed: ");
        String username = scammer.nextLine().replaceAll(" ", "");
        System.out.println("Welcome: " + username + "!");
        printMenu();
        boolean running = true;
        while (running) {
            System.out.println(seperator);
            System.out.print("Enter option from menu or M to show menu: ");
            String input = scammer.next();
            input = input.toLowerCase();
            switch (input) {
                case "p":
                    //print textbook
                    System.out.println(textBook);
                    break;
                case "a":
                    //create new post
                    System.out.print("Enter Post Text: ");
                    scammer.nextLine();//eat up all the stuff in scanner so the users input is what we take
                    String newPostText = scammer.nextLine();
                    textBook.addPost(username, newPostText);
                    System.out.println("Succussfully added post: \n" + textBook.getPostString(textBook.getPostCount() - 1));
                    break;
                case "d":
                    //delete old post
                    if (textBook.getPostCount() == 0) {
                        System.out.println("There are no posts to delete.");
                    }else {
                        Post deletedPost = textBook.removePost(getIndex(scammer, "Enter index of post to delete", textBook));
                        System.out.println("Deleted Post: " + deletedPost.toStringPostOnly());
                    }

                    break;
                case "c":
                    //comment on a post
                    if (textBook.getPostCount() == 0) {
                        System.out.println("There are no posts to comment on.");
                    }else {
                        int index = getIndex(scammer, "Enter index of post to comment on", textBook);
                        System.out.print("Enter Comment text: ");
                        scammer.nextLine();//eat up printout
                        String text = scammer.nextLine();
                        textBook.addComment(index, username, text);
                        System.out.println("Comment added.");
                    }
                    
                    break;
                case "r":
                    if (textBook.getPostCount() == 0) {
                        System.out.println("There are no posts to read, why don't you make one?");
                    }else {
                        System.out.println(textBook.getPostString(getIndex(scammer, "Enter index of post to read", textBook)));
                    }

                    break;
                case "q":
                    System.out.println("Quitting Application, thank you for your visit!");
                    System.exit(0);
                    break;
                case "m":
                    printMenu();
                    break;
                case "l":
                    System.out.print("Please Enter new username: ");
                    scammer.nextLine();
                    username = scammer.nextLine().replaceAll(" ", "");
                    break;
                default:
                    System.out.println("Option: " + input + " is not recognized use M for menu.");
                    break;
            }
        }
    }


    /**
     * Prints out the menu
     */
    private static void printMenu() {
        System.out.println("Menu Options: ");
        System.out.println("(p)rint the textbook");
        System.out.println("(a)dd a post to the textbook");
        System.out.println("(d)elete a post from the textbook");
        System.out.println("(c)omment on a post");
        System.out.println("(r)ead a post");
        System.out.println("(l)ogin (change username)");
        System.out.println("(q)uit the application");
        System.out.println("(m)show menu");
    }

    /**
     * this is a recusive method that forces the user to pick a valid index. If there are no valid indexes then the user will be stuck in the loop forever. 
     * But the driver code makes sure that this is only called when there is at least one valid index.
     * @param scan the scanner object so the method can take user input
     * @param prompt a prompt related to the useage of the method
     * @param book the textbook object This could and should be a static final variabel for this class, but its aginst the assignment rules. 
     * @return the chosen index
     */
    private static int getIndex(Scanner scan, String prompt, TextBook book) {
        System.out.print(prompt + ", (Valid Indecides are 0 through " + String.valueOf(book.getPostCount() - 1) + "): ");
        try {
            int input = scan.nextInt();
            if (0 <= input && input < book.getPostCount()) {
                return input;
            }else {
                System.out.println("Invlaid index, please try again.");
                return getIndex(scan, prompt, book);
            }
        }catch (Exception e) {
            //ignored
            return getIndex(scan, prompt, book);
        }

    }


}
