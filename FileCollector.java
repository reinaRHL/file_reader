package ca.cmpt213.as1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Reina on 2016-05-25.
 * FileCollector reads source file and randomly assign them to CDs
 * File will be sorted out by the size and largest file in the list will be assigned first.
 * User can specify - number of cds, and max size for each cd, and source file path
 * First arg: number of cds
 * Second arg: max size of each cd
 * Third arg: source file path
 */

public class FileCollector {

    private static String out;
    private static int number;
    private static List<FileInfo> files = new ArrayList<>();
    private static List<Collection> cds = new ArrayList<>();
    private static List<Integer> cdIndexList = new ArrayList();
    private static boolean isFileInCD = false;

    public static void main(String[] args) {

        //user input validation
        if (args.length < 3) {
            System.out.println("Insufficient number of arguments");
            System.exit(1);
        } else if ((Integer.parseInt(args[0]) < 0) || (Integer.parseInt(args[0]) > 1000)) {
            System.out.println("First Argument Error: Enter a valid integer between 0 to 1000");
            System.exit(1);
        } else if ((Long.parseLong(args[1]) < 0) || (Long.parseLong(args[1]) > 1000000000000L)) {
            System.out.println("Second Argument Error: Enter a valid integer between 0 to 1,000,000,000,000");
            System.exit(1);
        } else {
            Display menu = new Display(args);
            menu.displayArgs();
            out = args[2];
            number = Integer.parseInt(args[0]);
        }

        readFile();
        sortFile(); //sort by size

        //making new cd instances + one cd for extra files
        for (int j = 0; j <= Integer.parseInt(args[0]); j++) {
            cds.add(new Collection(j, Long.parseLong(args[1])));
        }
        //this array stores for index for CDs- will be shuffled later
        for (int j = 0; j < Integer.parseInt(args[0]); j++) {
            cdIndexList.add(j);
        }

        //assign file to random CDs
        assign();

        for (int i = 0; i <= number; i++) {
            System.out.println(cds.get(i));
            cds.get(i).getFileList();
        }
    }

    private static void readFile() {
        File source = new File(out);
        try {
            Scanner keyboard = new Scanner(source);
            while (keyboard.hasNextLine()) {

                String text = keyboard.nextLine();
                if (text.trim().length() > 0) { //unless it's blank line
                    if ((new File(text).isFile())) {
                        files.add(new FileInfo(text));
                    } else { //when file listed in source file does not exist
                        System.out.printf("\n(WARNING)File: %s does not exist.\n", text);
                    }
                }
            }
            keyboard.close();
        } catch (FileNotFoundException ex) {
            System.out.printf("\nSource File (%s) does not exist. Check the input!\n", out);
        }
    }


    private static void sortFile() {
        class SortByFileSize implements Comparator<FileInfo> {
            @Override
            public int compare(FileInfo o1, FileInfo o2) {
                return (int) (o1.getSize() - o2.getSize());
            }
        }
        java.util.Collections.sort(files, new SortByFileSize());
    }

    //assign file to random cd as long as cd has enough space
    private static void assign() {
        for (int i = files.size() - 1; i >= 0; i--) {
            isFileInCD = false;
            java.util.Collections.shuffle(cdIndexList);

            while (isFileInCD == false) {
                for (int j = 0; j < number; j++) {
                    //System.out.println(cdIndexList.get(j));
                    if (enoughSpace(cdIndexList.get(j), i)) {
                        cds.get(cdIndexList.get(j)).addFiles(files.get(i).getPath());
                        isFileInCD = true;
                        break;
                    }
                }
                if (isFileInCD == false) {
                    cds.get(number).addFiles(files.get(i).getPath());
                    cds.get(number).changeCurrentSize(files.get(i).getSize());
                    isFileInCD = true;
                }
            }
        }
    }

    private static boolean enoughSpace(int cdIndex, int fileIndex) {
        if (cds.get(cdIndex).getMemoryLeft() >= files.get(fileIndex).getSize()) {
            cds.get(cdIndex).changeCurrentSize(files.get(fileIndex).getSize());
            return true;
        } else {
            return false;
        }
    }
}


