package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
		
	private static Connection connect() {
	        Connection conn = null;
	        String url = "jdbc:sqlite:"+ PathUtil.getWorkingDirectory() +"database.db";
	        try {
	            conn = DriverManager.getConnection(url);
	            //System.out.println("Connection to SQLite has been established.");
	        } catch (SQLException e) {
	            AlertUtil.showExceptionStackTraceDialog(e);
	        }
	        return conn;
	    }
	    

	 /**
     * Create a database if it doesn't already exist
     */
    public static void createDatabase(){
        try (Connection conn = connect()) {
            if (conn != null){
                DatabaseMetaData metaData = conn.getMetaData();
                //System.out.println("Connected");
            }
        } catch (SQLException e) {
            AlertUtil.showExceptionStackTraceDialog(e);
        }
    }

    

	/**
     * Create the main table if it doesn't exist
     */
    public static void createMainTable(){
        try (Connection conn = connect();
            Statement stmt = conn.createStatement()) {
        	stmt.setQueryTimeout(30);
        	stmt.executeUpdate("CREATE TABLE IF NOT EXISTS main (id integer PRIMARY KEY, username varchar(255) NOT NULL, encryptedMasterkey varchar(255) NOT NULL, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (id) REFERENCES storage (id))");
        	stmt.executeUpdate("CREATE TABLE IF NOT EXISTS storage (id integer PRIMARY KEY, username varchar(255) NOT NULL, desc MEDIUMTEXT, website varchar(255), login varchar(255) NOT NULL, encryptedPassword varchar(255) NOT NULL, type varchar(255) NOT NULL, Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
        	//System.out.println("Created");
        } catch (SQLException e) {
            AlertUtil.showExceptionStackTraceDialog(e);
        }
    }

    /**
     * Insert a new password into the database
     * 
     * @param username the name of the user
     * @param encryptedMasterkey the encrypted password to be stored
     * @return Returns boolean value.
     */
    public static boolean insertPassword(String username, String encryptedMasterkey){
        String sql = "INSERT INTO main (username, encryptedMasterkey) VALUES (?,?);";//NON-NLS
        try (Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, encryptedMasterkey);
            int res = preparedStatement.executeUpdate();
            if (res == 1) return true;
        } catch (SQLException e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
        return false;
    }
    
    public static boolean insertLogin(String username, String desc, String website, String login, String encryptedPassword, String type){
        String sql = "INSERT INTO storage (username, desc, website, login, encryptedPassword, type) VALUES (?,?,?,?,?,?);";//NON-NLS
        try (Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
        	preparedStatement.setString(1, username);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, website);
            preparedStatement.setString(4, login);
            preparedStatement.setString(5, encryptedPassword);
            preparedStatement.setString(6, type);
            int res = preparedStatement.executeUpdate();
            if (res == 1) return true;
        } catch (SQLException e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
        return false;
    }
    
    public static String checkUsername(String username){
        String sql = "SELECT username FROM main WHERE EXISTS( SELECT 1 FROM main WHERE username = '" + username + "');";//NON-NLS
        try (Connection conn = connect();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            ResultSet rs = preparedStatement.executeQuery();
            System.out.print(rs.getString("username"));
            return rs.getString("username");
        } catch (SQLException e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
        return null;
    }
    
    /**
     * `truncateData()`: Deletes all rows from the `storage` table where equals to username
     */
    public static void truncateData(String username) {
        String sql = "DELETE FROM storage WHERE username = '" + username + "';";//NON-NLS
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e) {
            AlertUtil.showExceptionStackTraceDialog(e);
        }
    }
    
    public static void truncateUsers(String username) {
        String sql = "DELETE FROM main WHERE username = '" + username + "';";//NON-NLS
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e) {
            AlertUtil.showExceptionStackTraceDialog(e);
        }
    }

    /**
     * It selects all passwords from the database.
     * 
     * @return A list of lists. Each list contains the id, description, login, password, and iv.
     */
    public static List<List<String>> selectPasswords(){
        String sql = "SELECT username, desc, website, login, encryptedPassword, type FROM storage"; //NON-NLS
        List<List<String>> results = new ArrayList<>();
        try (Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                List<String> single = new ArrayList<>();
                single.add(rs.getString("username"));
                single.add(rs.getString("desc"));
                single.add(rs.getString("website"));
                single.add(rs.getString("login"));
                single.add(rs.getString("encryptedPassword"));
                results.add(single);
            }
        } catch (SQLException e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
        return results;
    }
    
    public static ResultSet selectPasswordsSet(String username){
        String sql = "SELECT id, username, desc, website, login, encryptedPassword, type FROM storage WHERE username = '" + username + "';"; //NON-NLS
        try {
        	Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
		return null;
    }

    
    /**
     * Update the password for a given entry
     * 
     * @return Returns boolean value.
     */
    public static boolean updatePassword(int id, String username, String desc, String website, String login, String encryptedPassword, String type) {
        String sql = "UPDATE storage SET username = ?, desc = ?, website = ?, login = ?, encryptedPassword = ? , type = ?" +
                	 "WHERE id = ?;";//NON-NLS
        String user1 = username;
        String desc1 = desc;
        String web1 = website;
        String log1 = login;
        String pass1 = encryptedPassword;
        String type1 = type;
        try (Connection conn = connect()){
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, website);
            preparedStatement.setString(4, login);
            preparedStatement.setString(5, encryptedPassword);
            preparedStatement.setString(6, type);
            preparedStatement.setInt(7, id);
            int res = preparedStatement.executeUpdate();
            if (res == 1) {
            	return true;
            }
            else {
            	insertLogin(user1, desc1, web1, log1, pass1, type1);
            }
        } catch (SQLException e) {
            AlertUtil.showExceptionStackTraceDialog(e);
        }
        return false;
    }

    /**
     * Delete a password from the database
     * 
     * @param id The id of the password to delete.
     * @return Returns boolean value.
     */
    public static boolean deletePassword(int id, String username, String desc, String website, String login, String encryptedPassword, String type) throws SQLException {
        String sql = "DELETE FROM storage WHERE id = ? AND username = ? AND desc = ? AND website = ? AND login = ? AND encryptedPassword = ? AND type = ?;";//NON-NLS
        try (Connection conn = connect()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, username);
            stmt.setString(3, desc);
            stmt.setString(4, website);
            stmt.setString(5, login);
            stmt.setString(6, encryptedPassword);
            stmt.setString(7, type);
            int res = stmt.executeUpdate();
            if (res == 1) return true;
        } catch (SQLException e){
            AlertUtil.showExceptionStackTraceDialog(e);
        }
        return false;
    }
    
    public static String getUser(String username, String encryptedMasterkey) {
    	String sql = "SELECT username, encryptedMasterkey FROM main WHERE username = '" + username + "' AND " + " encryptedMasterkey = '" + encryptedMasterkey + "'";
    	String dbUsername = "nil";
    	String dbEncryptedMasterkey = "nil";
    	try (Connection conn = connect();
    	        Statement stmt = conn.createStatement();
    	        ResultSet rs = stmt.executeQuery(sql)){
    	            while (rs.next()){
    	                dbUsername = rs.getString("username");//NON-NLS
    	                dbEncryptedMasterkey = rs.getString("encryptedMasterkey");
    	            }
    	            
    	        } catch (SQLException e){
    	            AlertUtil.showExceptionStackTraceDialog(e);
    	        }
    	if (dbUsername == username && dbEncryptedMasterkey == encryptedMasterkey)
    		return dbEncryptedMasterkey;
    	else
    		return dbEncryptedMasterkey;
    }

}
