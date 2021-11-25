package passwordgenerator.main;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Controller {
	
	private Worker _worker;

	public Controller() {
		System.out.println("[Controller] Creating");
		
		_worker = new Worker();
	}
	
	public String Execute(String text) {
		System.out.println("[Controller] Executing");
		
		String hash = "";
		
		try {
			hash = _worker.Execute(text);
		} catch (Exception e) {
			hash = "[Controller] Error generating hash";
			e.printStackTrace();
		}
		
		return hash;
	}
	
	public void CopyHashToClipboard(String text) {
		System.out.println("[Controller] Copying hash to clipboard");
		
		if (text.isEmpty()) {
			System.out.println("[Controller] Empty string");
		}
		
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	public void ClearClipboard() {
		System.out.println("[Controller] Cleaning clipboard");
		CopyHashToClipboard("");
	}
}