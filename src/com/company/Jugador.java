package com.company;

public class Jugador implements Comparable<Jugador> {
    public static final int NUM_FICHAS = 8;

    private String alias;
    private String nombre;
    private int edad;
    private int pGanadas;

    private Ficha[] fichas = new Ficha[NUM_FICHAS];

    public Jugador(String nombre, String alias, int edad) {
        this.alias = alias;
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getpGanadas() {
        return pGanadas;
    }

    public void setFichas(Ficha[] fichas){
        this.fichas = fichas;
    }

    public Ficha[] getFichas(){
        return fichas;
    }

    public void setpGanadas(int pGanadas) {
        this.pGanadas = pGanadas;
    }

    @Override
    public int compareTo(Jugador jugador) {
        return jugador.getpGanadas()- this.getpGanadas();
    }

    @Override
    public String toString() {
        return "Alias: " + alias + ", Nombre: " + nombre+ ", Edad:" + edad + ", Partidas ganadas:" + pGanadas;
    }
}
