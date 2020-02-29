package Game;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.stream.Collectors;

public class Contents extends JPanel implements ActionListener, KeyListener {
    private int xBasket = 200, yBasket = 290; // Позиция на кошницата
    private int xMove = 0;
    private int yOval = 8;
    private int yOval2 = 8;
    private int yOval3 = 8;
    private int yOval4 = 8;
    Random r = new Random();
    private int xOval = r.nextInt((300 - 250) + 1) + 300;
    private int xOval2 = r.nextInt((300 - 250) + 1) + 300;
    private int xOval3 = r.nextInt((300 - 250) + 1) + 300;
    private int xOval4 = r.nextInt((300 - 250) + 1) + 300;
    private Timer t; // За да определя през какъв период от време ми се обновява екрана
    private int points = 0;
    private int id = 0;
    Map<String, Integer> dictionary = new HashMap<String, Integer>();
    private int timerUnits = 0;
    private int seconds = 60;
    JLabel lbl1 = new JLabel();
    JTextField field = new JTextField(10);
    JButton b = new JButton("Save name");
    private boolean mustRun = true;


    public Contents() {
        super.setDoubleBuffered(true); // За по-гладко местене на фигурите
        t = new Timer(1, this); // Delay са милисекунди. С keyword this указваме, че става дума за този клас
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        super.add(field);
        super.add(b);
        field.setVisible(false);
        b.setVisible(false);
    }

    @Override // Показвам, че презаписвам този метод при съществуващ друг метод в друг клас
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Това не е наложително, но се прави за по-голям избор от елементи
        g2d.drawString("\\___/", xBasket, yBasket);
        g2d.drawOval(xOval, yOval, 8, 8);
        g2d.drawOval(xOval2, yOval2, 8, 8);
        g2d.drawOval(xOval3, yOval3, 8, 8);
        g2d.drawOval(xOval4, yOval4, 8, 8);

    }

    public void move() {
        if (mustRun == true) {
            yOval++;
            if (timerUnits > 200) {
                yOval2++;
            }
            if (timerUnits > 300) {
                yOval3++;
            }
            if (timerUnits > 400) {
                yOval4++;
            }

            super.add(lbl1);
            lbl1.setVisible(true);
            if (yOval == yBasket - 8) {
                if (xOval >= xBasket - 4 && xOval < xBasket + 20) {
                    points++;
                }
                yOval = 8;
                xOval = r.nextInt(((550 - 100) + 1) + 100);

            }
            if (yOval2 == yBasket - 8) {
                if (xOval2 >= xBasket - 4 && xOval2 < xBasket + 20) {
                    points++;
                }
                yOval2 = 8;
                xOval2 = r.nextInt(((550 - 100) + 1) + 100);
            }
            if (yOval3 == yBasket - 8) {
                if (xOval3 >= xBasket - 4 && xOval3 < xBasket + 20) {
                    points++;
                }
                yOval3 = 8;
                xOval3 = r.nextInt(((550 - 100) + 1) + 100);
            }
            if (yOval4 == yBasket - 8) {
                if (xOval4 >= xBasket - 4 && xOval4 < xBasket + 20) {
                    points++;
                }
                yOval4 = 8;
                xOval4 = r.nextInt(((550 - 100) + 1) + 100);
            }

            if (timerUnits > 0 && timerUnits % 65 == 0) {
                seconds--;
                if (seconds == 50) {
                    mustRun = false;
                    id++;
                }
            }
            timerUnits++;
            if (mustRun == false) {
               // lbl1.setText("Points: " + points + " Press Enter to start again");
                lbl1.setText("Enter your name ");
                field.setVisible(true);
                b.setVisible(true);


                b.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        field.setVisible(false);
                        b.setVisible(false);
                        String name =  field.getText();
                        dictionary.put(id + ". " + name, points);
                        StringBuilder sortedResults = new StringBuilder();
                        sortedResults.append("<html>");
                        for (Map.Entry<String, Integer> e : dictionary.entrySet()) {
                            sortedResults.append(e.getKey() + ": " + e.getValue() + "<br/>");
                        }
                        sortedResults.append("Press Enter to start again</html>");
                        lbl1.setText(String.valueOf(sortedResults));
                        sortedResults.setLength(0);
                       // lbl1.setText(name + "'s points: " + points + " Press Enter to start again");
                    }
                });
            } else {
                lbl1.setText("Points: " + points + " Seconds: " + seconds);
            }
            if (xBasket <= 550) {
                xBasket = xBasket + xMove;
            } else {
                xBasket = 550;
            }

            if (xBasket >= 10) {
                xBasket = xBasket + xMove;
            } else {
                xBasket = 10;
            }
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

        if (c == KeyEvent.VK_A) {
            xMove = -1;
        }
        if (c == KeyEvent.VK_D) {
            xMove = 1;
        }

        if (c == KeyEvent.VK_ENTER && mustRun == false) {
            mustRun = true;
            seconds = 60;
            points = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        xMove = 0;
    }
}
