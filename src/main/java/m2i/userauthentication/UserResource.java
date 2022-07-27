/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m2i.userauthentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



/**
 *
 * @author elouf
 */


 @Path("users")
public class UserResource {
     
    @Context
    private HttpServletRequest request;
        
       /* 
    @GET()
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsers() {
        Dao dao = new Dao();
        return dao.findAll();
    }
    */
    
    
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)   
    public Response recuperer( ) {
     
       Dao d = new Dao();
         List<User> users = d.findAll(); 
         if(users==null){
               Response.status(Response.Status.UNAUTHORIZED).entity( "erreur 401 Liste vide").build();
         }
         
      return Response.status(Response.Status.OK).entity(users).build();}
    
    
    
    
   @POST()
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postUser(User newUtilisateur) {
        Dao dao = new Dao();

        try {
            dao.create(newUtilisateur);
        } catch (BadRequestException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("An error occured")
                            .build()
            );
        }

        return Response.status(Response.Status.CREATED)
                .entity("User successfully created")
                .build();
    }
    
       /* 
    @GET
    public Response verification( ) {
     
        User u = (User) request.getAttribute("user");
        
        
        if(u==null){
           return  Response.status(Response.Status.UNAUTHORIZED).entity( "Utilisateur n'est pas connecté").build();
        }
      
      
       return Response.status(Response.Status.OK).entity(u).build();
    
     }
    */
   @Path("/{id}")
    @PUT()
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putUser(@PathParam("id") int id, User utilisateur) {
        Dao dao = new Dao();

        try {
            dao.update(id, utilisateur);

        } catch (NotFoundException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("User was not found")
                            .build()
            );
        } catch (Exception e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("An error occured")
                            .build()
            );
        }

        return Response.status(Response.Status.OK).entity("User successfully modified").build();
    }

    @Path("/{id}")
    @DELETE()
    public Response deleteUser(@PathParam("id") int userId) {
        Dao dao = new Dao();

        try {
            dao.delete(userId);
        } catch (NotFoundException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("User was not found")
                            .build()
            );
        } catch (Exception e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("An error occured")
                            .build()
            );
        }
        
        return Response.status(Response.Status.OK).entity("User successfully deleted").build();
    }

    @Path("/{id}")
    @GET()
    @Produces({MediaType.APPLICATION_JSON})
    public User getUser(@PathParam("id") int userId, @Context HttpServletRequest request) {

        Dao dao = new Dao();
        User user = dao.findById(userId);

        if (user == null) {
            throw new WebApplicationException("User was not found", Response.Status.NOT_FOUND);
        }

        return user;
    }

    @Path("/search")
    @GET()
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> searchUser(@QueryParam("q") String query, @QueryParam("count") Integer count) {
        
        if (query == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("Parameter 'q' is mandatory")
                            .build()
            );
        }

        if (count == null) {
            count = 1;
        }
        
       Dao dao = new Dao();
        List<User> searchResult = dao.search(query, count);

        if (searchResult == null || searchResult.isEmpty()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity("No user found")
                            .build()
            );
        }

        return searchResult;
    }
    
}