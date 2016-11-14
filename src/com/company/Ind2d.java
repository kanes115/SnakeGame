package com.company;

/**
 * Created by kanes on 24.07.16.
 */
public class Ind2d {
    public int x, y;


    public Ind2d(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Ind2d(){
        x=0;
        y=0;
    }

    public Ind2d(Ind2d ind){
        set(ind);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveRight(){
        x++;
    }

    public void moveLeft(){x--;}

    public void moveUp(){
        y--;
    }

    public void moveDown(){ y++;}

    //-----------

    public void moveRight(int modulo){
        x++;
        x=x%modulo;
    }

    public void moveLeft(int modulo){
        x--;
        if(x<0) x=modulo;
    }

    public void moveUp(int modulo){
        y--;
        if(y<0) y=modulo;
    }

    public void moveDown(int modulo){
        y++;
        y=y%modulo;
    }

    public void set(Ind2d ind){
        this.x = ind.getX();
        this.y = ind.getY();
    }

    public void show(){
        System.out.print(x);
        System.out.print(".");
        System.out.println(y);
    }

    public boolean isEqual(Ind2d ind){
        if(ind.x != x) return false;
        if(ind.y != y) return false;
        return true;
    }

}
