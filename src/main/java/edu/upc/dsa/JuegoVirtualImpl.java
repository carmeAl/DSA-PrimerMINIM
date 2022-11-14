package edu.upc.dsa;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JuegoVirtualImpl implements JuegoVirtual {
    private static JuegoVirtual instance;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Juego> listaJuegos;



    public JuegoVirtualImpl(){
        listaUsuarios=new ArrayList<>();
        listaJuegos=new ArrayList<>();
    }
    public static JuegoVirtual getInstance() {
        if (instance==null) instance = new JuegoVirtualImpl();
        return instance;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }



    public ArrayList<Juego> getListaJuegos() {
        return listaJuegos;
    }



    @Override
    public void addUsuario(String idUsuario) {
        Usuario usuario=new Usuario(idUsuario);
        listaUsuarios.add(usuario);
    }

    @Override
    public void crearJuego(String idJuego, String descripcion, int numNiveles) {
        Juego juego=new Juego(idJuego, descripcion,numNiveles);
        listaJuegos.add(juego);
    }

    @Override
    public Integer iniciarPartida(String idUsuario, String idJuego) {
        //si se inicia la partida retorna 0
        //si no existe el usuario retorna -1
        //si no existe el juego retorna -2
        //si usuario esta en una partida retorna -3
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
        if((UsuarioEncontrado)&&(JuegoEncontrado)&&(!listaUsuarios.get(i).jugando)){
            Usuario usuario=listaUsuarios.get(i);
            usuario.setJugando(true);
            usuario.setIdJuegoActual(idJuego);
            usuario.setPuntosActuales(50);
            usuario.setNivelActual(1);
            Juego juegoNuevo=new Juego(idJuego,listaJuegos.get(x).getDescripcionJuego(),listaJuegos.get(x).numNiveles);
            usuario.addListaJuegos(juegoNuevo);
            listaUsuarios.set(i,usuario);
            return 0;
        } else if (!UsuarioEncontrado) {
            return -1;
        }
        else if(!JuegoEncontrado){
            return -2;
        }
        else{
            return -3;
        }


    }

    @Override
    public int consultaNivelActual(String idUsuario) {
        //retorna el nivel actual del usuario en la partida
        //si usuario no existe retorna -1
        //si usuario no tiene partida en curso retorna -2
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
            } else if (!UsuarioEncontrado) {
                return -1;
            }
            else{
                return -2;
            }
    }

    @Override
    public Integer pasarNivel(String idUsuario, int puntosConseguidos, String fecha) {
        //si pasa de nivel correctamente retorna 0
        //Si esta en el ultimo nivel retorna 1
        //Si usuario no existe retorna -1
        //Si usuario no tiene partida activa retorna -2
        //Si no se encuentra el juego retorna -3

        //Miramos si el usuario existe
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
                    usuario.setPuntosActuales(puntos);
                    listaUsuarios.set(i,usuario);
                    return 1;
                }
                else{
                    Partida partida=new Partida(idJuegoActual,nivel,puntos,fecha);
                    listaUsuarios.get(i).listaJuegos.get(x).addListaPartidas(partida);
                    usuario.setNivelActual(nivel+1);
                    usuario.setPuntosActuales(puntos);
                    listaUsuarios.set(i,usuario);
                    return 0;
                }


            }
            else{
                return -3;
            }
        }
        else if (!UsuarioEncontrado) {
            return -1;
        }
        else{
            return -2;
        }
    }

    @Override
    public Integer finalizarPartida(String idUsuario) {
        //Si se finaliza correctamente return 0
        //Si usuario no existe retorna -1
        //Si usuario no tiene partida activa retorna -2
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
            return 0;
        }
        else if (!UsuarioEncontrado) {
            return -1;
        }
        else {
            return -2;
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
            List<Usuario> UsuariosOrdenados = new ArrayList<>();
            List<Integer> listaPuntos = new ArrayList<>();
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
                        z++;
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