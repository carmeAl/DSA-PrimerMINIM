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
    private void testBefore(){
        //Analizar tamaños
        Assert.assertEquals(2,this.tm.getListaUsuarios().size());
        Assert.assertEquals(2,this.tm.getListaJuegos().size());
        //Analizar una id
        //Analizar un nivel
    }


}