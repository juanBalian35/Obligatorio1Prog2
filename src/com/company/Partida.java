package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Partida {
    private Tablero tablero = new Tablero();
    private Jugador jugador1, jugador2;
    private int formaDeTerminar;
    private ArrayList<String> movimientos = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);


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

    private String pedirMovimiento(String nombreJugador){
        boolean esValido = false;
        while(!esValido){
            String str = Prueba.ingresarString(scanner, "Turno de " + nombreJugador + ": ");

            if(str.equals("VERR") || str.equals("VERN")) {
                return str;
            }
            else if (str.length() != 2){
                System.out.println("Debe ingresar un movimiento de dos caracteres");
            }
            else {
                int n = 0;
                try{
                    n = Integer.parseInt(str.charAt(0) + "");
                }
                catch(Exception e){
                    //todo cambiar
                    System.out.println("Debe ingresar un movimiento valido");
                    continue;
                }


                char c = str.charAt(1);

                if (n > 1 && n < Jugador.NUM_FICHAS && (c == 'A' || c == 'I' || c == 'D') || str.equals("X"))
                    return str.substring(0,1) + ","+ str.substring(1);
                else
                    System.out.println("Debe ingresar un movimiento valido");
            }
        }

        return null;
    }

    private void hacerMovimiento(String mov, Jugador jugador, boolean esJugadorUno){
        String[] a = mov.split(",");
        int num = Integer.parseInt(a[0]);

        Ficha ficha = new Ficha(0,0,0, false);
        for(int i = 0; i < jugador.getFichas().length; ++i){
            Ficha temp = jugador.getFichas()[i];
            if(temp.getNumero() == num)
                ficha = temp;
        }


        ficha.setY(ficha.getY() + (esJugadorUno ? -1 : 1));

        if(a[1].equals("D"))
            ficha.setX(ficha.getX() + 1);
        if(a[1].equals("I"))
            ficha.setX(ficha.getX() - 1);
    }

    //TODO: HAHHJAJAJAJAJ
    public void comenzar(){

        boolean termino = false;
        boolean reducido = true;

        Jugador jugadorActivo = jugador1;

        while(!termino){
            tablero.actualizar(jugador1,jugador2);
            tablero.mostrar(reducido);

            String s = pedirMovimiento(jugadorActivo.getAlias());
            switch(s){
                case "X":
                    break;
                case "VERR":
                    reducido = true;
                    break;
                case "VERN":
                    reducido = false;
                    break;
                default:
                    //ArrayList<Integer> f = tablero.fichasValidas();

                    //for(int i : f){
                    //    System.out.println("Movimiento valido: " + i);
                    //}
                    hacerMovimiento(s, jugadorActivo, jugadorActivo == jugador1);
                    jugadorActivo = jugadorActivo == jugador1 ? jugador2 : jugador1;
            }

        }


    }
}
