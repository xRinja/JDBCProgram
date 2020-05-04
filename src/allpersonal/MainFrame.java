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
	
	public void setWindowName(String window) {
		this.setTitle("Sys Database Managment" + window);
	}
	// JFrame
	public MainFrame(){
		// Frame Config
		setWindowName("");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Exits program on close.
		
		// Origin Layout Config
		BorderLayout layout = new BorderLayout(); // Layout of mainframe.
		setLayout(layout); // Sets layout to frame.
		
		// Initialize Components
		server = new Server(); // Class to add server functions.
		context = new Context(); // Initialize Context 
		loginState = new LoginState(); // Initialize LoginState
		menuState = new MenuState(); // Initialize MenuState
		tableSelectState = new TableSelectState(); // Initialize TableSelectState
		add_State = new addState(); // Initialize AddState
		textPanel = new TextPanel(server.connection, server.databaseTypes); // Class to add entries.
		
		// First State
		LoginState();

		// Constructing GUI elements.
				setSize(1200,800);
				setVisible(true);		
	}
	
	public void LoginState(){
		// Switch to loginState
		setWindowName(" | Login");
		loginState.Action(context, null, server.connection, server.databaseTypes);
		add(loginState.getTextField(), BorderLayout.CENTER); 
		add(loginState.getButton(), BorderLayout.SOUTH);
		repaint();
		validate();
		loginState.setGUIState(new GUIState() {
		
			@Override
			public void Action(Context context, Server server2, String[] dataSetOne, String[] dataSetTwo) {
				server.logConnectionDetails(dataSetOne);
				remove(loginState.getTextField());
				remove(loginState.getButton());
				MenuState();
			}
		});
	}
	
	public void MenuState(){
		// Switch to Menu State
		menuState.Action(context, null, server.schemaNames, null);
		add(menuState.getButton(), BorderLayout.CENTER);
		repaint();
		validate();
		setWindowName(" | Databases");
		menuState.setGUIState(new GUIState() {
			
			@Override
			public void Action(Context context, Server server2, String[] dataSetOne, String[] dataSetTwo) {
				if(context.getState().toString().equalsIgnoreCase("Menu State")){
				if(loginState != null){
					server.getSchema = dataSetOne[0];
					server.setMetaData();
					remove(menuState.getButton());
					TableState();
					}
				
				} else {
					System.out.println("tableSelectstate not initiated");
				}
			}
		});
	}
	
	public void TableState(){
		// Switch to Table State
		tableSelectState.Action(context, server, server.tableNames, null);
		add(tableSelectState.getTableList(), BorderLayout.WEST);
		add(tableSelectState.getTableFill(), BorderLayout.CENTER);
		repaint();
		validate();
		setWindowName(" | Tables Information");
		// Command Listener
		tableSelectState.setGUIState(new GUIState() {

			@Override
			public void Action(Context context, Server server2, String[] dataSetOne, String[] dataSetTwo) {
				System.out.println("Main frame reconizes JList Click");
				if(context.getState().toString().equalsIgnoreCase("Table State")){
					if(menuState != null){
						remove(tableSelectState.getTableList());
						/*server.setMetaData();
						textPanel = new TextPanel(server.columnsNames, server.databaseTypes);
						AddState();
						add(add_State, BorderLayout.SOUTH);
						add(textPanel, BorderLayout.CENTER);
						repaint();
						validate();*/
						System.out.println("Main frame reconizes JList Click");
					}
				}
				
			}
		});
	}

	public void AddState(){
		// Switch to Adding State
		add_State.Action(context, null, null, null);
		// Command Listener
		add_State.setGUIState(new GUIState() {
			
			@Override
			public void Action(Context context, Server server2, String[] dataSetOne, String[] dataSetTwo) {
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
