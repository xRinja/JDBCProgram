package allpersonal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.sql.*;


public class TableSelectState extends JFrame implements GUIState, ActionListener{

	private GUIState guiState;
	private Context context;
	private JList<String> jList;
	private JTable jTable;
	private DefaultTableModel tableModel;
	private JPanel tableList;
	private JPanel tableFill = new JPanel();
	private JPanel actionSearch;
	private JPanel database;
	private JButton btnDatabase;
	private JButton btnAddEntry;
	private JButton btnSearch;
	private JButton btnDeleteEntry;
	private JButton btnConfirm;
	private JTextField txtSearchBar;
	private JScrollPane jScrollPane;
	private String[][] currentColumnEntry;
	//private String selected = "";
	private String tableSelection;
	private String sqlQueries = "";
	private JScrollPane sp;
	private Connection connection;
	private Component comp;
	private static final long serialVersionUID = 1L;

	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	public TableSelectState() {
		connection = Server.getConnection();
	}
	
	@Override
	public void Action(Context context, Server server, String[] dataSetOne, String[] dataSetTwo) {
		if(tableSelection != null) {
		} else {
			tableSelection = dataSetOne[0];
		}
		if(tableFill != null) {
			//tableModel.fireTableChanged(null);
			tableFill.removeAll();
			
		}
		//if(tableSelection != null) {
		//tableSelection = dataSetOne[0];
		//}
		
		System.out.println("DataSetTwo is: ");
		MakeActionArea();
		MakeListSelect(dataSetOne, server);
		//MakeTableArea(server);
	 	
		
		// Context
		context.setState(this);
		this.context = context;
		System.out.println(context.getState().toString());
	}
	
