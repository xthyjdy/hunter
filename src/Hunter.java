import javax.swing.JFrame;
import javax.swing.JPanel;




public class Hunter {
	private JFrame frame;
	private HunterAction panel;

	public static void main(String[] args) {
		new Hunter().init();
	}
	
	private void init() {
		frame = new JFrame("hunter_0.1");
		panel = new HunterAction();
		
		frame.setSize(320, 320);
		frame.setLocation(100, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.add(new HunterAction());		
		
		frame.setVisible(true);
	}
}