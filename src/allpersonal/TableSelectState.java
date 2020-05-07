package allpersonal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.dbutils.DbUtils;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class TableSelectState extends JFrame implements GUIState, ActionListener{

	private JButton[] selectionButton;
	private GUIState guiState;
	private Context context;
	private JList<String> jList;
	private JTable jTable;
	private DefaultTableModel tableModel;
	private JPanel tableList;
	private JPanel tableFill = new JPanel();
	private JPanel actionSearch;
	private JPanel database;
	private JTextField txtSearchBar;
	private JScrollPane jScrollPane;
	//private String selected = "";
	private String tableSelection;
	private String[] columns;
	private String[][] data;
	private JScrollPane sp;
	private static final long serialVersionUID = 1L;

	public void setGUIState(GUIState guiState) {
		this.guiState = guiState;
	}
	
	@Override
	public void Action(Context context, Server server, String[] dataSetOne, String[] dataSetTwo) {
		if(dataSetTwo != null) {
		tableSelection = dataSetTwo[0];
		System.out.println("DataSetTwo is: " + dataSetTwo[0]);
		}
		tableSelection = dataSetOne[0];
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
		//jList.setVisibleRowCount(100);
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
			Statement statement = null;
			PreparedStatement ps = null;
			java.sql.ResultSetMetaData rsmd = null;
			DatabaseMetaData dbmd = server.getConnection().getMetaData();
			ps = server.getConnection().prepareStatement("select * from " + tableSelection);
			HashMap<String, String> metaDataHash = new HashMap<>(); // Column Type.
			ResultSet rs = ps.executeQuery();
			System.out.println("Result set is from: " + tableSelection);
			
			tableModel = new DefaultTableModel();
			tableModel = buildTableModel(rs);
			jTable = new JTable(tableModel);
			BorderLayout borderLayout = new BorderLayout();
			setLayout(borderLayout);
			tableModel.fireTableChanged(null);
			jTable.getTableHeader().setReorderingAllowed(false);
			jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			jTable.setFillsViewportHeight(true);
			jTable.setEnabled(false);
			
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
		JButton addEntry = new JButton();
		JButton deleteEntry = new JButton();
		actionSearch = new JPanel();
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {0, 0, 0};
		gbl_panel_1.rowHeights = new int[] {0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnSearch = new JButton("Search");
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
		
		JButton btnAddEntry = new JButton("Add Entry");
		btnAddEntry.addActionListener(this);
		GridBagConstraints gbc_btnAddEntry = new GridBagConstraints();
		gbc_btnAddEntry.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddEntry.gridx = 0;
		gbc_btnAddEntry.gridy = 1;
		panel_1.add(btnAddEntry, gbc_btnAddEntry);
		
		JButton btnConfirm = new JButton("Confirm");
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.fill = GridBagConstraints.BOTH;
		gbc_btnConfirm.insets = new Insets(0, 0, 0, 5);
		gbc_btnConfirm.gridx = 1;
		gbc_btnConfirm.gridy = 1;
		panel_1.add(btnConfirm, gbc_btnConfirm);
		
		JButton btnDeleteEntry = new JButton("Delete Entry");
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
		
		JButton btnDatabase = new JButton("Database");
		GridBagConstraints gbc_btnDatabase = new GridBagConstraints();
		gbc_btnDatabase.fill = GridBagConstraints.BOTH;
		gbc_btnDatabase.gridx = 0;
		gbc_btnDatabase.gridy = 0;
		btnDatabase.addActionListener(this);
		panel_2.add(btnDatabase, gbc_btnDatabase);
		database = panel_2;
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
			else if(clicked.getActionCommand() == "Add Entry") {
				String[] buttonChoice = {"Add Entry"};
				guiState.Action(this.context, null, buttonChoice, null);
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
	        	System.out.println(rs.getObject(columnIndex));
	            vector.add(rs.getObject(columnIndex));
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

}
