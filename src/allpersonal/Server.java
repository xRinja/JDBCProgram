package allpersonal;

import java.sql.*;
import java.util.HashMap;

import javax.swing.JTextField;

public class Server {
	
	// Connection Variables
	protected String mySQLURL;
	protected String getIP = "localhost";
	protected String port = "3306";
	protected String getSchema = "videogamecollection";
	protected String userName = "root";
	protected String password = "finger00";
	protected String table = "";
	protected String[] connection = {"Driver", "IP Address", "Port", "User Name", "Password"};
	protected String[] databaseTypes = {"", "SQL", "ORACLE", "MICROSOFT", "POSTGRE", "MARIADB"};
	// Interface
	protected EntryListener entryListener;
	// JDBC Variables
	protected static Connection myConnection = null;
	protected Statement myStatement = null;
	protected CallableStatement myCallableStatement = null;
	protected PreparedStatement myPreparedStatement = null;
	protected ResultSet rs = null;
	protected java.sql.ResultSetMetaData rsmd = null;
	protected DatabaseMetaData dbmd = null;
	// Column Variables
	protected HashMap<String, String> metaDataHash = new HashMap<>(); // Column Type.
	protected String[] columnsNames; // Column Names.
	// Database Variables
	protected String[] schemaNames; // Schema names.
	protected String[] tableNames; // Table names.
	protected String[] users; // Users associated with database.
	protected String[] ipAddress; // Users ipAddress.
	// Initialize Variables
	public GettingMetaData gettingMetaData;
	public AddingEntries addingEntries;
	
	public Server(){		

	}
	
	public void establishConnection(){
		
	}
	
	// MYSQL
	public String getMYSQLURL(){
		mySQLURL = "jdbc:mysql://";
		return mySQLURL;
	}
	// Oracle
	public String getOracleURL(){
		mySQLURL = "jdbc:oracle:thin:@";
		return mySQLURL;
	}
	
	public void setMetaData(String schemaName){
		this.getSchema = schemaName;
		Connection connection;
		try {
			connection = DriverManager.getConnection(mySQLURL + getIP + ":" + port + "/" + getSchema, userName, password);
			myConnection = DriverManager.getConnection(mySQLURL + getIP + ":" + port + "/" + getSchema, userName, password);
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(connection.getCatalog(), null, "%", new String[] {"TABLE"});
			String tempString = "";
			while(rs.next()) {
				tempString += rs.getString(3) + " ";
			}
			tableNames = tempString.split(" ");
			connection.close();
			//myConnection.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void setTableDate(String table) {
		this.table = table;
		gettingMetaData = new GettingMetaData(mySQLURL, getIP, port, getSchema, userName, password, table);
		
		// Updating server
		//this.tableNames = gettingMetaData.tableNames;
		this.columnsNames = gettingMetaData.columnsNames;
		this.metaDataHash = gettingMetaData.metaDataHash;
	}
	
	public String[] getColumnNames(){
		return this.columnsNames;
	}
	
	public String getCurrentTable() {
		return table;
	}
	
	public void AddingEntries(JTextField[] jTextFields){
		System.out.println("Server starting to add entry");
		addingEntries = new AddingEntries();
		addingEntries.columnsNames = this.columnsNames;
		addingEntries.metaDataHash = this.metaDataHash;
		addingEntries.myCallableStatement = this.myCallableStatement;
		addingEntries.myConnection = this.myConnection;
		addingEntries.myStatement = this.myStatement;
		addingEntries.dbmd = this.dbmd;
		System.out.println("Server pushed values to adding entry");
		addingEntries.AddingEntries(jTextFields, table);
	}
	
	public void setEntryListener(EntryListener entryListener) {
		this.entryListener = entryListener;
	}
	
	public static Connection getConnection() {
		return myConnection;
	}
	
	public void logConnectionDetails(String[] jTextFields){
		if(jTextFields != null){
			System.out.println("server loging details");
			try {
				// Connecting
				Connection connection = DriverManager.getConnection(jTextFields[0] + jTextFields[1] + ":" + jTextFields[2] + "/", 
						jTextFields[3], jTextFields[4]);
				System.out.println("Connection Succeeded");
				DatabaseMetaData meta = connection.getMetaData();
				ResultSet schemas = meta.getCatalogs();
				String tempString = "";
				while(schemas.next()) {
					tempString += schemas.getString("TABLE_CAT") + " ";
				}
				connection.close();
				// Logins variables
				this.mySQLURL = jTextFields[0];
				this.getIP = jTextFields[1];
				this.port = jTextFields[2];
				this.userName = jTextFields[3];
				this.password = jTextFields[4];
				this.schemaNames = tempString.split(" ");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
