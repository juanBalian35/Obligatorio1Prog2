package com.company;

public class Ficha {
    private int x;
    private int y;
    private int numero;

    Ficha(int x, int y, int numero){
        this.x = x;
        this.y = y;
        this.numero = numero;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setNumero(int numero){
        this.numero = numero;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getNumero(){
        return numero;
    }
}
