package ca.cmpt213.as1;

import java.io.File;

/**
 * Created by Reina on 2016-05-29.
 * This class stores file's information: file size and its path
 */
public class FileInfo {

    private String path;
    private Long size;

    public FileInfo(String path) {
        this.path = path;
        File file = new File(path);
        this.size = file.length();
    }

    public String getPath() {
        return path;
    }

    public Long getSize() {
        return size;
    }
}
