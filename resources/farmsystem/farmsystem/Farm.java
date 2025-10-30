package finalprep.farmsystem;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private Integer id;
    private String name;
    private List<Field> fields;

    // Constructors
    public Farm() {
        this.fields = new ArrayList<>();
    }

    public Farm(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.fields = new ArrayList<>();
    }

    // Getters and Setters
    public Integer getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Field> getFields() { return fields; }

    public void addField(Field field) {
        this.fields.add(field);
    }

    @Override
    public String toString() {
        return name + " Farm";
    }
}

