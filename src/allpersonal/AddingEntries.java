package allpersonal;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.swing.JTextField;

public class AddingEntries extends Server{
	//Variables
	private String values = "";
	
	public AddingEntries(){
		System.out.println("Reached adding entries");
	}
	
	public void AddingEntries(JTextField[] columnTextField){
		try {
			System.out.println("Adding Entry");
			AddDatatoDataBase(getValuesFromTextField(columnTextField));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getValuesFromTextField(JTextField[] columnTextField) throws SQLException, ParseException{
		System.out.println("Getting values from textfield...");
		for(int i = 0; i < columnTextField.length; i++){
			if(columnTextField[i] == columnTextField[columnTextField.length - 1]){
				if(columnTextField[i].getText().equals("")){
					values += "null";
				} 
				else if(columnTextField[i].getText() != null){
					values += columnTextField[i].getText();
				}
				
			} else {
				if(columnTextField[i].getText().equals("")){
					values += "null" + ",";
				} 
				else if(columnTextField[i] != null){
					values += columnTextField[i].getText() + ",";
				}
			}
		}
		return values;
	}
		
		private void AddDatatoDataBase(String textFieldValues) throws SQLException, ParseException{
			// Setting auto commit to false
			myConnection.setAutoCommit(false);
		
			// Getting column names from table
			String columnNames = "";
			String valuesIndex ="";
			
			for(int j = 0; j < columnsNames.length; j++){
				if(j < columnsNames.length - 1){
				columnNames+= columnsNames[j] + ",";
				} else if(j < columnsNames.length){
					columnNames+= columnsNames[j];
				}
			}
			// Making SQL statements from input.
			System.out.println("Textfieldvalues are" + textFieldValues);
			String[] tempArray = textFieldValues.split(",");
			for(int i1 = 0; i1 < tempArray.length-1; i1++){
				valuesIndex += "?,";
				System.out.println("Temp Array is: " + tempArray[i1]);
			}
			valuesIndex += "?";
			
			String sql = "INSERT INTO PS4(" + columnNames + ") " + "VALUES(" + valuesIndex + ")";
			System.out.println(sql);
			myPreparedStatement = myConnection.prepareStatement(sql);
			for(int i2 = 0; i2 < tempArray.length; i2++){
				System.out.println("Made it into forloop");
				// INT TYPE
				if(metaDataHash.get(columnsNames[i2]).equalsIgnoreCase("int")){
					System.out.println("Made it into forloop in sub array");
					myPreparedStatement.setInt(i2+1,Integer.parseInt(tempArray[i2]));
					System.out.println(myPreparedStatement);
				}
				// VARCHAR TYPE
				if(metaDataHash.get(columnsNames[i2]).equalsIgnoreCase("varchar")){
					myPreparedStatement.setString(i2+1,tempArray[i2]);
					System.out.println(myPreparedStatement);
				}
				// DATE TYPE
				if(metaDataHash.get(columnsNames[i2]).equalsIgnoreCase("date")){
					java.util.Date localDate = new SimpleDateFormat("yyyy-MM-dd").parse(tempArray[i2]);
					java.sql.Date sqlDate = new java.sql.Date(localDate.getTime());
					myPreparedStatement.setDate(i2+1,sqlDate);
					System.out.println(myPreparedStatement);
				}
				// BIT TYPE
				if(metaDataHash.get(columnsNames[i2]).equalsIgnoreCase("bit")){
					myPreparedStatement.setByte(i2+1,Byte.parseByte(tempArray[i2]));
					System.out.println(myPreparedStatement);
				}
			}
			myPreparedStatement.executeUpdate();
			myConnection.commit();
		}
}
