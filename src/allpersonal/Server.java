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
	protected String[] connection = {"Driver", "IP Address", "Port", "Database", "User Name", "Password"};
	protected String[] databaseTypes = {"", "SQL", "ORACLE", "MICROSOFT", "POSTGRE", "MARIADB"};
	// Interface
	protected EntryListener entryListener;
	// JDBC Variables
	protected Connection myConnection = null;
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
	
	public void setMetaData(){
		gettingMetaData = new GettingMetaData(mySQLURL, getIP, port, getSchema, userName, password, "PS4");
		this.rsmd = gettingMetaData.rsmd;
		this.dbmd = gettingMetaData.dbmd;
		this.myConnection = gettingMetaData.myConnection;
		this.myStatement = gettingMetaData.myStatement;
		this.myPreparedStatement = gettingMetaData.myPreparedStatement;
		this.columnsNames = gettingMetaData.columnsNames;
		this.metaDataHash = gettingMetaData.metaDataHash;
		this.rs = gettingMetaData.rs;
		this.schemaNames = gettingMetaData.schemaNames;
		this.tableNames = gettingMetaData.tableNames;
		
		//schemaNames = new String[];
		//this.schemaNames = gettingMetaData.dbmd.getSchemas().;

			try {
				rs = dbmd.getSchemas();
				while(rs.next()){
					String tableSchema = rs.getString(1);
					String tableCatalog = rs.getString(2);
					System.out.println("tableSchema"+tableSchema);
					System.out.println("The schem is: " + rs.getString("TABLE_SCHEM"));
					System.out.println("The catalog is: " + rs.getString("TABLE_CATALOG"));
					} 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public String[] getColumnNames(){
		return this.columnsNames;
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
		addingEntries.AddingEntries(jTextFields);
	}
	
	public void setEntryListener(EntryListener entryListener) {
		this.entryListener = entryListener;
	}
	
	public void logConnectionDetails(String[] jTextFields){
		if(jTextFields != null){
			System.out.println("server loging details");
			try {
				// Connecting
				Connection connection = DriverManager.getConnection(jTextFields[0] + jTextFields[1] + ":" + jTextFields[2] + "/" + jTextFields[3], 
						jTextFields[4], jTextFields[5]);
				System.out.println("Connection Succeeded");
				DatabaseMetaData meta = connection.getMetaData();
				ResultSet schemas = meta.getCatalogs();
				//System.out.println(schemas.getString("TABLE_CAT"));
				String tempString = "";
				while(schemas.next()) {
					tempString += schemas.getString("TABLE_CAT") + " ";
				}
				// Logins variables
				this.mySQLURL = jTextFields[0];
				this.getIP = jTextFields[1];
				this.port = jTextFields[2];
				this.getSchema = jTextFields[3];
				this.userName = jTextFields[4];
				this.password = jTextFields[5];
				this.schemaNames = tempString.split(" ");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
