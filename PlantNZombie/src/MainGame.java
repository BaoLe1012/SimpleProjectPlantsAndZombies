import javax.swing.JFrame;

public class MainGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Plants vs Zombies - Basic Project");
        
        GamePanel panel = new GamePanel();
        
        frame.add(panel);
        frame.pack(); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 
        frame.setResizable(false); 
        frame.setVisible(true);
        
        panel.startGame();
    }
}
