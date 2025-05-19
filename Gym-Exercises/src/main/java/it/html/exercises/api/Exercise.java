package it.html.exercises.api;

//import java.util.List;

public class Exercise {

    private long id;                // id dell'esercizio
    private String name;            // nome dell'esercizio
    private String description;     // descrizione dell'esercizio
    private String muscleGroup;     // gruppo muscolare
    //private List<Trainer> trainers; 

    // Getter e Setter per id
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    // Getter e Setter per name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter e Setter per description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter e Setter per muscleGroup
    public String getMuscleGroup() {
        return muscleGroup;
    }
    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    /*public List<Trainer> getTrainers() {
        return trainers;
    }
    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }*/
}
