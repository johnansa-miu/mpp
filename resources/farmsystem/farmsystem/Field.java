package finalprep.farmsystem;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private Integer id;
    private String fieldName;
    private List<Crop> crops;

    // Constructors
    public Field() {
        this.crops = new ArrayList<>();
    }

    public Field(Integer id, String fieldName) {
        this.id = id;
        this.fieldName = fieldName;
        this.crops = new ArrayList<>();
    }

    // Getters and Setters
    public Integer getId() { return id; }

    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }

    public List<Crop> getCrops() { return crops; }

    public void addCrop(Crop crop) {
        this.crops.add(crop);
    }
}
