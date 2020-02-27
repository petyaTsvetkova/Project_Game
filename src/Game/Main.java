package Game;

import javax.swing.JFrame;

public class Main extends JFrame {

    public Main(){
        super.setTitle("Game");
        super.setSize(600, 400);
        super.setResizable(false);
        super.add(new Contents());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
