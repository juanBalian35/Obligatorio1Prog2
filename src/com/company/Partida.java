package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

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

    Partida(Jugador j1, Jugador j2, int forma, Date d){
        jugador1 = j1;
        jugador2 = j2;
        formaDeTerminar = forma;
        fecha = d;
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

    private String pedirMovimiento(Jugador jugador, boolean esJugador1){
        boolean esValido = false;
        while(!esValido){
            String str = Prueba.ingresarString(scanner, "Turno de " + jugador.getAlias() + ": ");

            if(str.equals("VERR") || str.equals("VERN") || str.equals("X")) {
                return str;
            }
            else if (str.length() != 2){
                System.out.println("Debe ingresar un movimiento de dos caracteres");
                continue;
            }

            int n;
            try{
                n = Integer.parseInt(str.charAt(0) + "");
            }
            catch(Exception e){
                //todo cambiar
                System.out.println("Debe ingresar un movimiento valido");
                continue;
            }

            char dir = str.charAt(1);

            if (n >= 1 && n <= Jugador.NUM_FICHAS && (dir == 'A' || dir == 'I' || dir == 'D')){
                String movimiento = str.substring(0,1) + ","+ str.substring(1);
                Ficha ficha = fichaAMover(movimiento, jugador);

                int numFichaAnterior = ultimaFichaMovida(esJugador1);

                int x = ficha.getX() + (dir == 'D' ? 1 : dir == 'I' ? -1 : 0);
                int y = ficha.getY() + (esJugador1 ? -1 : 1);

                if(!tablero.esPosValida(x, y))
                    System.out.println("La posicion a la que se quiere mover no es valida.");
                else if(tablero.estaPosOcupada(x, y))
                    System.out.println("La posicion a la que se quiere mover esta ocupada.");
                else if(n == numFichaAnterior)
                    System.out.println("No puede mover la misma ficha dos veces");
                else
                    return movimiento;
            }
            else{
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

        return Arrays.stream(jugador.getFichas()).
                filter(x -> x.getNumero() == num).
                findFirst().
                get();
    }

    private int ultimaFichaMovida(boolean esJugador1){
        if(!movimientos.isEmpty())
            if(movimientos.get(movimientos.size() - 1).charAt(4) == (esJugador1 ? '1' : '2'))
                return Integer.parseInt(movimientos.get(movimientos.size() - 1).charAt(0) + "");
        return -1;
    }

    public void comenzar(){
        boolean termino = false;
        boolean reducido = true;

        Jugador jugadorActivo = jugador1;

        tablero.actualizar(jugador1, jugador2);

        while(!termino){
            tablero.mostrar(reducido);

            String s = pedirMovimiento(jugadorActivo, jugadorActivo == jugador1);
            switch(s){
                case "X":
                    return;
                case "VERR":
                    reducido = true;
                    break;
                case "VERN":
                    reducido = false;
                    break;
                default:
                    hacerMovimiento(s, jugadorActivo, jugadorActivo == jugador1);

                    movimientos.add(s + "," + (jugadorActivo == jugador1 ? "1" : "2"));

                    tablero.actualizar(jugador1, jugador2);

                    ArrayList<Integer> f = tablero.fichasValidas(fichaAMover(s, jugadorActivo));

                    System.out.println("Fichas validas: ");
                    Arrays.stream(f.toArray())
                            .filter(x -> !x.equals(ultimaFichaMovida(jugadorActivo == jugador1)))
                            .forEach(x -> System.out.print(x + " "));

                    //jugadorActivo = jugadorActivo == jugador1 ? jugador2 : jugador1;
            }
        }
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
}
