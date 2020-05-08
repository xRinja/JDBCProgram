package allpersonal;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainFrame extends JFrame{
	
	// Variables
	private Server server;
	private LoginState loginState;
	private MenuState menuState;
	private TableSelectState tableSelectState;
	private addState add_State;
	private Context context;
	
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
		
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		// First State
		LoginState();

		// Constructing GUI elements.
				//setSize(1200,800);
				setVisible(true);	
	}
	
	public void LoginState(){
		// Switch to loginState
		setWindowName(" | Login");
		loginState.Action(context, null, server.connection, server.databaseTypes);
		add(loginState.getTextField(), BorderLayout.CENTER); 
		add(loginState.getButton(), BorderLayout.SOUTH);
		pack();
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
		pack();
		repaint();
		validate();
		setWindowName(" | Databases");
		menuState.setGUIState(new GUIState() {
			
			@Override
			public void Action(Context context, Server server2, String[] dataSetOne, String[] dataSetTwo) {
				if(context.getState().toString().equalsIgnoreCase("Menu State")){
				if(loginState != null){
					server.setMetaData(dataSetOne[0]);
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
		add(tableSelectState.getActionSearch(), BorderLayout.NORTH);
		add(tableSelectState.getDataBaseButton(), BorderLayout.SOUTH);
		pack();
		repaint();
		validate();
		setWindowName(" | Tables Information");
		// Command Listener
		tableSelectState.setGUIState(new GUIState() {

			@Override
			public void Action(Context context, Server server2, String[] dataSetOne, String[] dataSetTwo) {
				if(context.getState().toString().equalsIgnoreCase("Table State")){
					if(dataSetOne[0].equalsIgnoreCase("Database")) {
						remove(tableSelectState.getTableList());
						remove(tableSelectState.getTableFill());
						remove(tableSelectState.getActionSearch());
						remove(tableSelectState.getDataBaseButton());
						MenuState();
					}
					else if(dataSetOne[0].equalsIgnoreCase("Add Entry")) {
						server.setTableDate(dataSetTwo[0]);
						remove(tableSelectState.getTableList());
						remove(tableSelectState.getTableFill());
						remove(tableSelectState.getActionSearch());
						remove(tableSelectState.getDataBaseButton());
						AddState();
					}
				}
				
			}
		});
	}

	public void AddState(){
		// Switch to Adding State
		add_State.Action(context, null, server.columnsNames, null);
		add(add_State.getFuncButtons(), BorderLayout.SOUTH);
		add(add_State.getTextField(), BorderLayout.CENTER);
		pack();
		repaint();
		validate();
		setWindowName(" | Add Entry: " + server.table);
		for(int i = 0; i < server.getColumnNames().length; i++) {
			System.out.println(server.getColumnNames()[i]);
		}
		
		// Command Listener
		add_State.setGUIState(new GUIState() {
			
			@Override
			public void Action(Context context, Server server2, String[] dataSetOne, String[] dataSetTwo) {
				if(context.getState().toString().equalsIgnoreCase("Add State")){
					if(tableSelectState!= null){
						if(dataSetOne[0].equalsIgnoreCase("Cancle")) {
							remove(add_State.getFuncButtons());
							remove(add_State.getTextField());
							TableState(); 
						}
						else if(dataSetOne[0].equalsIgnoreCase("Save")) {
							server.AddingEntries(add_State.columnTextField);
							System.out.println("Record Saved.");
							remove(add_State.getFuncButtons());
							remove(add_State.getTextField());
							TableState(); 
						}
						else if(dataSetOne[0].equalsIgnoreCase("Confirm")) {
							
						}
					}
				}
			}
		});
	}
	
	// Search State
	public void SearchState(){
		
	}
}
