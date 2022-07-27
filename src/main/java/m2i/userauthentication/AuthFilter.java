package m2i.userauthentication;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
    
    @Context
    public  HttpServletRequest request;
    
   
     
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        /* TODO */
        // Récuperer le header Authorization   
        String authorization = requestContext.getHeaderString("Authorization");
        System.out.println("authorizationHeader" + authorization);
        
        if(authorization== null){
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity("you must be connected").build());
        }

        // Décoder la vale ur du header Authorization pour récuperer l'email et le mot de passe
        // email:password
        String[] decodedAuth = BasicAuth.decode(authorization);
        
        
        if(decodedAuth== null || decodedAuth.length !=2){
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity("You must be connect").build());
        }

        String email = decodedAuth[0]; // email
        String password = decodedAuth[1]; // password

        System.out.println("email" + email);
        System.out.println("password " + password);

        User uu = checkUser(email, password);

        if (uu == null) {
            // throw new IOException("  pas ");}
            throw new WebApplicationException(Response.status(401).entity("You must be connected").build());
        }
        /* ---- */

        // Implémenter la méthode checkUser qui vérifie les identifiants
        // Mettre l'utilisateur en attribut de la requete
    }

    public User checkUser(String email, String password) {
        Dao d = new Dao();
        List<User> users = d.findAll();
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                request.setAttribute("user", u);
                return u;
            }

        }
        return null;

    }

}
