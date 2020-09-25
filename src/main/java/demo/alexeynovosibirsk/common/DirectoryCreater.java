package demo.alexeynovosibirsk.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryCreater {

    private static String base = "just_portal";


    public static void makeDirs() {
        go(base);
        go(base + "/security");
    }

    public static void go(String dirname) {
        Path path = Paths.get(dirname);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}