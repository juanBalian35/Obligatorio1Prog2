package com.company;

import java.util.Scanner;

public class Prueba {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sistema sistema = new Sistema();

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
                    Jugador nuevoJugador=new Jugador(nombre,alias,edad);

                    sistema.registrarJugador(nuevoJugador);
                    break;
                case "2":
                    //TODO: validar que existe con ese alias sino seguir preguntando juan ...
                    System.out.println("Ingrese alias del primer jugador: ");
                    String aliasJugador = scanner.nextLine();
                    Jugador j1 = sistema.buscarJugador(aliasJugador);
                    System.out.println("Ingrese alias del segundo jugador: ");
                    aliasJugador = scanner.nextLine();
                    Jugador j2 = sistema.buscarJugador(aliasJugador);

                    sistema.jugar(j1, j2);
                    break;
                case "3":
                    sistema.replicar();
                    break;
                case "4":
                    sistema.ranking();
                    break;
                default:
                    salir = true;
            }
        }
    }
}
