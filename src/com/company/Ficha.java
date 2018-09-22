package com.company;

public class Ficha {
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String AZUL = "\u001B[34m";

    private int x;
    private int y;
    private int numero;
    private boolean esRojo;

    Ficha(){ }

    Ficha(int x, int y, int numero, boolean b){
        this.x = x;
        this.y = y;
        this.numero = numero;
        esRojo = b;
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

    @Override
    public String toString(){
        return (esRojo ? ROJO : AZUL) + getNumero() + RESET;
    }
/*
    public void setJugador(Jugador j){
        this.jugador = j;
    }

    public Jugador getJugador(){
        return jugador;
    }*/
}
