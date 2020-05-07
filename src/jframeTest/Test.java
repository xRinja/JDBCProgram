package jframeTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.table.TableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.CardLayout;
import javax.swing.SpringLayout;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.border.EtchedBorder;
import javax.swing.ScrollPaneConstants;

public class Test extends JFrame {
	private JTable table;
	private JTextField txtSearchBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Test() throws SQLException {
		setSize(800,600);
		// Code for Table
		Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.121:8457/videogamecollection", "root", "finger00");
		Statement statement = null;
		PreparedStatement ps = null;
		java.sql.ResultSetMetaData rsmd = null;
		DatabaseMetaData dbmd = connection.getMetaData();
		ps = connection.prepareStatement("select * from " + "PS4");
		HashMap<String, String> metaDataHash = new HashMap<>(); // Column Type.
		ResultSet rs = ps.executeQuery();
		System.out.println("Result set is from: " + "PS4");
		
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel = buildTableModel(rs);
		String[] systems = {"PS4", "SWITCH", "PC"};
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable(tableModel);
		panel.add(table, BorderLayout.CENTER);
		
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
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.WEST);
		String[] consoles = {"PS4", "SWITCH", "PC"};
		panel_3.setLayout(new BorderLayout(0, 0));
		JList list = new JList(consoles);
		JScrollPane sp = new JScrollPane(list);
		list.setBackground(Color.WHITE);
		list.setBorder(null);
		list.setVisibleRowCount(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_3.add(sp, BorderLayout.CENTER);
		
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
		panel_2.add(btnDatabase, gbc_btnDatabase);
		//pack();
		setVisible(true);
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

}
