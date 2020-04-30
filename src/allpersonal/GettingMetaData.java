package allpersonal;

import java.sql.DriverManager;
import java.sql.SQLException;

public class GettingMetaData extends Server{

	public GettingMetaData(String url, String ip, String port, String schema, String userName, String password, String table){
		System.out.println("Reached Meta Data Class");
		setresultSetMetaData(url,ip,port,schema,userName,password,table);
	}
	
	// Setter for ResultSetMetaData
	private void setresultSetMetaData(String url, String ip, String port, String schema, String userName, String password, String table){
		try{
			// Debug message
			if(entryListener != null){
			entryListener.debug("Connecting to database...");
			setEntryListener(entryListener);
			}
			
			// Collecting and listing SCHEMA names
			myConnection = DriverManager.getConnection(getMYSQLURL() + "192.168.0.121:" + "8457/" + "videogamecollection", "root", "finger00");
			myStatement = myConnection.createStatement();
			dbmd = myConnection.getMetaData();
			rs = dbmd.getCatalogs();
			String tempString = "";
			while(rs.next()){
				tempString += rs.getString("TABLE_CAT") + " ";
			}
			schemaNames = tempString.split(" ");
			for(int j = 0; j < schemaNames.length; j++){
				System.out.println(schemaNames[j]);
			}
			// Collecting and listing Table names
			myConnection = DriverManager.getConnection(getMYSQLURL() + "192.168.0.121:" + "8457/" + "videogamecollection", "root", "finger00");
			myStatement = myConnection.createStatement();
			dbmd = myConnection.getMetaData();
			rs = dbmd.getTables("videogamecollection", null, null, null);
			rsmd = rs.getMetaData();
			tableNames = new String[rsmd.getColumnCount()];
			tempString ="";
			while(rs.next()){
				//System.out.println("Table name: "+rs.getString("Table_NAME"));
		         //System.out.println("Table type: "+rs.getString("TABLE_TYPE"));
		         //System.out.println("Table schema: "+rs.getString("TABLE_SCHEM"));
		         //System.out.println("Table catalog: "+rs.getString("TABLE_CAT"));
		         //System.out.println(" ");
				tempString += rs.getString("Table_NAME") + " "; 
				//System.out.println(tableNames[i]);
			}
			tableNames = tempString.split(" ");
			// Collecting and listing Column NAMES
			rs = myStatement.executeQuery("SELECT * from " + table);
			rsmd = rs.getMetaData();
			columnsNames = new String[rsmd.getColumnCount()];
			System.out.println(rsmd.getColumnCount());
			rs = dbmd.getColumns(null, "videogamecollection", "PS4", null);
			int i =0;
			while(rs.next()){
				columnsNames[i] = rs.getString("COLUMN_NAME");
				metaDataHash.put(rs.getString("COLUMN_NAME"), rs.getString("TYPE_NAME"));
				i++;
			}
		} catch (SQLException c) {
			c.printStackTrace();
		}
	}
}
