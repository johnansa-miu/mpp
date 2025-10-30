package Generics;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ListC {

    public static  <T extends Comparable<? super T>> T findFirst(List<T> elements){
        Optional<Optional<T>> value = Optional.of(elements
                .stream()
                .sorted()
                .findFirst());

        return value.get().orElseThrow();

    }

    public static <T> int mySearch(List<T> elements, T element){

        for (int i = 0; i < elements.size(); i++) {
            if(elements.get(i) == element){
                return i;
            }
        }

        return -1;
    }
}
