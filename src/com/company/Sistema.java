package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

    public void registrarJugador(Jugador jugador){
        jugadores.add(jugador);
    }

    public void jugar(Jugador jugador1, Jugador jugador2){
        Partida partida = new Partida(jugador1, jugador2);
        partida.comenzar();
    }

    public void replicar(){
        System.out.println("Replicar");
    }

    public void ranking(){
        System.out.println("Ranking");
    }

    public Jugador buscarJugador(String alias){
        for(Jugador jugador : jugadores)
            if(jugador.getAlias().equals(alias))
                return jugador;

        return null;
    }
}
