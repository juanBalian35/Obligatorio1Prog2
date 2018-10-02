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
    private ArrayList<Integer> numFichasValidas = new ArrayList<>();
    private Date fecha;


    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
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

    public void inicializarFichas(){
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
                if(str.equals("0") && !numFichasValidas.isEmpty())
                    return str;

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

                if(n == numFichaAnterior)
                    System.out.println("No puede mover la misma ficha dos veces");
                else if(!numFichasValidas.isEmpty() && numFichasValidas.indexOf(ficha.getNumero()) == -1)
                    System.out.println("Debe seleccionar una ficha valida");
                else if(!tablero.esPosValida(x, y))
                    System.out.println("La posicion a la que se quiere mover no es valida.");
                else if(tablero.estaPosOcupada(x, y))
                    System.out.println("La posicion a la que se quiere mover esta ocupada.");
                else
                    return movimiento;
            }
            else{
                System.out.println("Debe ingresar un movimiento valido");
            }
        }

        return null;
    }

    public static void hacerMovimiento(String movimiento, Jugador jugador, boolean esJugadorUno){
        String[] a = movimiento.split(",");

        Ficha ficha = fichaAMover(movimiento, jugador);

        ficha.setY(ficha.getY() + (esJugadorUno ? -1 : 1));

        if(a[1].equals("D"))
            ficha.setX(ficha.getX() + 1);
        if(a[1].equals("I"))
            ficha.setX(ficha.getX() - 1);
    }

    private static Ficha fichaAMover(String movimiento, Jugador jugador){
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

    private boolean debeTerminar(boolean esJugadorUno, int cantMaxima){
        switch(formaDeTerminar){
            case 1:
                //Alcanzar cantidad maxima de movimientos
                if(movimientos.size() == cantMaxima){
                    System.out.println("Se ha alcanzado la cantidad máxima de movimientos ("+cantMaxima+")");
                    return true;
                }
                break;
            case 2:
                //Alcanzar con una ficha al otro lado
                if(tablero.unaFichaLadoContrario(esJugadorUno)){
                    System.out.println("Una ficha del lado contrario, terminemos!");
                    return true;
                }
                break;
            case 3:
                //Alcanzar con todas las fichas al otro lado
                if(tablero.todasFichaLadoContrario(esJugadorUno)){
                    System.out.println("todas las fichas del lado contrario, terminemos!");
                    return true;
                }
        }
        return false;
    }

    public Partida comenzar(){
        boolean termino = false;
        boolean reducido = true;
        int cantMaxima = -1;
        Jugador jugadorActivo = jugador1;

        tablero.actualizar(jugador1, jugador2);

        if(formaDeTerminar==1){
            cantMaxima = Prueba.ingresarNatural(scanner,"Ingrese la cantidad maxima de movimientos que desea para la partida:");
        }

        while(!termino){
            tablero.mostrar(reducido);

            String s = pedirMovimiento(jugadorActivo, jugadorActivo == jugador1);
            switch(s) {
                case "0":
                    System.out.println("Cambiando de turno");
                    numFichasValidas.clear();
                    //numFichasValidas: Estamo clear (Estamo clial) (Cancion xD)
                    jugadorActivo = jugadorActivo == jugador1 ? jugador2 : jugador1;
                    break;
                case "X":
                    String opcion = Prueba.ingresarString(scanner, "¿Está seguro que desea abandonar la partida? (SI-NO)");
                    switch (opcion) {
                        case "SI":
                            if (jugadorActivo == jugador1) {
                                System.out.println(jugadorActivo.getAlias() + " ha abandonado la partida, " + jugador2.getAlias() + " es el ganador");
                                jugador2.setpGanadas(jugador2.getpGanadas() + 1);
                            } else {
                                System.out.println(jugadorActivo.getAlias() + " ha abandonado la partida, " + jugador1.getAlias() + " es el ganador");

                                jugador1.setpGanadas(jugador1.getpGanadas() + 1);
                            }

                            return this;
                        case "NO":
                            break;
                    }
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

                    //Terminaaaaa
                    if(debeTerminar(jugadorActivo == jugador1, cantMaxima))
                        break;

                    numFichasValidas = tablero.fichasValidas(fichaAMover(s, jugadorActivo));

                    if (numFichasValidas.contains(Integer.parseInt(s.charAt(0) + "")))
                        numFichasValidas.remove(numFichasValidas.indexOf(Integer.parseInt(s.charAt(0) + "")));

                    if (numFichasValidas.isEmpty()) {
                        System.out.println("No quedan fichas validas, turno de " + (jugador1 == jugadorActivo ? jugador2.getAlias() : jugador1.getAlias()));
                        jugadorActivo = jugadorActivo == jugador1 ? jugador2 : jugador1;
                    } else {
                        System.out.println("Fichas validas: ");
                        for (Integer ficha : numFichasValidas) {
                            System.out.print(ficha + " ");
                        }
                        System.out.println("o terminar turno (ingrese 0)");
                    }
            }
        }

        int a = tablero.contarFichasLadoContrario(true);
        int b = tablero.contarFichasLadoContrario(false);

        if(a > b){
            jugador1.setpGanadas(jugador1.getpGanadas() + 1);
            System.out.println(jugador1.getAlias() + " gano");
        }
        if(a == b){
            System.out.println(jugador1.getAlias() + " y " + jugador2.getAlias() + " empataron");
        }
        else{
            jugador2.setpGanadas(jugador2.getpGanadas() + 1);
            System.out.println(jugador2.getAlias() + " gano");
        }

        return this;
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
