package com.sks.ZombieGame;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class App extends JFrame implements MouseMotionListener, ActionListener, MouseListener
{
    Frame f;
    Timer t;
    int offset;
    int fac, inc, ammo;
    double interval;
    boolean moveLeft, moveUp, moveRight, moveDown, fire;

    public App() {
        f = new Frame();
        add(f);
        setSize(1000,1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        t = new Timer(1000/30, this);
        f.addMouseListener(this);
        f.addMouseMotionListener(this);
        f.list.add(new Gobject(350,350,20,20,0,1, f));
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor() {            
            public boolean postProcessKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                        char c = e.getKeyChar();
                        switch(c) {
                            case 'a':
                            moveLeft = true;
                            break;
                            
                            case 'd':
                            moveRight = true;
                            break;
                            
                            case 'w':
                            moveUp = true;
                            break;
                            
                            case 's':
                            moveDown = true;
                            break;
                            
                            case 'q':
                                if(ammo/10 > 0) {
                                    for ( int i = 0; i < 100; i++) {
                                        f.list.add(new Gobject(f.getSqrLoc().x, f.getSqrLoc().y, 20, 20, f.getAngle(f.getSqrLoc(), f.getPos())+i*2*Math.PI/(100), 0, f));
                                    }
                                    ammo-=10;
                                }
                            break;
                        }
                    } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                        char c = e.getKeyChar();
                        switch(c) {
                            case 'a':
                            moveLeft = false;
                            break;
                            
                            case 'd':
                            moveRight = false;
                            break;
                            
                            case 'w':
                            moveUp = false;
                            break;
                            
                            case 's':
                            moveDown = false;
                            break;
                            
                            case 'p':
                            System.exit(0);
                            
                        }
                    }
                return true;
            }
        });
        fac = 20;
        interval = 1;
        t.start();
    }
    
    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);
    }
    
    public void mouseMoved(MouseEvent e) {
        Point ePt = e.getPoint();
        f.setPos(ePt);
        f.repaint();
    }
    
    public void mouseDragged(MouseEvent e) {
        fire = true;
        Point ePt = e.getPoint();
        f.setPos(ePt);
        f.repaint();
    }
    
    public void mouseExited(MouseEvent e) {
        
    }
    
    public void mouseEntered(MouseEvent e) {
        
    }
    
    public void mouseReleased(MouseEvent e) {
        fire = false;
    }
    
    public void mousePressed(MouseEvent e) {
        
    }
    
    public void mouseClicked(MouseEvent e) {
        
    }
    
    public void actionPerformed(ActionEvent e) {
        LinkedList<Gobject> temp = new LinkedList<Gobject>(f.list);
        for (Gobject g : temp) {
            g.step();
            if ((g.x > f.getWidth() || g.x < 0 || g.y > f.getHeight() || g.y < 0) && g.type == 0){
                f.list.remove(g);
            }
            
            if(g.type == 2) {
                for (Gobject go : temp) {
                    if(go.type == 0 && new Rectangle(g.x,g.y,g.width,g.height).intersects(new Line2D.Double(new Point(go.x,go.y), new Point((int)(go.x+20*Math.cos(go.dir)),(int)(go.y+20*Math.sin(go.dir)))).getBounds())) {
                        f.list.remove(go);
                        f.list.remove(g);
                        ammo++;
                    }
                }
                if (new Rectangle(g.x,g.y,g.width,g.height).intersects(new Rectangle(f.getSqrLoc().x-f.sqrSize/2,f.getSqrLoc().y-f.sqrSize/2,f.sqrSize,f.sqrSize))) {
                    System.exit(0);
                }
            }
        }
        if (inc > (int)interval) {
            double rand = Math.random();
            f.list.add(new Gobject((int)(Math.cos(Math.PI*4*rand)*2*f.getWidth()),(int)(Math.sin(Math.PI*4*rand)*f.getHeight()),20,20,f.getAngle(new Point(1,1), f.getPos()), 2, f));
            inc = 0;
        } else {
            interval-=0.4;
            inc++;
        }
            
        Point sqrLoc = f.getSqrLoc();
        if (fire) {
            f.list.add(new Gobject(f.getSqrLoc().x, f.getSqrLoc().y, 20, 20, f.getAngle(sqrLoc, f.getPos())+(Math.random()-.5)/5, 0, f));
        }
        
        System.out.println(ammo);
        
        sqrLoc.x = moveLeft ? sqrLoc.x-5 : sqrLoc.x;
        sqrLoc.x = moveRight ? sqrLoc.x+5 : sqrLoc.x;
        sqrLoc.y = moveUp ? sqrLoc.y-5 : sqrLoc.y;
        sqrLoc.y = moveDown ? sqrLoc.y+5 : sqrLoc.y;
        f.setSqrLoc(sqrLoc);
        f.repaint();
    }
}
