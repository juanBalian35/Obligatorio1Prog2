package com.company;


import java.util.*;

public class Partida implements Comparable<Partida> {
    private Tablero tablero = new Tablero();
    private Jugador jugador1, jugador2;
    private int formaDeTerminar;
    private ArrayList<String> movimientos = new ArrayList<>();
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    private Scanner scanner = new Scanner(System.in);


    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(ArrayList<String> movimientos) {
        this.movimientos = movimientos;
    }

    Partida(Jugador j1, Jugador j2, int forma, Date f){
        jugador1 = j1;
        jugador2 = j2;
        formaDeTerminar = forma;
        fecha= f;
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

                if (n >= 1 && n < Jugador.NUM_FICHAS && (c == 'A' || c == 'I' || c == 'D') || str.equals("X"))
                    return str.substring(0,1) + ","+ str.substring(1);
                else
                    System.out.println("Debe ingresar un movimiento valido");
            }

        }

        return null;
    }

    private void hacerMovimiento(String movimiento, Jugador jugador, boolean esJugadorUno){
        String[] a = movimiento.split(",");

        Ficha ficha = fichaAMover(movimiento, jugador);

        ficha.setY(ficha.getY() + (esJugadorUno ? -1 : 1));

        if(a[1].equals("D"))
            ficha.setX(ficha.getX() + 1);
        if(a[1].equals("I"))
            ficha.setX(ficha.getX() - 1);
    }

    private Ficha fichaAMover(String movimiento, Jugador jugador){
        String[] a = movimiento.split(",");
        int num = Integer.parseInt(a[0]);

        Ficha ficha = new Ficha(0,0,0, false);
        for(int i = 0; i < jugador.getFichas().length; ++i){
            Ficha temp = jugador.getFichas()[i];
            if(temp.getNumero() == num)
                return temp;
        }

        return null;
    }

@Override
    public int compareTo(Partida partida){
        return partida.getFecha().compareTo(getFecha());
    }

    @Override
    public String toString() {
        return jugador1.getAlias() +
                " vs " + jugador2.getAlias() +
                ", fecha=" + fecha;
    }

    //TODO: HAHHJAJAJAJAJ
    public void comenzar(){

        boolean termino = false;
        boolean reducido = true;

        Jugador jugadorActivo = jugador1;

        tablero.actualizar(jugador1, jugador2);

        while(!termino){
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

                    hacerMovimiento(s, jugadorActivo, jugadorActivo == jugador1);
                    movimientos.add(s);
                    tablero.actualizar(jugador1, jugador2);

                    ArrayList<Integer> f = tablero.fichasValidas(fichaAMover(s, jugadorActivo));

                    for(int i : f){
                        System.out.println("Movimiento valido: " + i);
                    }
                    //jugadorActivo = jugadorActivo == jugador1 ? jugador2 : jugador1;
            }

        }



    }
}
