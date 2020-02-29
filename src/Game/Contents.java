package Game;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Contents extends JPanel implements ActionListener, KeyListener {
    private int xBasket = 200, yBasket = 290; // Позиция на кошницата
    private int xMove = 0; // увеличава или намалява позицията на кошницата
    private int yOval = 0;
    private int yOval2 = 0;
    private int yOval3 = 0;
    private int yOval4 = 0;
    Random r = new Random();
    private int xOval = defineXOval(r);
    private int xOval2 = defineXOval(r);
    private int xOval3 = defineXOval(r);
    private int xOval4 = defineXOval(r);
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

    public static int defineXOval(Random r)
    {
        return r.nextInt((550 - 100) + 1) + 100;
    }

    public Contents() {
        super.setDoubleBuffered(true); // За по-гладко местене на фигурите
        t = new Timer(10, this); // Delay са милисекунди. С keyword this указваме, че става дума за този клас
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
                yOval = 0;
                xOval = defineXOval(r);
            }
            if (yOval2 == yBasket - 8) {
                if (xOval2 >= xBasket - 4 && xOval2 < xBasket + 20) {
                    points++;
                }
                yOval2 = 0;
                xOval2 = defineXOval(r);
            }
            if (yOval3 == yBasket - 8) {
                if (xOval3 >= xBasket - 4 && xOval3 < xBasket + 20) {
                    points++;
                }
                yOval3 = 0;
                xOval3 = defineXOval(r);
            }
            if (yOval4 == yBasket - 8) {
                if (xOval4 >= xBasket - 4 && xOval4 < xBasket + 20) {
                    points++;
                }
                yOval4 = 0;
                xOval4 = defineXOval(r);
            }

            if (timerUnits > 0 && timerUnits % 65 == 0) {
                seconds--;
                if (seconds == 0) {
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
                        String name = field.getText();
                        dictionary.put(id + ". " + name, points);
                        StringBuilder sortedResults = new StringBuilder();
                        sortedResults.append("<html>");
                        Object[] a = dictionary.entrySet().toArray();
                        Arrays.sort(a, new Comparator() {
                            public int compare(Object o1, Object o2) {
                                return ((Map.Entry<String, Integer>) o2).getValue()
                                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
                            }
                        });
                        for (Object e : a) {
                            sortedResults.append(((Map.Entry<String, Integer>) e).getKey() + ": " + ((Map.Entry<String, Integer>) e).getValue() + "<br/>");
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
            timerUnits = 0;
            yOval = 0;
            yOval2 = 0;
            yOval3 = 0;
            yOval4 = 0;
            seconds = 60;
            points = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        xMove = 0;
    }
}
