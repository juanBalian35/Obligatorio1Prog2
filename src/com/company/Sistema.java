package com.company;

import java.util.*;

/*
 * Creado por:
 *   Juan Balian - 211150
 *   Agustín Introini - 211064
 * */

public class Sistema {
    private Scanner scanner = new Scanner(System.in);
    private static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private ArrayList<Partida> partidas = new ArrayList<>();

    public ArrayList<Partida> getPartidas() {
        return partidas;
    }
    public void setPartidas(ArrayList<Partida> partidas) {
        this.partidas = partidas;
    }

    public static ArrayList<Jugador> getJugadores() {
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
        partidas.add(partida.comenzar());
    }

    public void replicar(){
        Collections.sort(partidas);
        if(partidas.isEmpty()){
            System.out.println("\nAún no se han jugado partidas\n");
            return;
        }

        for (int i = 0; i < getPartidas().size(); i++)
            System.out.println((i + 1) + ") " + partidas.get(i));

        int opcion = Prueba.ingresarEnteroEnRango(scanner, 1, partidas.size(), ("Seleccione la partida que desea replicar (1-" + (partidas.size()) + ")"));
        Partida partidaReplicar = partidas.get(opcion - 1);
        Tablero tablero = new Tablero();
        partidaReplicar.inicializarFichas();
        System.out.println("Inicia la partida entre " + partidaReplicar.getJugador1().getAlias() + " y " + partidaReplicar.getJugador2().getAlias());
        tablero.actualizar(partidaReplicar.getJugador1(), partidaReplicar.getJugador2());
        tablero.mostrar(true);
        System.out.print("Presione Enter para continuar...");
        scanner.nextLine();

        for (int i = 0; i < partidaReplicar.getMovimientos().size(); i++) {
            String mov = partidaReplicar.getMovimientos().get(i);

            if(mov.charAt(0) == 'X')
                break;

            String posicion = "";
            switch (mov.charAt(2)) {
                case 'A':
                    posicion = "adelante";
                    break ;
                case 'D':
                    posicion = "la derecha";
                    break;
                case 'I':
                    posicion = "la izquierda";
                    break;
            }
            String alias;

            if (mov.charAt(4) == '1') {
                Partida.hacerMovimiento(mov, partidaReplicar.getJugador1(), true);
                alias = Ficha.ROJO + partidaReplicar.getJugador1().getAlias() + Ficha.RESET;
            }
            else {
                Partida.hacerMovimiento(mov, partidaReplicar.getJugador2(), false);
                alias = Ficha.AZUL + partidaReplicar.getJugador2().getAlias() + Ficha.RESET;
            }
            tablero.actualizar(partidaReplicar.getJugador1(), partidaReplicar.getJugador2());

            tablero.mostrar(true);
            System.out.println();
            System.out.println(alias + " movió la ficha " + mov.charAt(0) + " hacia " + posicion);
            System.out.print("Presione Enter para continuar...");
            scanner.nextLine();
        }

        partidaReplicar.setTablero(tablero);
        partidaReplicar.mostrarGanador(false);
    }

    public void ranking() {
        System.out.println("");
        System.out.println("+---------------------------------------------+");
        System.out.println("|RANKING DE JUGADORES CON MAS PARTIDAS GANADAS|");
        System.out.println("+---------------------------------------------+");
        System.out.println("");
        Collections.sort(jugadores);

        if (jugadores.isEmpty()) {
            System.out.println("Aún no se han registrado jugadores\n");
        } else {


            Jugador jugadorAnterior = jugadores.get(0);

            int posicion = 1;
            for (int i = 0; i < jugadores.size(); i++) {
                if (jugadorAnterior.getpGanadas() == jugadores.get(i).getpGanadas())
                    System.out.println((posicion) + ") " + jugadores.get(i).toString());
                else {
                    posicion = i + 1;
                    System.out.println(posicion + ") " + jugadores.get(i).toString());
                }

                jugadorAnterior = jugadores.get(i);
            }
            System.out.println("");
        }
    }

    public Jugador buscarJugador(String alias){
        for(Jugador jugador : jugadores)
            if(jugador.getAlias().equals(alias))
                return jugador;

        return null;
    }
}
