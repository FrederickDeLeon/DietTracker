
public class Main {
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
            DietTracker tracker = new DietTracker();
            new DietTrackerUI(tracker).setVisible(true);
		});
    
	}
	
}
		
		