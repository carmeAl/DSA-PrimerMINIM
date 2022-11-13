package edu.upc.dsa.services;


import edu.upc.dsa.Juego;
import edu.upc.dsa.JuegoVirtual;
import edu.upc.dsa.JuegoVirtualImpl;
import edu.upc.dsa.models.Track;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/JuegoVirtual", description = "Motor juego virtual DSA UPC")
@Path("/JuegoVirtual")
public class JuegoVirtualService {

    private JuegoVirtual tm;
    public JuegoVirtualService(){
        this.tm= new JuegoVirtualImpl();
        if(tm.getListaJuegos().size()==0){
            this.tm.crearJuego("idJuego1", "Este es el primer juego creado y tiene 5 nieles", 5);
            this.tm.crearJuego("idJuego2", "Este es el segundo juego creado y tiene 3 nieles", 3);

            this.tm.addUsuario("idUsuario1");
            this.tm.addUsuario("idUsuario2");

        }
    }

    @GET
    @ApiOperation(value = "devuelve todos los juegos", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = JuegoVirtual.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJuegos() {

        List<Juego> listJ = this.tm.getListaJuegos();

        GenericEntity<List<Juego>> entity = new GenericEntity<List<Juego>>(listJ) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @POST
    @ApiOperation(value = "crear un juego nuevo", notes = "introducir como parametros id del juego, descripcion y el numero de niveles que tendra el juego")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Juego.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response JuegoNuevo(Juego juego) {


        if (juego.getIDjuego()==null || juego.getDescripcionJuego()==null || juego.getNumNivel()==0)  return Response.status(500).entity(juego).build();
        this.tm.crearJuego(juego.getIDjuego(),juego.getDescripcionJuego(), juego.getNumNivel());
        return Response.status(201).entity(juego).build();
    }


}