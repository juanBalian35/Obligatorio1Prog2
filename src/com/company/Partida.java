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

    private Scanner scanner = new Scanner(System.in);

	Partida(Jugador j1, Jugador j2, int forma, Date d){
	    jugador1 = j1;
	    jugador2 = j2;
	    formaDeTerminar = forma;
	    fecha = d;
	    inicializarFichas();
	}
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

    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    public void setTablero(Tablero tablero){this.tablero = tablero; }

    public void setMovimientos(ArrayList<String> movimientos) {
        this.movimientos = movimientos;
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
                    System.out.println("Se ha alcanzado la cantidad máxima de movimientos ("+cantMaxima+"). JUEGO FINALIZADO");
                    return true;
                }

                // En el caso de que se este jugando con una cantidad maxima de movmimientos y un jugador llego con
                // todas sus fichas al otro extremo del tablero, entonces no va a poder seguir jugando, entonces
                // a pesar de seguir habiendo movimientos restantes el juego debe terminar.
            case 3:
                //Alcanzar con todas las fichas al otro lado.
                if(tablero.todasFichaLadoContrario(esJugadorUno)){
                    System.out.println("Todas las fichas han alcanzado el lado contrario del tablero. JUEGO FINALIZADO");
                    return true;
                }
                break;
            case 2:
                //Alcanzar con una ficha al otro lado
                if(tablero.unaFichaLadoContrario(esJugadorUno)){
                    System.out.println("Una ficha ha alcanzado el lado contrario del tablero. JUEGO FINALIZADO");
                    return true;
                }
        }
        return false;
    }

    public Partida comenzar(){
        boolean termino = false;
        boolean verReducido = true;
        int cantMaxima = -1;
        Jugador jugadorActivo = jugador1;

        tablero.actualizar(jugador1, jugador2);

        if(formaDeTerminar == 1){
            do{
                cantMaxima = Prueba.ingresarNatural(scanner, "Ingrese la cantidad maxima de movimientos que desea para la partida (minimo 10):");
                if(cantMaxima < 10)
                    System.out.println("Ingrese un numero de movimientos mayor o igual a 10");
            } while(cantMaxima < 10);
        }

        mostrarInstrucciones();

        while(!termino){
            if(formaDeTerminar == 1)
                System.out.println("Movimientos restantes: " + (cantMaxima - movimientos.size()));

            tablero.mostrar(verReducido);

            String s = pedirMovimiento(jugadorActivo, jugadorActivo == jugador1);
            switch(s) {
                case "0":
                    System.out.println("Cambiando de turno");
                    numFichasValidas.clear();
                    jugadorActivo = jugadorActivo == jugador1 ? jugador2 : jugador1;
                    break;
                case "X":
                    boolean salir=false;
                    while(!salir) {
                        String opcion = Prueba.ingresarString(scanner, "¿Está seguro que desea abandonar la partida? (SI-NO)");

                        switch (opcion) {
                            case "SI":
                                movimientos.add(s + " , " + (jugadorActivo == jugador1 ? "1" : "2"));
                                if (jugadorActivo == jugador1) {
                                    System.out.println(jugadorActivo.getAlias() + " ha abandonado la partida, " + jugador2.getAlias() + " es el ganador");
                                    jugador2.setpGanadas(jugador2.getpGanadas() + 1);
                                } else {
                                    System.out.println(jugadorActivo.getAlias() + " ha abandonado la partida, " + jugador1.getAlias() + " es el ganador");

                                    jugador1.setpGanadas(jugador1.getpGanadas() + 1);
                                }

                                return this;
                            case "NO":
                                salir=true;
                                break;
                            default:
                                System.out.println("Ingrese una opción valida");
                        }
                    }
                case "VERR":
                    verReducido = true;
                    break;
                case "VERN":
                    verReducido = false;
                    break;
                default:
                    hacerMovimiento(s, jugadorActivo, jugadorActivo == jugador1);

                    movimientos.add(s + "," + (jugadorActivo == jugador1 ? "1" : "2"));

                    tablero.actualizar(jugador1, jugador2);

                    if(debeTerminar(jugadorActivo == jugador1, cantMaxima)){
                        termino = true;
                        break;
                    }

                    numFichasValidas = tablero.fichasValidas(fichaAMover(s, jugadorActivo));

                    if (numFichasValidas.contains(Integer.parseInt(s.charAt(0) + "")))
                        numFichasValidas.remove(numFichasValidas.indexOf(Integer.parseInt(s.charAt(0) + "")));

                    if (numFichasValidas.isEmpty()) {
                        System.out.println("No quedan fichas validas, turno de " + (jugador1 == jugadorActivo ? jugador2.getAlias() : jugador1.getAlias()));
                        jugadorActivo = jugadorActivo == jugador1 ? jugador2 : jugador1;
                    }
                    else {
                        System.out.println("Fichas validas: ");
                        for (Integer ficha : numFichasValidas) {
                            System.out.print(ficha + " ");
                        }
                        System.out.println("o terminar turno (ingrese 0)");
                    }
            }
        }

        mostrarGanador(true);

        return this;
    }

    public void mostrarGanador(boolean guardarInformacion){
        int a = tablero.contarFichasLadoContrario(true);
        int b = tablero.contarFichasLadoContrario(false);
        System.out.println("+-----------------------------------------+");
        System.out.println("| Suma de las fichas en el lado contrario |" );
        System.out.println("+-----------------------------------------+");

        System.out.println(Ficha.ROJO+  jugador1.getAlias() + Ficha.RESET+  ": " +  a);
        System.out.println(Ficha.AZUL+ jugador2.getAlias()+ Ficha.RESET + ": " +  b);

        if(a > b){
            if(guardarInformacion)
                jugador1.setpGanadas(jugador1.getpGanadas() + 1);

            System.out.println(Ficha.ROJO+  jugador1.getAlias() + Ficha.RESET + " ganó la partida" );
        }
        else if(a == b){
            if("X".equals(movimientos.get(movimientos.size() - 1).charAt(0) + "")) {
                if("1".equals(movimientos.get(movimientos.size() - 1).charAt(4) + ""))
                    System.out.println(jugador1.getAlias() + " ha abandonado la partida, " + jugador2.getAlias() + " es el ganador");
                else
                    System.out.println(jugador2.getAlias() + " ha abandonado la partida, " + jugador1.getAlias() + " es el ganador");
            }
            else{
                System.out.println(Ficha.ROJO+ jugador1.getAlias() + Ficha.RESET + " y " + Ficha.AZUL + jugador2.getAlias() + Ficha.RESET + " han empatado");
            }
        }
        else{
            if(guardarInformacion)
                jugador2.setpGanadas(jugador2.getpGanadas() + 1);

            System.out.println(Ficha.AZUL + jugador2.getAlias() + Ficha.RESET + " ganó la partida");
        }
    }

    private void mostrarInstrucciones(){
        System.out.println("+--------------------------------+");
        System.out.println("|         INSTRUCCIONES          |");
        System.out.println("+--------------------------------+");
        System.out.println("| Para mover la ficha indique    |");
        System.out.println("| número de ficha y la dirección |");
        System.out.println("| en cuál quiere moverla,        |");
        System.out.println("| ej: 1A, 3D, 7I                 |");
        System.out.println("+--------------------------------+");
        System.out.println("| 0 - Pasar de turno             |");
        System.out.println("| X - Abandonar partida          |");
        System.out.println("| VERR - Ver tablero de forma    |");
        System.out.println("|        reducida                |");
        System.out.println("| VERN - Ver tablero de forma    |");
        System.out.println("|        normal                  |");
        System.out.println("+--------------------------------+");
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
