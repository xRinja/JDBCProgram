package allpersonal;

import java.awt.event.ActionEvent;

public interface GUIState {
	
	public void Action(Context context, String[] data);
	public void Debug(String debug);
}
