package com.company;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Prueba {
    public static String ingresarString(Scanner scanner, String mensaje) {
        String str;
        boolean esValido;
        do {
            System.out.println(mensaje);
            str = scanner.nextLine();

            esValido = !str.isEmpty() && (str.trim().length() > 0);
            if (!esValido)
                System.out.println("Debe ingresar una cadena de texto valida.");
        } while (!esValido);

        return str;
    }

    public static int ingresarNatural(Scanner scanner, String mensaje) {
        int natural = -1;
        boolean esNatural;
        do {
            System.out.println(mensaje);
            String naturalStr = scanner.nextLine();
            try {
                natural = Integer.parseInt(naturalStr);
                esNatural = natural >= 0;
            } catch (Exception e) {
                esNatural = false;
            }

            if (!esNatural)
                System.out.println("Debe ingresar un numero natural.");
        } while (!esNatural);

        return natural;
    }

    public static boolean estaEnRango(int n, int min, int max) {
        return n >= min && n <= max;
    }

    public static int ingresarEnteroEnRango(Scanner scanner, int min, int max, String mensaje) {
        int natural;
        boolean estaEnRango = false;
        do {
            natural = ingresarNatural(scanner, mensaje);
            estaEnRango = estaEnRango(natural, min, max);
            if (!estaEnRango) {
                if (min == max) {
                    System.out.println("Solo puede ingresar " + min);
                } else {
                    System.out.println("Debe ingresar un numero natural entre " + min + " y " + max);
                }
            }
        } while (!estaEnRango);

        return natural;
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        Sistema sistema = new Sistema();

        boolean salir = false;

        while (!salir) {
            System.out.println("1 - Registrar jugador");
            System.out.println("2 - Jugar partida");
            System.out.println("3 - Replicar partida");
            System.out.println("4 - Ranking");
            System.out.println("0 - Salir");
            System.out.print("Eleccion: ");

            String op = scanner.nextLine();

            switch (op) {
                case "1":
                    String nombre = ingresarString(scanner, "Ingrese nombre del jugador:");
                    String alias;
                    do {
                        alias = ingresarString(scanner, "Ingrese alias del jugador:");
                        if (sistema.buscarJugador(alias) != null) {
                            System.out.println("Ya existe un jugador con el mismo alias");
                        }
                    } while (sistema.buscarJugador(alias) != null);

                    int edad = ingresarEnteroEnRango(scanner, 6, 120, "Ingrese edad del jugador (6-120 años)");

                    scanner.nextLine();
                    Jugador nuevoJugador = new Jugador(nombre, alias, edad);

                    sistema.registrarJugador(nuevoJugador);
                    break;
                case "2":
                    //TODO: validar que existe con ese alias sino seguir preguntando juan ...
                    /*String aliasJugador;
                    do {
                        aliasJugador = ingresarString(scanner, "Ingrese alias del primer jugador:");
                        if (sistema.buscarJugador(aliasJugador) == null) {
                            System.out.println("No existe un jugador con ese alias, intente de nuevo");
                        }
                    } while (sistema.buscarJugador(aliasJugador) == null);

                    Jugador j1 = sistema.buscarJugador(aliasJugador);

                    do {
                        aliasJugador = ingresarString(scanner, "Ingrese alias del segundo jugador:");
                        if (sistema.buscarJugador(aliasJugador) == null) {
                            System.out.println("No existe un jugador con ese alias, intente de nuevo");
                        }
                        if(aliasJugador.equals(j1.getAlias())){
                            System.out.println("El segundo jugador no puede ser igual al primero");
                        }
                    } while ((sistema.buscarJugador(aliasJugador) == null)||(aliasJugador.equals(j1.getAlias())));

                    Jugador j2 = sistema.buscarJugador(aliasJugador);

                        System.out.println("Seleccione la forma de terminar la partida:");
                        System.out.println("1 - Alcanzar una cantidad maxima de movimientos");
                        System.out.println("2 - Alcanzar con UNA ficha el lado opuesto del tablero");
                        System.out.println("3 - Alcanzar con TODAS las fichas el lado opuesto del tablero");

                        int opcion = ingresarEnteroEnRango(scanner,1,3,"Eleccion: ");*/


                    Jugador j1 = new Jugador("baba","1",15);
                    Jugador j2 = new Jugador("asd","2",15);
                    int opcion = 2;
                    Date fecha = GregorianCalendar.getInstance().getTime();
                    sistema.jugar(j1, j2, opcion,fecha);

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

    public static void main(String[] args) {
        menu();
    }
}