package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private Tablero tablero;
    private Scanner scanner = new Scanner(System.in);

    public void empezar(){

        boolean salir = false;

        while(!salir){
            System.out.println("1 - Registrar jugador");
            System.out.println("2 - Jugar partida");
            System.out.println("3 - Replicar partida");
            System.out.println("4 - Ranking");
            System.out.println("0 - Salir");
            System.out.print("Elecccion: ");

            String op = scanner.nextLine();

            switch(op){
                case "1":

                    System.out.println("Ingrese nombre:");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingrese alias:");
                    String alias = scanner.nextLine();
                    System.out.println("Ingrese edad:");
                    int edad = scanner.nextInt();

                    registrar(nombre,alias,edad);
                    break;
                case "2":

                    break;
                case "3":

                    break;
                case "4":

                    break;


            }
        }

    }

    private void registrar(String nombre, String alias, int edad){
        Jugador nuevoJugador = new Jugador(nombre, alias, edad);
        jugadores.add(nuevoJugador);
    }

    private void jugar(){
        System.out.println("Jugar");
    }

    private void replicar(){
        System.out.println("Replicar");
    }

    private void ranking(){
        System.out.println("Ranking");
    }
}
