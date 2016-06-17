package ca.cmpt213.as1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reina on 2016-05-30.
 * This class is for storing cd information: maximum size of cd, current size of cd, memory space left in cd..etc.
 * toString() method will be called by main method when printing Minion instances.
 */
public class Collection {
    private long cd_Maxsize;
    private long cd_size;
    private int cdNumber; //cd index#
    private List<String> filesInCd = new ArrayList<>(); //list of files in each cd
    private String filePath;
    private long memoryLeft;
    private static int counter = 0; //number of CDs


    public Collection(int cdNumber, Long cd_size) {
        this.cdNumber = cdNumber;
        this.cd_Maxsize = cd_size;
        this.memoryLeft = cd_size;
        this.cd_size = 0;
        counter++;
    }


    public Long getCdMaxSize() {
        return cd_Maxsize;
    }

    public Long getcdSize() {
        return cd_size;
    }

    public int getCdNumber() {
        return cdNumber;
    }

    public Long getMemoryLeft() {
        return memoryLeft;
    }

    //when new file is saved in cd, memory left & current size will be updated
    public void changeCurrentSize(long fileSize) {
        memoryLeft = memoryLeft - fileSize;
        cd_size = cd_size + fileSize;
    }

    //add file into a CD
    public void addFiles(String filePath) {
        this.filesInCd.add(filePath);
    }

    public void getFileList() {
        for (String file : filesInCd) {
            System.out.println(file);
        }
    }

    //to print cd list and its files
    @Override
    public String toString() {

        if (this.cdNumber + 1 == counter) {
            return String.format("\nExtra Files: %,.2f MiB (%,d bytes)%n**************************************************",
                    (double) this.getcdSize() / 1048567.0, this.getcdSize());
        } else {
            return String.format("\nCollection %d: %,.2f MiB (%,d bytes)%n**************************************************",
                    (this.cdNumber + 1), (double) this.getcdSize() / 1048567.0, this.getcdSize());
        }
    }
}


