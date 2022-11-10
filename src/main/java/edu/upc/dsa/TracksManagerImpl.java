package edu.upc.dsa;

import edu.upc.dsa.models.Track;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

public class TracksManagerImpl implements TracksManager {
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Juego> listaJuegos;

    public TracksManagerImpl(){
        listaUsuarios=new ArrayList<Usuario>();
        listaJuegos=new ArrayList<Juego>();
    }
    @Override
    public void crearJuego(String idJuego, String descrpcion, int numNiveles) {
        Juego juego=new Juego(idJuego,descrpcion,numNiveles);
        listaJuegos.add(juego);
    }

    @Override
    public void iniciarPartida(String idUsuario, String idJuego) throws Exception {
        boolean UsuarioEncontrado=false;
        boolean JuegoEncontrado=false;
        int i=0;
        while((!UsuarioEncontrado)&&(i<listaUsuarios.size())){
            if(Objects.equals(listaUsuarios.get(i).getIdUsuario(), idUsuario)){
                UsuarioEncontrado=true;
            }
            else{
                i++;
            }
        }
        int x=0;
        while((!JuegoEncontrado)&&(x<listaJuegos.size())){
            if(listaJuegos.get(x).getIDjuego()==idJuego){
                JuegoEncontrado=true;
            }
            else{
                x++;
            }
        }
        if((UsuarioEncontrado)&&(JuegoEncontrado)){
            Usuario usuario=listaUsuarios.get(i);
            usuario.setJugando(true);
            usuario.setIdJuegoActual(idJuego);
            usuario.setPuntosActuales(50);
            usuario.setNivelActual(1);
            usuario.addListaJuegos(listaJuegos.get(x));
            listaUsuarios.set(i,usuario);
        }
//falta error

    }

    @Override
    public int consultaNivelActual(String idUsuario) throws Exception { //si no encuentra usuario o usuario no esta en ninguna partida
        //retorna 0
            boolean UsuarioEncontrado=false;
            int i=0;
            while((!UsuarioEncontrado)&&(i<listaUsuarios.size())){
                if(Objects.equals(listaUsuarios.get(i).getIdUsuario(), idUsuario)){
                    UsuarioEncontrado=true;
                }
                else{
                    i++;
                }
            }
            if((UsuarioEncontrado)&& listaUsuarios.get(i).isJugando()) {
                return listaUsuarios.get(i).getNivelActual();
            }
            else{
                return 0;
            }
    }

    @Override
    public void pasarNivel(String idUsuario, int puntosConseguidos, String fecha) {
        boolean UsuarioEncontrado=false;
        int i=0;
        while((!UsuarioEncontrado)&&(i<listaUsuarios.size())){
            if(Objects.equals(listaUsuarios.get(i).getIdUsuario(), idUsuario)){
                UsuarioEncontrado=true;
            }
            else{
                i++;
            }
        }
        if((UsuarioEncontrado)&& listaUsuarios.get(i).isJugando()) {
            int nivel=listaUsuarios.get(i).getNivelActual();
            int puntos=listaUsuarios.get(i).getPuntosActuales()+puntosConseguidos;
            String idJuegoActual=listaUsuarios.get(i).getIdJuegoActual();
            Usuario usuario=listaUsuarios.get(i);
            boolean JuegoEncontrado=false;
            int x=0;
            while((!JuegoEncontrado)&&(x<usuario.getListaJuegos().size())){
                if(Objects.equals(usuario.listaJuegos.get(x).getIDjuego(), idJuegoActual)){
                    JuegoEncontrado=true;
                }
                else{
                    x++;
                }
            }
            if(JuegoEncontrado){

                if(nivel==usuario.listaJuegos.get(x).getNumNivel()){
                    Partida partida=new Partida(idJuegoActual,nivel,puntos+100,fecha);
                    usuario.listaJuegos.get(x).addListaPartidas(partida);
                    usuario.setJugando(false);
                }
                else{
                    Partida partida=new Partida(idJuegoActual,nivel,puntos,fecha);
                    usuario.listaJuegos.get(x).addListaPartidas(partida);
                    usuario.setNivelActual(nivel+1);
                }
                listaUsuarios.set(i,usuario);

            }
        }

    }

    @Override
    public void finalizarPartida(String idUsuario) {
        boolean UsuarioEncontrado=false;
        int i=0;
        while((!UsuarioEncontrado)&&(i<listaUsuarios.size())){
            if(Objects.equals(listaUsuarios.get(i).getIdUsuario(), idUsuario)){
                UsuarioEncontrado=true;
            }
            else{
                i++;
            }
        }
        if((UsuarioEncontrado)&& listaUsuarios.get(i).isJugando()) {
            Usuario usuario=listaUsuarios.get(i);
            usuario.setJugando(false);
            listaUsuarios.set(i,usuario);
        }

    }

    @Override
    public List<Usuario> consultaListaUsuariosJuego(String idJuego) {
        List<Usuario> UsuariosOrdenados=new ArrayList<Usuario>();
        for(int i=0;i<listaUsuarios.size();i++){
            List<Juego> ListJ=listaUsuarios.get(i).getListaJuegos();

            boolean JuegoEncontrado=false;
            int x=0;
            while((!JuegoEncontrado)&&(x<ListJ.size())){
                if(Objects.equals(ListJ.get(x).getIDjuego(), idJuego)){
                    JuegoEncontrado=true;
                }
                else{
                    x++;
                }
            }
            List<Partida> ListP=ListJ.get(x).getListaPartidas();
            int puntos=ListP.get(ListP.size()-1).getPuntos();

            boolean superior=false;
            int y=0;


        }
        return null;

    }

    @Override
    public List<Juego> consultaJuegosUsuario(String idUsuario) {
        return null;
    }

    @Override
    public List<Partida> consultaActividadJuego(String idUsuario, String idJuego) {
        return null;
    }
}