package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private Scanner scanner = new Scanner(System.in);

    public void empezar(){
        boolean salir = false;

        while(!salir){
            System.out.println("1 - Registrar jugador");
            System.out.println("2 - Jugar partida");
            System.out.println("3 - Replicar partida");
            System.out.println("4 - Ranking");
            System.out.println("0 - Salir");
            System.out.print("Eleccion: ");

            String op = scanner.nextLine();

            switch(op){
                case "1":
                    //TODO: Validar alias unico, y todo lo demas jaja
                    System.out.println("Ingrese nombre:");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingrese alias:");
                    String alias = scanner.nextLine();
                    System.out.println("Ingrese edad:");
                    int edad = scanner.nextInt();

                    scanner.nextLine();

                    registrarJugador(nombre,alias,edad);
                    break;
                case "2":
                    //TODO: validar que existe con ese alias sino seguir preguntando juan ...
                    System.out.println("Ingrese alias del primer jugador: ");
                    String aliasJugador = scanner.nextLine();
                    Jugador j1 = buscarJugador(aliasJugador);
                    System.out.println("Ingrese alias del segundo jugador: ");
                    aliasJugador = scanner.nextLine();
                    Jugador j2 = buscarJugador(aliasJugador);

                    jugar(j1, j2);
                    break;
                case "3":
                    replicar();
                    break;
                case "4":
                    ranking();
                    break;
                default:
                    salir = true;
            }
        }

    }

    private void registrarJugador(String nombre, String alias, int edad){
        Jugador nuevoJugador = new Jugador(nombre, alias, edad);
        jugadores.add(nuevoJugador);
    }

    private void jugar(Jugador jugador1, Jugador jugador2){
        Partida partida = new Partida(jugador1, jugador2);
        partida.comenzar();
    }

    private void replicar(){
        System.out.println("Replicar");
    }

    private void ranking(){
        System.out.println("Ranking");
    }

    private Jugador buscarJugador(String alias){
        for(Jugador jugador : jugadores)
            if(jugador.getAlias().equals(alias))
                return jugador;

        return null;
    }
}
