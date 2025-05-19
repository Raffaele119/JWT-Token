package it.html.exercises.api;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ExercisesApplication extends ResourceConfig {
    public ExercisesApplication() {
        packages("it.html.exercises.api");
        register(JwtFilter.class);  // Usa la classe corretta
        register(LoginService.class);  // Registra il servizio di login
        register(ExerciseServices.class);
    }
}