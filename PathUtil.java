package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class PathUtil {


    private static final String confFileName = "config.conf";
    private static final String databaseFileName = "database.db";
    private static String workingDirectory;

    /**
     * The function will detect the operating system and set the working directory to the appropriate
     * location
     * 
     * @return The OS number (1 => Windows, 2 => Mac, 3 => Unix).
     */
    //TODO i need to package the thing into a .jar first
    public static void setWorkingDirectory(){
                workingDirectory = System.getenv("AppData") + "\\GAMBITVault\\";
                if (!checkIfWorkingDirExists())
                    createWorkingDir(workingDirectory);
    }

    /**
     * Check if the working directory exists
     *
     * @return The method returns a boolean value.
     */
    public static boolean checkIfWorkingDirExists(){
        File tmp = new File(workingDirectory);
        return tmp.exists();
    }

    /**
     * Creates a new working directory if it doesn't exist, otherwise prints an error
     *
     * @param workDir The working directory.
     */
    public static void createWorkingDir(String workDir) {
    	File f = new File(workingDirectory);
        if (!f.mkdir()) {
            System.out.println("Directory cannot be created");
        }
    }

    /**
     * Returns the current working directory
     * 
     * @return The working directory.
     */
    public static String getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * Check if the config file exists
     * 
     * @return The method returns a boolean value.
     */
    public static boolean checkIfConfigExists(){
        File tmp = new File(workingDirectory + confFileName);
        return tmp.exists();
    }

    /**
     * Check if the database exists
     * 
     * @return The method returns a boolean value.
     */
    public static boolean checkIfDatabaseExists(){
        File database = new File(workingDirectory + databaseFileName);
        return database.exists();
    }

    /**
     * Creates a new configuration file if it doesn't exist, otherwise throws an error
     * 
     * @param init The initial configuration String.
     */
    public static void createConfFiles(String init) {
        try {
            File f = new File(workingDirectory + confFileName);
            if(f.createNewFile()){
                writeConfFile(init);
                //DatabaseHandler.createDatabase();
                //DatabaseHandler.createMainTable();
            } else {
                AlertUtil.showErrorDialog("Error", "Restart the program",
                		"Error");
            }
        } catch (IOException e) {
            AlertUtil.showExceptionStackTraceDialog(e);
        }
    }

    /**
     * Write the given string to the conf file
     * 
     * @param s The configuration string to write to the file.
     */
    public static void writeConfFile(String s) {
        try {
            FileWriter fw = new FileWriter(workingDirectory + confFileName);
            fw.write(s);
            fw.close();
        } catch (IOException e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
    }

    /**
     * Reads the configuration file and returns the contents as a string.
     * 
     * @return The configuration file as a string.
     */
    public static String readConfigFile() {
        try {
            String confString = Files.readString(Paths.get(workingDirectory + confFileName));
            if (confString.isEmpty() ){
                deleteConfFiles();
                AlertUtil.showErrorDialog("Error", "Restart the program",
                		"Error");
            } else {
                return confString;
            }
        } catch (Exception e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
        return null;
    }

    /**
     * If the config file exists, read the config file and return the theme. If the config file doesn't exist, return
     * the default theme
     *
     * @return The last part of the config file, which is the theme.
     */
    public static String getConfigTheme() {
        if(!checkIfConfigExists()){
            return "dark";
        } else {
            try {
                return readConfigFile().split(":")[readConfigFile().split(":").length - 1].trim();
            } catch (Exception e){
                AlertUtil.showExceptionStackTraceDialog(e);
            }
        }
        return "dark";
    }
    
    
    /**
     * If the config file exists, read the config file and return the username. 
     * If the config file doesn't exist, return "Error".
     *
     * @return The first part of the config file, which is the name
     */
    public static String getConfigName() {
        if(!checkIfConfigExists()){
            return "Error";//NON-NLS
        } else {
            try {
                return readConfigFile().split(":")[0].trim();
            } catch (Exception e){
                AlertUtil.showExceptionStackTraceDialog(e);
            }
        }
        return "Error";//NON-NLS
    }

    /**
     * This function changes theme the of the application
     *
     * @param theme the theme to change to
     */
    public static void changeTheme(String theme) {
        if(checkIfConfigExists()){
            String[] conf = readConfigFile().split(":");
            int confLength = conf.length;
            conf[confLength - 1] = theme;
            String newConfig = String.join(":", conf);
            writeConfFile(newConfig);
        }
    }

    /**
     * Delete the conf file and the database file
     */
    public static void deleteConfFiles() {
        try {
            File f = new File(workingDirectory + confFileName);
            File db = new File(workingDirectory + databaseFileName);
            if (f.exists()){
                if (!f.delete()) throw new Exception("Configuration file could not be deleted");
            }
            /*if (db.exists()){
                if (!db.delete()) throw new Exception("Database could not be deleted");
            }*/

        } catch (Exception e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
    }


   

}