package finalprep.farmsystem;

public class Crop {
    private Integer id;
    private String cropType;

    // Constructors
    public Crop() {}

    public Crop(Integer id, String cropType) {
        this.id = id;
        this.cropType = cropType;
    }

    // Getters and Setters
    public Integer getId() { return id; }

    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }

}
