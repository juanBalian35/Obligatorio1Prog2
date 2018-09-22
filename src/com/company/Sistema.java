package com.company;

import java.util.ArrayList;

public class Sistema {
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

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
