package edu.upc.dsa;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    String idUsuario;
    List<Juego> listaJuegos;
    boolean jugando;
    String idJuegoActual;
    int puntosActuales;
    int nivelActual;

    public int getPuntosActuales() {
        return puntosActuales;
    }

    public void setPuntosActuales(int puntos) {
        this.puntosActuales = puntos;
    }

    public String getIdJuegoActual() {
        return idJuegoActual;
    }

    public void setIdJuegoActual(String idJuegoActual) {
        this.idJuegoActual = idJuegoActual;
    }



    public Usuario(String idUsuario){
        listaJuegos=new ArrayList<Juego>();
        this.idUsuario=idUsuario;
        jugando=false;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Juego> getListaJuegos() {
        return listaJuegos;
    }

    public void setListaJuegos(List<Juego> listaJuegos) {
        this.listaJuegos = listaJuegos;
    }
    public void addListaJuegos(Juego juego) {
        this.listaJuegos.add(juego);
    }

    public boolean isJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }


}