	private void MakeListSelect(String[] tables, Server server) {
		for(int i = 0; i < tables.length; i++) {
			System.out.println("The TEEBLES are: " + tables[i]);
		}
		jList = new JList<String>(tables);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList.setFixedCellHeight(25);
		jList.setFixedCellWidth(100);
		jList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
				String[] tempString = new String[1];
				for(int i = 0; i < tables.length; i++) {
					if(jList.getSelectedValue().equalsIgnoreCase(tables[i])) {
						System.out.println(tables[i]);
						tempString[0] = tableSelection;
						tableSelection = jList.getSelectedValue();
						if(tableModel != null) {
							tableFill.removeAll();
							MakeTableArea(server);
							//guiState.Action(context, server, tables, tempString);
						}
					}
				}
				System.out.println("Listener happened");
			}
			}
			
		});
		tableList = new JPanel();
		tableList.setLayout(new BorderLayout(0,0));
		jScrollPane = new JScrollPane(jList);
		for(int i = 0; i < tables.length; i++) {
			System.out.println("Tables are: " + tables[i]);
		}
		tableList.add(jScrollPane, BorderLayout.CENTER);
		MakeTableArea(server);
	}
	
	private void MakeTableArea(Server server) {
		//server.myConnection
		try {
			connection = Server.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement ps = null;
			//connection = Server.getConnection();
			connection.getMetaData();
			ps = connection.prepareStatement("select * from " + tableSelection);
			new HashMap<>();
			ResultSet rs = ps.executeQuery();
			System.out.println("Result set is from: " + tableSelection);
			tableModel = new DefaultTableModel();
			tableModel = buildTableModel(rs);
			//tableModel.addRow(new Object[]{Boolean.FALSE,null,null,null});
			jTable = new JTable(tableModel) {
				@Override
				public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
			        comp = super.prepareRenderer(renderer, row, col);
			        Object value = getModel().getValueAt(row, 1);
			       /* if (value.equals("The Last Of Us")) {
			            comp.setBackground(Color.lightGray);
			        }  else {
			           //comp.setBackground(Color.white);
			        }*/
			        return comp;
			    }
			};
			BorderLayout borderLayout = new BorderLayout();
			setLayout(borderLayout);
			tableModel.fireTableChanged(null);
			jTable.getTableHeader().setReorderingAllowed(false);
			jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			jTable.setFillsViewportHeight(true);
			jTable.setEnabled(true);
			jTable.getModel().addTableModelListener(jTable);
			//jTable.tableChanged(tableModel);
			jTable.getModel().addTableModelListener(new MyTableModelListener(jTable, this));
			int x = 0;
			currentColumnEntry = new String[jTable.getRowCount()][jTable.getColumnCount()];
			for(int i = 0; i < jTable.getRowCount(); i++) {
				for(int n = 0; n < jTable.getColumnCount(); n++) {
				currentColumnEntry[i][n] = jTable.getValueAt(i, n).toString();
				System.out.println("JTable contains: " + currentColumnEntry[i][n]);
				System.out.println(jTable.getColumnName(n));
				}
			}
			
			sp = new JScrollPane(jTable);
			tableFill.setLayout(new BorderLayout(0,0));
			tableFill.add(sp, borderLayout.CENTER);
			tableFill.repaint();
			tableFill.revalidate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void MakeActionArea() {
		new JButton();
		new JButton();
		actionSearch = new JPanel();
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {0, 0, 0};
		gbl_panel_1.rowHeights = new int[] {0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0};
		panel_1.setLayout(gbl_panel_1);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 0;
		panel_1.add(btnSearch, gbc_btnSearch);
		
		JLabel lblSearch = new JLabel("Search:");
		GridBagConstraints gbc_lblSearch = new GridBagConstraints();
		gbc_lblSearch.insets = new Insets(0, 0, 5, 5);
		gbc_lblSearch.gridx = 0;
		gbc_lblSearch.gridy = 0;
		panel_1.add(lblSearch, gbc_lblSearch);
		
		txtSearchBar = new JTextField();
		lblSearch.setLabelFor(txtSearchBar);
		txtSearchBar.setText("Search Bar");
		GridBagConstraints gbc_txtSearchBar = new GridBagConstraints();
		gbc_txtSearchBar.fill = GridBagConstraints.BOTH;
		gbc_txtSearchBar.anchor = GridBagConstraints.WEST;
		gbc_txtSearchBar.weighty = 1.0;
		gbc_txtSearchBar.insets = new Insets(0, 0, 5, 5);
		gbc_txtSearchBar.gridx = 1;
		gbc_txtSearchBar.gridy = 0;
		panel_1.add(txtSearchBar, gbc_txtSearchBar);
		txtSearchBar.setColumns(10);
		
		btnAddEntry = new JButton("Add Entry");
		btnAddEntry.addActionListener(this);
		GridBagConstraints gbc_btnAddEntry = new GridBagConstraints();
		gbc_btnAddEntry.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddEntry.gridx = 0;
		gbc_btnAddEntry.gridy = 1;
		panel_1.add(btnAddEntry, gbc_btnAddEntry);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(this);
		btnConfirm.setEnabled(false);
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.fill = GridBagConstraints.BOTH;
		gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirm.gridx = 1;
		gbc_btnConfirm.gridy = 1;
		panel_1.add(btnConfirm, gbc_btnConfirm);
		
		btnDeleteEntry = new JButton("Delete Entry");
		btnDeleteEntry.addActionListener(this);
		GridBagConstraints gbc_btnDeleteEntry = new GridBagConstraints();
		gbc_btnDeleteEntry.gridx = 2;
		gbc_btnDeleteEntry.gridy = 1;
		panel_1.add(btnDeleteEntry, gbc_btnDeleteEntry);
		actionSearch = panel_1;
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {0};
		gbl_panel_2.rowHeights = new int[] {0};
		gbl_panel_2.columnWeights = new double[]{1.0};
		gbl_panel_2.rowWeights = new double[]{0.0};
		panel_2.setLayout(gbl_panel_2);
		
		btnDatabase = new JButton("Database");
		GridBagConstraints gbc_btnDatabase = new GridBagConstraints();
		gbc_btnDatabase.fill = GridBagConstraints.BOTH;
		gbc_btnDatabase.gridx = 0;
		gbc_btnDatabase.gridy = 0;
		btnDatabase.addActionListener(this);
		panel_2.add(btnDatabase, gbc_btnDatabase);
		database = panel_2;
	}
	
	private void DeleteEntry(){
		System.out.println("Delete Entry Started");
		for(int i = 0; i < jTable.getRowCount(); i++) {
			if(jTable.isRowSelected(i) == true) {
				//Delete Selected Row        
				int[] getSelectedRowForDeletion = jTable.getSelectedRows();
				//Check if their is a row selected
				for(int k = getSelectedRowForDeletion.length - 1; k >= 0; k--) {
					try {
						if (getSelectedRowForDeletion[k] >= 0) {
							System.out.println("Selected rows are: " +  getSelectedRowForDeletion[k]);
						Statement statement = connection.createStatement();
						sqlQueries = "DELETE FROM " + tableSelection + " WHERE " + jTable.getColumnName(0) + "='" + 
						jTable.getValueAt(getSelectedRowForDeletion[k], 0) +"'";
						//tableModel.fireTableChanged(null);
						System.out.println(sqlQueries);
						statement.executeUpdate(sqlQueries);
						btnConfirm.setEnabled(true);
						/*Object value = jTable.getModel().getValueAt(getSelectedRowForDeletion[k], 0);
					     if (value.equals(jTable.getModel().getValueAt(getSelectedRowForDeletion[k], 0))) {
					    	 comp.setBackground(Color.lightGray);
					     }*/
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (getSelectedRowForDeletion[k] >= 0) {

						//jTable.getSelectedRow()
						tableModel.removeRow(getSelectedRowForDeletion[k]);
						getSelectedRowForDeletion = jTable.getSelectedRows();
						System.out.println("Row Deleted");
					} else {
						System.out.println("Unable To Delete");
					}
					//comp.setBackground(Color.lightGray);
				}
			}
		}
		//tableModel.removeRow(getSelectedRowForDeletion[k]);
	}
	
	private void ConfirmChanges(){
		try {
			connection.commit();
			btnConfirm.setEnabled(false);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		System.out.println("DatabaseButtonPressed Outside");
		System.out.println(clicked.getActionCommand());
			if(clicked.getActionCommand() == "Database") {
				String[] buttonChoice = {"Database"};
				guiState.Action(this.context, null, buttonChoice, null);
			} 
			if(clicked.getActionCommand() == "Add Entry") {
				String[] buttonChoice = {"Add Entry"};
				String[] table = {tableSelection};
				guiState.Action(this.context, null, buttonChoice, table);
			}
			if(clicked.getActionCommand() == "Delete Entry") {
				String[] buttonChoice = {"Add Entry"};
				System.out.println("Pressed Delete");
				DeleteEntry();
			}
			if(clicked.getActionCommand() == "Confirm") {
				String[] buttonChoice = {"Confirm"};
				System.out.println("Pressed Confirm");
				ConfirmChanges();
			}
		
		
	}
	
	private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		java.sql.ResultSetMetaData metaData = rs.getMetaData();
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	        System.out.println(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	            System.out.println("Column Entry is: " + vector);
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
	
	public JPanel getTableList() {
		return tableList;
	}
	
	public JPanel getTableFill() {
		return tableFill;
	}
	
	public JPanel getActionSearch() {
		return actionSearch;
	}
	
	public JPanel getDataBaseButton() {
		return database;
	}
	
	public String toString(){
		return "Table State";
	}

	public String getSqlQueries() {
		return sqlQueries;
	}
	
	
	class MyTableModelListener implements TableModelListener {
		  JTable table;
		  //TableSelectState tss;

		  MyTableModelListener(JTable table, TableSelectState tss) {
		    this.table = table;
		    //this.tss = tss;
		  }
		  
		  @Override
		  public void tableChanged(TableModelEvent e) {
			    int firstRow = e.getFirstRow();
			    int lastRow = e.getLastRow();
			    int index = e.getColumn();

			    switch (e.getType()) {
			    case TableModelEvent.INSERT:
			      for (int i = firstRow; i <= lastRow; i++) {
			        System.out.println("Insert at: " + i);
			      }
			      break;
			    case TableModelEvent.UPDATE:
			      if (firstRow == TableModelEvent.HEADER_ROW) {
			        if (index == TableModelEvent.ALL_COLUMNS) {
			          System.out.println("A column was added");
			        } else {
			          System.out.println(index + "in header changed");
			        }
			      } else {
			        for (int i = firstRow; i <= lastRow; i++) {
			          if (index == TableModelEvent.ALL_COLUMNS) {
			            System.out.println("All columns have changed");
			          } else {
			        	  if(currentColumnEntry[firstRow][index].equalsIgnoreCase(jTable.getValueAt(firstRow, index).toString())) {
			        		  System.out.println("Field has not been changed");
			        	  } else {
			        		  try {
			        			//connection = Server.myConnection;
								Statement statement = connection.createStatement();
								sqlQueries = "UPDATE " + tableSelection + " SET " + table.getColumnName(index) + "='" + 
								table.getValueAt(firstRow, index) + "' Where " + table.getColumnName(0) + "='" + table.getValueAt(firstRow, 0) +"'";
								System.out.println(sqlQueries);
								statement.executeUpdate(sqlQueries);
								//tableModel.fireTableDataChanged();
								btnConfirm.setEnabled(true);
								//connection.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			        		  System.out.println(table.getValueAt(firstRow, index).toString());
			        		  System.out.println("Change at index: " + "(" + firstRow + "," + index+")");
			        	  }
			          	}
			        }
			      }
			      break;
			    case TableModelEvent.DELETE:
			      for (int i = firstRow; i <= lastRow; i++) {
			          int getSelectedRowForDeletion = table.getSelectedRow();
			          //tableModel.removeRow(getSelectedRowForDeletion);
			          System.out.println("Deleted From Case");
			      }
			      break;
			    }
	}
	}
}
