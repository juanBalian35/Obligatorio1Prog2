package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private Tablero tablero;
    private Scanner scanner = new Scanner(System.in);

    public void empezar(){
        String opcion = "";

        while(!opcion.equals("0")){
            System.out.println("1 - Registrar jugador");
            System.out.println("2 - Jugar partida");
            System.out.println("3 - Replicar partida");
            System.out.println("4 - Ranking");
            System.out.println("0 - Salir");
            System.out.print("Elecccion: ");

            opcion = scanner.next();

            if(opcion.equals("1"))
                registrar();
            else if(opcion.equals("2"))
                jugar();
            else if(opcion.equals("3"))
                replicar();
            else if(opcion.equals("4"))
                ranking();
        }

    }

    private void registrar(){
        System.out.println("Registrar");
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
