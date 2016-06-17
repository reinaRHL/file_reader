package ca.cmpt213.as1;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by Reina on 2016-05-25.
 * class FileList scans all the files in the folder specified by the user
 * and write the list of files to the output file.
 * When user specify the file type, only those with specified type will be written to the output file
 * First arg: folder to look up
 * Second arg: path for output file
 * Further args are optional: enter file type
 */

public class FileLister {
    private static int totalSize = 0;
    private static long numberFiles = 0;
    private static String[] extension;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("\n(ERROR) Insufficient number of argument");
            System.out.println("First two args are mandatory - First: folder to loop up, Second: path for output file");
            System.exit(1);
        } else if (args.length == 2) { //extension not specified by user
            extension = new String[1];
            extension[0] = "";
            filterFile(args[0], args[1]);
        } else { //extension specified by user
            extension = new String[args.length - 2];
            for (int i = 2; i < (args.length); i++) {
                extension[i - 2] = args[i];
            }
            filterFile(args[0], args[1]);
        }
    }

    //filter file with specified type
    private static void filterFile(String path, String output) {
        class ListFilter implements FileFilter {
            @Override
            public boolean accept(File file) {
                for (String ext : extension) {
                    return file.getName().endsWith(ext);
                }
                return false;
            }
        }
        FileFilter filter = new ListFilter();
        File folder = new File(path);
        File[] fileList = folder.listFiles(filter);
        displayFileList(folder, fileList, output);
    }

    //display summary
    private static void displayFileList(File folder, File[] fileList, String output) {
        File target = new File(output);
        for (File subFile : fileList) {
            totalSize += subFile.length();
        }
        System.out.println("\nStatistics on Files Found:\n" +
                "**************************");
        System.out.printf("%-15s %s%n", "Source Path:", folder.getAbsolutePath());
        System.out.printf("%-15s %s%n", "Target Path:", output);
        System.out.printf("%-16s", "Extension:");
        for (String ext : extension) {
            System.out.printf("%-5s", ext);
        }
        System.out.printf("\n%-15s %d%n", "Files Found:", fileList.length);
        System.out.printf("%-15s %,.2f MiB (%,d bytes) %n", "Total Size:", (double) totalSize / 1048567.0, totalSize);
        System.out.println("Files:\n" +
                "*****************");

        //List of Files: write to file and screen
        try {
            PrintWriter writer = new PrintWriter(target);
            for (File subFile : fileList) {
                System.out.println(subFile.getAbsoluteFile());
                writer.println(subFile.getAbsoluteFile());
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println("\nWriting file list to output file: " + output);


    }
}
