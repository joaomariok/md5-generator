package passwordgenerator.main;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Controller {
	
	private Worker _worker;
	private final String EMPTY_STRING = "";

	public Controller() {
		System.out.println("[Controller] Creating");
		
		_worker = new Worker();
	}
	
	public String Execute(String text) {
		System.out.println("[Controller] Executing");
		
		if (text.isEmpty()) {
			System.out.println("[Controller] Empty string");
			return EMPTY_STRING;
		}
		
		String hash = EMPTY_STRING;
		
		try {
			hash = _worker.Execute(text);
		} catch (Exception e) {
			System.out.println("[Controller] Error generating hash");
			hash = EMPTY_STRING;
			e.printStackTrace();
		}
		
		return hash;
	}
	
	public void CopyHashToClipboard(String text) {
		System.out.println("[Controller] Copying {" + text + "} to clipboard");
		
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	public void ClearClipboard() {
		System.out.println("[Controller] Cleaning clipboard");
		CopyHashToClipboard(EMPTY_STRING);
	}
}