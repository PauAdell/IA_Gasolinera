package Gasolina;

import java.util.*;

public class CustomComparator implements Comparator<Peticio> {

    @Override
    public int compare(Peticio p1, Peticio p2) {
        return p1.getDia()-p2.getDia();
    }
}
