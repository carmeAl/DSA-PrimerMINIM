package edu.upc.dsa;

import edu.upc.dsa.models.Track;

import java.util.List;

public interface TracksManager {
    public void crearJuego(String idJuego,String descrpcion,int numNiveles);
    public void iniciarPartida(String idUsuario, String idJuego) throws Exception;
    public int consultaNivelActual(String idUsuario) throws Exception;
    public void pasarNivel(String idUsuario,int puntosConseguidos,String fecha);
    public void finalizarPartida(String idUsuario);
    public List<Usuario> consultaListaUsuariosJuego(String idJuego);
    public List<Juego> consultaJuegosUsuario(String idUsuario);
    public List<Partida> consultaActividadJuego(String idUsuario, String idJuego);

}
