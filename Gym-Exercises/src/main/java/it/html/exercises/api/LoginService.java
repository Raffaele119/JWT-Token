package it.html.exercises.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UserCredentials creds) {
	    // Controllo semplice
	    if ("user".equals(creds.getUsername()) && "password".equals(creds.getPassword())) {
	        String username = creds.getUsername();  // prende il nome utente corretto
	        String token = JwtUtil.generateToken(username, "admin"); // o "user" a seconda del ruolo
	        String json = "{\"token\":\"" + token + "\"}";
	        return Response.ok(json, MediaType.APPLICATION_JSON).build();
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED).build();
	    }
	}

}

class UserCredentials {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}