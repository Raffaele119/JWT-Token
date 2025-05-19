package it.html.exercises.api;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.*;

@Path("exercises")
public class ExerciseServices {

    private static List<Exercise> exercises = new ArrayList<>();//lista statica che contiene gli esercizi
    private long maxId = 0;//contatore per tenere traccia dell'ultimo id usato per gli esercizi

    @PostConstruct
    public void caricaDati() {
        if (!exercises.isEmpty()) {
            return; //evita di ricaricare i dati dal db se già caricati
        }

        //carica i dati una sola volta dal database 
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM esercizi")) {

            while (rs.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(rs.getLong("id"));
                exercise.setName(rs.getString("nome"));
                exercise.setDescription(rs.getString("descrizione"));
                exercise.setMuscleGroup(rs.getString("gruppo_muscolare"));
                exercises.add(exercise);

                //aggiorna la variabile maxId con l'ID più grande trovato
                if (exercise.getId() > maxId) {
                    maxId = exercise.getId();
                }
           }
        } catch (SQLException e) {
            e.printStackTrace(); //se si è verificato un problma stampa l'errore
        }
    }

    @GET
    public List<Exercise> list() {
        return new ArrayList<>(exercises); //restituisce una copia della lista di esercizi
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") long id) {
        //cerca un esercizio tramite ID utilizzando un for
        for (Exercise exercise : exercises) {
            if (exercise.getId() == id) {
                return Response.ok(exercise).build(); //restituisce l'esercizio se trovato
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build(); //se non viene trovato restituisce errore 404
    }


    @POST
    public Response add(Exercise exercise) {
        //nuovo ID (incrementando maxId) impostando l'ID sull'esercizio
        maxId++;
        exercise.setId(maxId);

        //aggiunge l'esercizio alla lista
        exercises.add(exercise);

        //esercizio aggiunto
        return Response.status(Response.Status.CREATED).entity(exercise).build();
    }


    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") long id, Exercise updatedExercise) {
        //cerca l'esercizio con l'ID dato e lo aggiorna se trovato
        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).getId() == id) {
                updatedExercise.setId(id); //mantiene l'ID
                exercises.set(i, updatedExercise); //aggiorna l'esercizio nella lista
                return Response.noContent().build(); //restituisce 204 (NO CONTENT) se aggiornato con successo
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // restituisce 404 se l'esercizio non viene trovato
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        //rimuove l'esercizio con l'ID fornito
        boolean removed = exercises.removeIf(e -> e.getId() == id);
        if (removed) {
            return Response.noContent().build(); //se l'esercizio è stato rimosso con successo restituisce 204
        } else {
            return Response.status(Response.Status.NOT_FOUND).build(); //se non viene trovato restituisce errore 404
        }
    }
}
