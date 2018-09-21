package com.company;

public class Partida {
    private Tablero tablero = new Tablero();
    private Jugador jugador1, jugador2;

    Partida(Jugador j1, Jugador j2){
        jugador1 = j1;
        jugador2 = j2;
    }

    //HAHHJAJAJAJAJ
    public void comenzar(){
        System.out.println(tablero);
    }
}
