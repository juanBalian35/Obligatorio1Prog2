package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Partida {
    private Tablero tablero = new Tablero();
    private Jugador jugador1, jugador2;
    private int formaDeTerminar;
    private ArrayList<String> movimientos = new ArrayList<>();


    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(ArrayList<String> movimientos) {
        this.movimientos = movimientos;
    }

    Partida(Jugador j1, Jugador j2, int forma){
        jugador1 = j1;
        jugador2 = j2;
        formaDeTerminar = forma;
        inicializarFichas();
    }

    private void inicializarFichas(){
        Ficha[] fichas1 = jugador1.getFichas();
        Ficha[] fichas2 = jugador2.getFichas();

        for(int i = 0; i < Jugador.NUM_FICHAS; ++i){
            //Fichas del jugador 1
            Ficha ficha = new Ficha(i, Tablero.LARGO - 1, Jugador.NUM_FICHAS-i, true);
            fichas1[i] = ficha;
            ficha = new Ficha(Tablero.ANCHO - 1 - i, 0, Jugador.NUM_FICHAS-i, false);
            fichas2[i] = ficha;
        }

        jugador1.setFichas(fichas1);
        jugador2.setFichas(fichas2);

        tablero.actualizar(jugador1,jugador2);
    }

    //TODO: HAHHJAJAJAJAJ
    public void comenzar(){
        tablero.actualizar(jugador1,jugador2);
        tablero.mostrar(false);
    }
}
