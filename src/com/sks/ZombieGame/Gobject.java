package com.sks.ZombieGame;

import java.awt.Point;
import java.util.ArrayList;

public class Gobject
{
    int x,y,width,height,type,fac;
    double dir;
    ArrayList<Gobject> childs;
    Frame f;
    
    public Gobject(int nx, int ny, int nwidth, int nheight, double dir, int type, Frame f) {
        childs = new ArrayList<Gobject>();
        this.type = type;
        this.dir = dir;
        this.f = f;
        x = nx;
        y = ny;
        height = nheight;
        width = nwidth;
        fac = 40;
    }
    
    public void step() {
        if(type == 0) {
            x += Math.cos(dir)*fac;
            y += Math.sin(dir)*fac;
        }
        if (type == 2) {
            dir = f.getAngle(new Point(x,y), f.getSqrLoc());
            x += Math.cos(dir)*fac/10;
            y += Math.sin(dir)*fac/10;
        }
    }
    
    public void move(double angle, int factor) {
        x += (int)factor*Math.cos(angle);
        y += (int)factor*Math.sin(angle);
        System.out.println(Math.cos(angle));
    }
    
    public void addChilds (ArrayList<Gobject> list) {
        if(childs != null) {
            for (Gobject go : list) {
                go.addChilds(list);
            }
        } else {
            list.add(this);
        }
    }
}
