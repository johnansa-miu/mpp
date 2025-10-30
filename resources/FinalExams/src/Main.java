import Generics.ListC;
import Generics.Pair;

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

        System.out.println(farmExists(farms, "Maan"));

        System.out.println(farmsWithTwoOrMoreFields(farms));
        System.out.println(countTotalCrops(farms));

        System.out.println(getUniqueCropTypes(tafilaFarm));

        var pair = new Pair<String, Integer>("John", 32);

        System.out.println("first = " + pair.getFirst());
        System.out.println("second = " + pair.getSecond());

        List<String> list  = Arrays.asList("John", "Okon", "nsa");
Collections.sort(list);
        System.out.println(ListC.mySearch(list, "ggg"));

    }

    public static boolean farmExists(List<Farm> farms, String farmName){
       return farms.stream()
                .anyMatch(x-> x.getName().equalsIgnoreCase(farmName));
    }

    public static List<Farm> farmsWithTwoOrMoreFields (List<Farm> farms) {

       return farms.stream()
                .filter(x->x.getFields() != null)
                .filter(x-> !x.getFields().isEmpty())
                .filter(x->x.getFields().size() >= 2)
                .toList();
    }
    public static Long countTotalCrops (List<Farm> farms) {
            Optional<Long> count = Optional.of(farms.stream()
                    .flatMap(x -> x.getFields().stream())
                    .mapToLong(x -> x.getCrops().size())
                    .sum());

        return count.orElse(0L);
    }

    public static List<String> getUniqueCropTypes(Farm farm){

        Optional<List<String>> uniqueCropTypes = Optional.of(farm.getFields().stream()
                .flatMap(x -> x.getCrops().stream())
                .map(Crop::getCropType)
                .distinct()
                .toList());
        return uniqueCropTypes.orElse(null);
    }
}