package com.company;

import java.util.*;

public class Sistema {
    Scanner scanner = new Scanner(System.in);
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private ArrayList<Partida> partidas = new ArrayList<>();
    Jugador j1 = new Jugador("baba","1",15);
    Jugador j2 = new Jugador("asd","2",15);
    Partida partida1 = new Partida(j1, j2, 1, GregorianCalendar.getInstance().getTime());
    Partida partida2 = new Partida(j1, j2, 2, GregorianCalendar.getInstance().getTime());

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }
    public void setPartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void registrarJugador(Jugador jugador){
        jugadores.add(jugador);
    }

    public void jugar(Jugador jugador1, Jugador jugador2, int formaDeTerminar, Date fecha){
        Partida partida = new Partida(jugador1, jugador2, formaDeTerminar, fecha);
        partidas.add(partida);
        partida.comenzar();
    }

    public void replicar(){
        partidas.add(partida1);
        partidas.add(partida2);
        Collections.sort(partidas);
        for (int i = 0; i < getPartidas().size(); i++) {
            System.out.println((i+1)+") " + partidas.get(i));
        }
        int opcion=Prueba.ingresarEnteroEnRango(scanner,1,partidas.size()+1,("Seleccione la partida que desea replicar (1-"+(partidas.size())+")"));
        Partida partidaReplicar = partidas.get(opcion);
        for (int i = 0; i < partidaReplicar.getMovimientos().size(); i++) {
            String mov= partidaReplicar.getMovimientos().get(i);
        }
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
