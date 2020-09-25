package demo.alexeynovosibirsk.security;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SecurityConfigFileHandler {
    static Logger logger = LoggerFactory.getLogger(SecurityConfigFileHandler.class);

   // private static String path = System.getProperty("user.home");

    private static String fileName = "tech_portal/security/security.conf";

    public static String getFullNameFile() {return fileName;}

        public static void createFile() {
        File file = new File(fileName);

            if(! file.exists()) {
                try {
                    file.createNewFile();
                    logger.info("SECURITY configuration file created");

                } catch (IOException e) {
                    logger.info("ERROR: Unable to CREATE SECURITY configuration file!");
                    e.printStackTrace();
                }
                write2File(file);

            } else {
                logger.info("The SECURITY configuration file exists.");
            }
        }

        private static void write2File(File file) {

            try {
                FileWriter fw = new FileWriter(file);
                fw.write(
                        "AA.Nazarov\n"
                );
                fw.flush();
                fw.close();
                logger.info("SECURITY configuration file updated");
            } catch (IOException e) {
                logger.info("ERROR: Unable WRITE to SECURITY configuration file!");
                e.printStackTrace();
            }
        }

    public static Set readConfig() {
        Set<String> setAdmins = new TreeSet<>();

        FileReader fr = null;
        try {
            fr = new FileReader(SecurityConfigFileHandler.getFullNameFile());
            Scanner scanner = new Scanner(fr);
            String s;
            while (scanner.hasNext()) {
                s = scanner.nextLine();
                setAdmins.add(s);
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return setAdmins;
    }

    public static void addAdmin(String string) {

        File file = new File(getFullNameFile());

        try {
            List list = FileUtils.readLines(file, Charset.defaultCharset());
            list.add(string);
            FileUtils.writeLines(file, list);

        } catch (IOException e) {
            logger.info("ERROR: unable to write to " + getFullNameFile());
            e.printStackTrace();
        }
    }

    public static void removeAdmin(String string) {
        File file = new File(getFullNameFile());

        try {
            List list = FileUtils.readLines(file, Charset.defaultCharset());
            list.remove(string);
            FileUtils.writeLines(file, list);

        } catch (IOException e) {
            logger.info("ERROR: unable to write to " + getFullNameFile());
            e.printStackTrace();
        }
    }
}
