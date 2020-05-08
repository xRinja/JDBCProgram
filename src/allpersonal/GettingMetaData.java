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
			String tempString = "";
			myConnection = DriverManager.getConnection(getMYSQLURL() + ip +":" + port + "/" + schema, userName, password);
			myStatement = myConnection.createStatement();
			dbmd = myConnection.getMetaData();
			rs = myStatement.executeQuery("SELECT * from " + table);
			rsmd = rs.getMetaData();
			columnsNames = new String[rsmd.getColumnCount()];
			System.out.println(rsmd.getColumnCount());
			rs = dbmd.getColumns(null, schema, table, null);
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
