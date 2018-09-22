package com.company;


public class Tablero {
    public static final int ANCHO = 9;
    public static final int LARGO = 8;

    Ficha[][] matriz;

    Tablero(){
        matriz = new Ficha[LARGO][ANCHO];
    }

    void actualizar(Jugador j1, Jugador j2){
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
}
