package it.html.exercises.api;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class Authorizer implements SecurityContext {

	 private final String username;

	    public Authorizer(String username) {
	        this.username = username;
	    }

	    @Override
	    public Principal getUserPrincipal() {
	        return () -> username;
	    }

	    @Override
	    public boolean isUserInRole(String role) {
	        return true; 
	    }

	    @Override
	    public boolean isSecure() {
	        return false;
	    }

	    @Override
	    public String getAuthenticationScheme() {
	        return "Bearer";
	    }
	}
