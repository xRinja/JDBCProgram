package allpersonal;

public class Context {
	// Private Variables
	private GUIState guiState;
	
	// Public Variables
	
	// Constructor
	public Context(){
		guiState = null;
	}
	
	// Getter
	public GUIState getState(){
		
		return guiState;
	}
	
	// Setter
	public void setState(GUIState guiState){
		this.guiState = guiState;
	}
}
