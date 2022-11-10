package edu.upc.dsa;

public class Partida {
    String idJuego;
    int nivel;
    int puntos;
    String fecha;

    public Partida(String idJuego,int nivel,int puntos, String fecha){
        this.idJuego=idJuego;
        this.nivel=nivel;
        this.puntos=puntos;
        this.fecha=fecha;
    }

    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
