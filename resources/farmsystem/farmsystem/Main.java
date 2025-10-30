package farmsystem;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // farms
        Farm karakFarm = new Farm(1, "Karak");
        Farm tafilaFarm = new Farm(2, "Tafila");
        Farm maanFarm = new Farm(3, "Maan");
        // fields
        Field field1 = new Field(101, "North Field");
        Field field2 = new Field(102, "South Field");
        Field field3 = new Field(103, "North Field");
        Field field4 = new Field(104, "South Field");
        Field field5 = new Field(105, "East Field");
        // crops
        Crop crop1 = new Crop(1001, "Wheat");
        Crop crop2 = new Crop(1002, "Lintel");
        Crop crop3 = new Crop(1003, "Tomato");
        Crop crop4 = new Crop(1004, "Peach");
        Crop crop5 = new Crop(1005, "Tomato");

        karakFarm.addField(field1);
        karakFarm.addField(field2);
        tafilaFarm.addField(field3);
        tafilaFarm.addField(field4);
        maanFarm.addField(field5);

        field1.addCrop(crop1);
        field1.addCrop(crop2);
        field3.addCrop(crop3);
        field4.addCrop(crop4);
        field4.addCrop(crop5);

        List<Farm> farms = new ArrayList<>(Arrays.asList(karakFarm, tafilaFarm, maanFarm));

 

    public static boolean farmExists(List<Farm> farms, String farmName) {
        // Answer here ...
    }

    public static List<Farm> farmsWithTwoOrMoreFields(List<Farm> farms) {
        // Answer here ...
    }

    public static Long countTotalCrops(List<Farm> farms) {
        // Answer here ...
    }

    public static List<String> getUniqueCropTypes(Farm farm) {
      // Answer here ...


}


