package edu.upc.dsa;

import edu.upc.dsa.models.Track;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import io.swagger.models.auth.In;
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
            if(Objects.equals(listaJuegos.get(x).getIDjuego(), idJuego)){
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
        boolean juegoEncontrado=false;
        int k=0;
        while((!juegoEncontrado)&&(k<listaJuegos.size())){
            if(Objects.equals(listaJuegos.get(k).getIDjuego(), idJuego)){
                juegoEncontrado=true;
            }
            else{
                k++;
            }
        }
        if(juegoEncontrado) {
            List<Usuario> UsuariosOrdenados = new ArrayList<Usuario>();
            List<Integer> listaPuntos = new ArrayList<Integer>();
            int y = 0;
            boolean jugadorAnadido = false;
            while ((y < listaUsuarios.size()) && (!jugadorAnadido)) {
                List<Juego> ListJ = listaUsuarios.get(y).getListaJuegos();

                boolean JuegoEncontrado = false;
                int x = 0;
                while ((!JuegoEncontrado) && (x < ListJ.size())) {
                    if (Objects.equals(ListJ.get(x).getIDjuego(), idJuego)) {
                        JuegoEncontrado = true;
                    } else {
                        x++;
                    }
                }
                if (JuegoEncontrado) {
                    UsuariosOrdenados.add(listaUsuarios.get(y));
                    List<Partida> ListP = ListJ.get(x).getListaPartidas();
                    int puntos = ListP.get(ListP.size() - 1).getPuntos();
                    listaPuntos.add(puntos);
                    jugadorAnadido = true;
                } else {
                    y++;
                }
            }
            for (int i = y + 1; i < listaUsuarios.size(); i++) {
                List<Juego> ListJ = listaUsuarios.get(i).getListaJuegos();

                boolean JuegoEncontrado = false;
                int x = 0;
                while ((!JuegoEncontrado) && (x < ListJ.size())) {
                    if (Objects.equals(ListJ.get(x).getIDjuego(), idJuego)) {
                        JuegoEncontrado = true;
                    } else {
                        x++;
                    }
                }
                List<Partida> ListP = ListJ.get(x).getListaPartidas();
                int puntos = ListP.get(ListP.size() - 1).getPuntos();

                boolean inferior = false;
                boolean puesto = false;
                int z = 0;
                while ((!inferior) && (z < listaPuntos.size())) {
                    if (listaPuntos.get(z) > puntos) {
                        listaPuntos.add(z, puntos);
                        UsuariosOrdenados.add(z, listaUsuarios.get(y));
                        inferior = true;
                        puesto = true;
                    } else {
                        y++;
                    }
                }
                if (!puesto) {
                    listaPuntos.add(z, puntos);
                    UsuariosOrdenados.add(z, listaUsuarios.get(y));
                }
            }
            return UsuariosOrdenados;
        }
        else{
            return null;
        }

    }

    @Override
    public List<Juego> consultaJuegosUsuario(String idUsuario) {
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
        if(UsuarioEncontrado){
            return listaUsuarios.get(i).getListaJuegos();
        }
        return null;
    }

    @Override
    public List<Partida> consultaActividadJuego(String idUsuario, String idJuego) {
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
        if(UsuarioEncontrado){

            int y=0;
            while(y<listaUsuarios.get(i).getListaJuegos().size()){
                if(Objects.equals(listaUsuarios.get(i).getListaJuegos().get(y).getIDjuego(), idJuego)){

                    return listaUsuarios.get(i).getListaJuegos().get(y).getListaPartidas();
                }
                else{
                    y++;
                }
            }
        }
        return null;
    }
}