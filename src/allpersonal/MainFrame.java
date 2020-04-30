package allpersonal;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class MainFrame extends JFrame{
	
	// Variables
	private Toolbar toolbar;
	private TextPanel textPanel;
	private Server server;
	private String statement;
	private LoginState loginState;
	private MenuState menuState;
	private TableSelectState tableSelectState;
	private addState add_State;
	private Context context;
	private GUIState currentGUIState;
	
	// JFrame
	public MainFrame(){
		// Frame Config
		super("Sys Database Management System"); // Window Title.
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Exits program on close.
		
		// Origin Layout Config
		BorderLayout layout = new BorderLayout(); // Layout of mainframe.
		setLayout(layout); // Sets layout to frame.
		
		// Initialize Components
		context = new Context(); // Initialize Context 
		loginState = new LoginState(); // Initialize LoginState
		menuState = new MenuState(); // Initialize MenuState
		tableSelectState = new TableSelectState();
		add_State = new addState(); // Initialize AddState
		server = new Server(); // Class to add server functions.
		System.out.println("Server Started | Not Connected");
		textPanel = new TextPanel(server.connection, server.databaseTypes); // Class to add entries.
		
		// First State
		LoginState();

		// Adding components to frame.
		add(textPanel, BorderLayout.CENTER); 
		add(loginState, BorderLayout.SOUTH);
		
		// Constructing GUI elements.
				setSize(1200,800);
				setVisible(true);		
	}
	// Login State
	public void LoginState(){
		// Switch to loginState
		//System.out.println(context.getState().toString());
		loginState.Action(context, null);
		// Command Listener
		loginState.setGUIState(new GUIState() {
			@Override
			public void Debug(String debug) {
				System.out.println(debug);
			}
			
			@Override
			public void Action(Context context, String[] strings) {
				if(context.getState().toString().equals("Start State")) {
					if(textPanel != null){
						remove(textPanel);
					}
					if(toolbar != null){
					remove(loginState);
					}
					server.logConnectionDetails(textPanel.columnTextField);
					server.setMetaData();
					MenuState();
					add(menuState, BorderLayout.CENTER);
					repaint();
					validate();
				}
				else {
					System.out.println("Start State not confimed");
				}
			}
		});
	}
	// Menu State
	public void MenuState(){
		// Switch to Menu State
		//System.out.println(context.getState().toString());
		menuState.Action(context, server.schemaNames);
		// Command Listener
		menuState.setGUIState(new GUIState() {
			@Override
			public void Debug(String debug) {
				System.out.println(debug);
			}
			
			@Override
			public void Action(Context context, String[] strings) {
				if(context.getState().toString().equalsIgnoreCase("Menu State")){
				if(loginState != null){
					remove(menuState);
					TableState();
					add(tableSelectState, BorderLayout.CENTER);
					repaint();
					validate();
					}
				
				} else {
					System.out.println("tableSelectstate not initiated");
				}
			}
		});
	}
	// Table State
	public void TableState(){
		// Switch to Table State
		//System.out.println(context.getState().toString());
		tableSelectState.Action(context, server.tableNames);
		// Command Listener
		tableSelectState.setGUIState(new GUIState() {
			@Override
			public void Debug(String debug) {
				System.out.println(debug);
			}
			
			@Override
			public void Action(Context context, String[] strings) {
				if(context.getState().toString().equalsIgnoreCase("Table State")){
					if(menuState != null){
						remove(tableSelectState);
						server.setMetaData();
						textPanel = new TextPanel(server.columnsNames, server.databaseTypes);
						AddState();
						add(add_State, BorderLayout.SOUTH);
						add(textPanel, BorderLayout.CENTER);
						repaint();
						validate();
					}
				}
				
			}
		});
	}
	// Add State
	public void AddState(){
		// Switch to Adding State
		//System.out.println(context.getState().toString());
		add_State.Action(context, server.columnsNames);
		// Command Listener
		add_State.setGUIState(new GUIState() {
			@Override
			public void Debug(String debug) {
				System.out.println(debug);
			}
			
			@Override
			public void Action(Context context, String[] strings) {
				if(context.getState().toString().equalsIgnoreCase("Add State")){
					if(tableSelectState!= null){
						remove(tableSelectState);
						server.AddingEntries(textPanel.columnTextField);
						System.out.println("Record Saved.");
					}
				}
			}
		});
	}
	// Search State
	public void SearchState(){
		
	}
}
