package com.company;

public class Jugador {
    private String alias;
    private String nombre;
    private int edad;
    private int pGanadas;

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

    public void setpGanadas(int pGanadas) {
        this.pGanadas = pGanadas;
    }
}
