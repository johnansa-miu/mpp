package Generics;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Pair<TFirst, TSecond> {
    private final TFirst first;
    private final TSecond second;

    public Pair(TFirst first, TSecond second){
        this.first = first;
        this.second = second;
    }

    public TFirst getFirst(){
        return this.first;
    }

    public TSecond getSecond(){
        return this.second;
    }



}
