package edu.upc.dsa;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TracksManagerTest {
    TracksManager tm;
    Usuario usuario;

    @Before
    public void setUP(){
        tm=new TracksManagerImpl();

        tm.crearJuego("idJuego1","Este es el primer juego creado y tiene 5 nieles",5);
        tm.crearJuego("idJuego2","Este es el segundo juego creado y tiene 3 nieles",3);

        tm.addUsuario("idUsuario1");
        tm.addUsuario("idUsuario2");
    }

    @After
    public void tearDown(){this.tm=null;}

    @Test
    public void testBefore(){
        //Analizar tamaños
        Assert.assertEquals(2,this.tm.getListaUsuarios().size());
        Assert.assertEquals(2,this.tm.getListaJuegos().size());
        //Analizar IDs
        Assert.assertEquals("idUsuario1",this.tm.getListaUsuarios().get(0).getIdUsuario());
        Assert.assertEquals("idUsuario2",this.tm.getListaUsuarios().get(1).getIdUsuario());
        Assert.assertEquals("idJuego1",this.tm.getListaJuegos().get(0).getIDjuego());
        Assert.assertEquals("idJuego2",this.tm.getListaJuegos().get(1).getIDjuego());
        //Analizar niveles
        Assert.assertEquals(5,this.tm.getListaJuegos().get(0).getNumNivel());
        Assert.assertEquals(3,this.tm.getListaJuegos().get(1).getNumNivel());
    }

    @Test
    public void testIniciarPartida() throws Exception {
        int i=this.tm.iniciarPartida("idUsuario1","idJuego1");
        //Se ha iniciado la partida correctamente
        Assert.assertEquals(0,i);
        //El jugador tiene los 50 puntos iniciales
        Assert.assertEquals(50,this.tm.getListaUsuarios().get(0).puntosActuales);
        //El jugador esta en el nivel 1
        Assert.assertEquals(1,this.tm.getListaUsuarios().get(0).nivelActual);
        //El jugador esta en estado activo
        Assert.assertEquals(true,this.tm.getListaUsuarios().get(0).jugando);

        //No encuentra el usuario y retorna -1
        i=this.tm.iniciarPartida("idUsuario3","idJuego1");
        Assert.assertEquals(-1,i);
        //No encuentra el juego y retorna -2
        i=this.tm.iniciarPartida("idUsuario2","idJuego3");
        Assert.assertEquals(-2,i);
        //El usuario ya esta en una partida y retrona -3
        i=this.tm.iniciarPartida("idUsuario1","idJuego1");
        Assert.assertEquals(-3,i);
    }

    @Test
    public void testConsultaNivelActual() throws Exception {
        this.tm.iniciarPartida("idUsuario1","idJuego1");

        //Consulta nivel actual y retorna 1 porque es en el que esta
        int i=this.tm.consultaNivelActual("idUsuario1");
        Assert.assertEquals(1,i);
        //Buscamos el nivel de un usuario que no existe y retorna -1
        i=this.tm.consultaNivelActual("idUsuario3");
        Assert.assertEquals(-1,i);
        //Buscamos el nivel de un usuario que no está activo y retorna -2
        i=this.tm.consultaNivelActual("idUsuario2");
        Assert.assertEquals(-2,i);
    }

    @Test
    public void testPasarNivel() throws Exception {
        this.tm.iniciarPartida("idUsuario1","idJuego1");

        //Pasamos de nivel 1->2, pasamos correctamente y retorna 0
        int i=this.tm.pasarNivel("idUsuario1",50,"12/11/2022");
        Assert.assertEquals(0,i);
        //Consultamos nivel actual
        i=this.tm.consultaNivelActual("idUsuario1");
        Assert.assertEquals(2,i);
        //Consultamos los puntos que tenemos despues de haber pasado al nivel 2
        //50 puntos iniciales + 50 puntos por el nivel 1(marcados en input)=100
        Assert.assertEquals(100,this.tm.getListaUsuarios().get(0).getListaJuegos().get(0).getListaPartidas().get(0).getPuntos());

        //Pasamos de nivel
        this.tm.pasarNivel("idUsuario1",50,"13/11/2022");
        this.tm.pasarNivel("idUsuario1",50,"14/11/2022");
        //Miramos si se actualiza el nivel bien
        i=this.tm.consultaNivelActual("idUsuario1");
        Assert.assertEquals(4,i);
        //Miramos si se actualiza los puntos bien
        this.tm.pasarNivel("idUsuario1",50,"15/11/2022");
        Assert.assertEquals(250,this.tm.getListaUsuarios().get(0).getListaJuegos().get(0).getListaPartidas().get(this.tm.getListaUsuarios().get(0).getListaJuegos().get(0).getListaPartidas().size()-1).getPuntos());
        //Miramos como actua cuando queremos pasar de nivel y estamos en el utlimo nivel
        //Retorna 1 porque ya estas en el ultimo nivel
        //Se suma 150 puntos = 50 puntos por subir de nivel y 100 por acabar el juego
        //Tota acumulado de 400
        //El jugador pasa en estado inactivo
        i=this.tm.pasarNivel("idUsuario1",50,"13/11/2022");
        Assert.assertEquals(1,i);
        Assert.assertEquals(400,this.tm.getListaUsuarios().get(0).getListaJuegos().get(0).getListaPartidas().get(this.tm.getListaUsuarios().get(0).getListaJuegos().get(0).getListaPartidas().size()-1).getPuntos());
        Assert.assertEquals(false,this.tm.getListaUsuarios().get(0).jugando);

        //El usuario no existe y retorna -1
        i=this.tm.pasarNivel("idUsuario3",50,"13/11/2022");
        Assert.assertEquals(-1,i);
        //El usuario está inactivo y retorna -2
        i=this.tm.pasarNivel("idUsuario2",50,"13/11/2022");
        Assert.assertEquals(-2,i);
    }


}