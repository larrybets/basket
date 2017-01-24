package model;

import java.time.LocalDate;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

/**
 * Created by DAM on 13/1/17.
 */
public class Jugador {

    private String nombre;
    private LocalDate nacimiento;
    private int canastas;
    private String posicion;
    private int asistencias;
    private int rebotes;

   private Equipo equipo;

    public Jugador() {
    }

    public Jugador(String nombre, LocalDate nacimiento, int canastas, String posicion, int asistencias,int rebotes,Equipo equipo) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.canastas = canastas;
        this.posicion = posicion;
        this.asistencias = asistencias;
        this.rebotes = rebotes;
        this.equipo = equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getCanastas() {
        return canastas;
    }

    public void setCanastas(int canastas) {
        this.canastas = canastas;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getRebotes() {
        return rebotes;
    }

    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", nacimiento=" + nacimiento +
                ", canastas=" + canastas +
                ", posicion='" + posicion + '\'' +
                ", asistencias=" + asistencias +
                ", rebotes=" + rebotes +
                ", equipo=" + equipo.getNombre() +
                '}';
    }
}
