package ca.cmpt213.as1;

/**
 * Created by Reina on 2016-05-29.
 * This class receives a series of argument as parameter and display initial summary to the screen
 */
public class Display {

    private String[] args;

    public Display(String[] args) {
        this.args = args;
    }

    public void displayArgs() {
        System.out.printf("\nNow building collection:\n" +
                "**************************\n" +
                "# Collections: %d%n", Integer.parseInt(args[0]));
        System.out.printf("Size per Collection: %d%n", Long.parseLong(args[1]));
        System.out.printf("Source file list: %s%n", args[2]);
        System.out.println("**************************\n");
    }
}
