package com.sks.ZombieGame;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Write a description of class App here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
@SuppressWarnings("serial")
public class Frame extends JPanel
{
    Random r;
    int x,y,vx,vy,sqrSize;
    LinkedList<Gobject> list;
    Point sqrLoc;
    
    {
        sqrSize = 40;
        x = 100;
        y = 100;
    }
    
    public Frame() {
        r = new Random();
        list = new LinkedList<Gobject>();
        sqrLoc = new Point(getWidth()/2-20,getHeight()/2-20);
    }
    
    public Point getPos() {
        return new Point(list.get(0).x,list.get(0).y);
    }
    
    public void setSqrLoc(Point p) {
        sqrLoc = p;
    }
    
    public Point getSqrLoc() {
        return sqrLoc;
    }
    
    public void setPos(Point e) {
        list.get(0).x = (int)e.getX();
        list.get(0).y = (int)e.getY();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (vx > 0)
            vx--;
        else if (vx < 0)
            vx++;
            
        if (vy > 0)
            vy--;
        else if (vy < 0)
            vy++;
            
        sqrLoc.x += vx;
        sqrLoc.y += vy;
        g.drawRect(sqrLoc.x-sqrSize/2, sqrLoc.y-sqrSize/2,sqrSize,sqrSize);
        
        for (Gobject go : list) {
            switch (go.type) {
                case 1:
                    g.drawOval(go.x-go.width/2,go.y-go.height/2,go.width,go.height);
                    break;
                case 0:
                    g.drawLine(go.x,go.y,(int)(go.x+20*Math.cos(go.dir)),(int)(go.y+20*Math.sin(go.dir)));
                    break;
                case 2:
                    g.fillOval(go.x,go.y,go.width,go.height);
                    break;
            }
        }
        int d = (int)(getPos().distance(sqrLoc));
        int ds = d/16;
        for(int i = 0; i < ds; i++) {
            g.drawLine(
            (int)(sqrLoc.x+i*16*Math.cos(getAngle(sqrLoc, getPos()))),
            (int)(sqrLoc.y+i*16*Math.sin(getAngle(sqrLoc, getPos()))),
            (int)(sqrLoc.x+(i*16+8)*Math.cos(getAngle(sqrLoc, getPos()))),
            (int)(sqrLoc.y+(i*16+8)*Math.sin(getAngle(sqrLoc, getPos())))
            );
        }
    }
    
    public double getAngle(Point a, Point b) {
        return a.getX() > b.getX() ? Math.PI+Math.atan((b.getY() - a.getY())/(b.getX() - a.getX())) : Math.atan((b.getY() - a.getY())/(b.getX() - a.getX()));
    }
}
