package com.company;


import java.util.ArrayList;

public class Tablero {
    public static final int ANCHO = 9;
    public static final int LARGO = 8;

    Ficha[][] matriz;

    Tablero(){
        matriz = new Ficha[LARGO][ANCHO];
    }

    void actualizar(Jugador j1, Jugador j2){
        for(int i = 0; i < matriz.length; ++i)
            for(int j = 0; j < matriz[i].length; ++j)
                matriz[i][j] = null;

        for(Ficha ficha : j1.getFichas())
            matriz[ficha.getY()][ficha.getX()] = ficha;

        for(Ficha ficha : j2.getFichas())
            matriz[ficha.getY()][ficha.getX()] = ficha;
    }

    private String lineaSeparatoria(){
        String s = "";

        for(int j = 0; j < ANCHO*2+1; ++j)
            s += ((j % 2 == 0 ? "+" : "-"));

        return s;
    }

    public void mostrar(boolean reducido){
        String out = "";

        System.out.println(reducido ? "" : lineaSeparatoria());
        for(int i = 0; i < LARGO; ++i){
            for(int j = 0; j < ANCHO; ++j){
                System.out.print(reducido ? " " : "|");
                System.out.print((matriz[i][j] == null ? (reducido ? "-" : " ") : matriz[i][j]));
                System.out.print(reducido ? " " : (j == ANCHO - 1 ? "|" : ""));
            }

            System.out.println(reducido ? "" : "\n" + lineaSeparatoria());
        }
    }

    ArrayList<Integer> fichasValidas(Ficha ficha){
        ArrayList<Integer> movValidos = new ArrayList<>();

        int[] s1 = sumaDiagonales(ficha);
        int s2 = sumaHorizontal(ficha);
        int s3 = sumaVertical(ficha);

        if(s1[0] < 8)
            movValidos.add(s1[0]);
        if(s1[1] < 8)
            movValidos.add(s1[1]);
        if(s2 < 8)
            movValidos.add(s2);
        if(s3 < 8)
            movValidos.add(s3);

        /*
        for(int i = 0; i < movValidos.size(); ++i){
            for(int j = i+1; j < movValidos.size(); ++j){
                if(movValidos.get(i) == movValidos.get(j)){
                    movValidos.remove(j);
                    i--;
                    j--;
                }
            }
        }*/

        return movValidos;
    }

    private int[] sumaDiagonales(Ficha ficha){
        int x = ficha.getX(), y = ficha.getY();
        int suma1 = 0, suma2 = 0;

        for(int i = 0; i < Tablero.ANCHO; ++i){
            int xAtras = x - i, xAdelante = x + i;

            int yAtras = y - i, yAdelante = y + i;

            //System.out.println("xAtras: " + xAtras + " xAdelante " + xAdelante + " yAtras " + yAtras + " yAdelante "+ yAdelante);

            if(esPosValida(xAtras, yAtras))
                suma1 += matriz[yAtras][xAtras] == null ? 0 : matriz[yAtras][xAtras].getNumero();
            if(esPosValida(xAdelante, yAdelante))
                suma1 += matriz[yAdelante][xAdelante] == null ? 0 : matriz[yAdelante][xAdelante].getNumero();

            if(esPosValida(xAtras, yAdelante))
                suma2 += matriz[yAdelante][xAtras] == null ? 0 : matriz[yAdelante][xAtras].getNumero();
            if(esPosValida(xAdelante, yAtras))
                suma2 += matriz[yAtras][xAdelante] == null ? 0 : matriz[yAtras][xAdelante].getNumero();
        }

        int[] ret = {suma1, suma2};
        return ret;
    }

    private int sumaHorizontal(Ficha ficha){
        int y = ficha.getY();

        int suma = 0;

        for(int i = 0; i < Tablero.ANCHO; ++i)
            if(esPosValida(i, y))
                suma += matriz[y][i] == null ? 0 : matriz[y][i].getNumero();

        return suma;
    }

    private int sumaVertical(Ficha ficha){
        int x = ficha.getX();

        int suma = 0;

        for(int i = 0; i < Tablero.LARGO; ++i)
            if(esPosValida(x, i))
                suma += matriz[i][x] == null ? 0 : matriz[i][x].getNumero();

        return suma;
    }

    private boolean esPosValida(int x, int y){
        return x >= 0 && x < Tablero.ANCHO && y >= 0 && y < Tablero.LARGO;
    }
}
