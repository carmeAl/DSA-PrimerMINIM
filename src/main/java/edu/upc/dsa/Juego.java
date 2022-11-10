package edu.upc.dsa;

import java.util.ArrayList;
import java.util.List;

public class Juego {
    String IDjuego;
    String descripcionJuego;
    int numNiveles;
    List<Partida> listaPartidas;
    public Juego(String IDjuego,String descripcionJuego,int numNiveles){
        listaPartidas=new ArrayList<Partida>();
        this.IDjuego=IDjuego;
        this.descripcionJuego=descripcionJuego;
        this.numNiveles=numNiveles;
    }

    public String getIDjuego() {
        return IDjuego;
    }

    public void setIDjuego(String IDjuego) {
        this.IDjuego = IDjuego;
    }

    public String getDescripcionJuego() {
        return descripcionJuego;
    }

    public void setDescripcionJuego(String descripcionJuego) {
        this.descripcionJuego = descripcionJuego;
    }

    public int getNumNivel() {
        return numNiveles;
    }

    public void setNumNivel(int numNiveles) {
        this.numNiveles = numNiveles;
    }

    public List<Partida> getListaPartidas() {
        return listaPartidas;
    }

    public void setListaPartidas(List<Partida> listaPartidas) {
        this.listaPartidas = listaPartidas;
    }
    public void addListaPartidas(Partida partida) {
        this.listaPartidas.add(partida);
    }
}
