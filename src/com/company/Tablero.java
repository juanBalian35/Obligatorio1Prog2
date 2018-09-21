package com.company;

public class Tablero {
    private static int ANCHO = 8;
    private static int LARGO = 9;

    int[][] matriz;

    Tablero(){
        matriz = new int[ANCHO][LARGO];
    }

    private String lineaSeparatoria(){
        String s = "";

        for(int j = 0; j < ANCHO*2+1; ++j)
            s += ((j % 2 == 0 ? "+" : "-") + (j == ANCHO*2 ? "\n" : ""));

        return s;
    }

    @Override
    public String toString(){
        String out = "";
        out += lineaSeparatoria();
        for(int i = 0; i < LARGO; ++i){
            for(int j = 0; j < ANCHO; ++j)
                out += ("|" +  (matriz[j][i] == 0 ? " " : matriz[j][i]) + (j == ANCHO - 1 ? "|\n" : ""));

            out += lineaSeparatoria();
        }

        return out;
    }
}
