package Game;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Contents extends JPanel implements ActionListener, KeyListener
{
    private int xBasket = 200, yBasket = 290; // Позиция на кошницата
    private int xMove = 0;
    private int yOval = 8;
    private Timer t; // За да определя през какъв период от време ми се обновява екрана

    public Contents(){
        super.setDoubleBuffered(true); // За по-гладко местене на фигурите
        t = new Timer(7, this); // Delay са милисекунди. С keyword this указваме, че става дума за този клас
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
    @Override // Показвам, че презаписвам този метод при съществуващ друг метод в друг клас
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g; // Това не е наложително, но се прави за по-голям избор от елементи
        g2d.drawString("\\___/", xBasket, yBasket);
        g2d.drawOval(200, yOval, 8, 8);
    }


    public void move(){
        yOval++;
        if (xBasket <= 400)
        {
            xBasket = xBasket + xMove;
        }
        else {
            xBasket = 400;
        }

        if (xBasket >= 100)
        {
            xBasket = xBasket + xMove;
        } else {
            xBasket = 100;
        }
    }

    @Override // С този метод следя какво прави потребителят
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if (c == KeyEvent.VK_A)
        {
            xMove = -1;
        }
        if (c == KeyEvent.VK_D)
        {
            xMove = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        xMove = 0;
    }
}
