package sabel.com.wegepunkte;

import java.util.ArrayList;
import java.util.List;

public class WegepunktRepo {

    // DATA FIELDS
    private List<WegePunkt> wegepunkte;

    // CONSTRUCTOR

    public WegepunktRepo() {
        wegepunkte = new ArrayList<>();
    }

    // METHODS

    private void add(WegePunkt wegePunkt) {
        if (wegePunkt != null) {
            wegepunkte.add(wegePunkt);
        }
    } // END VOID ADD

    public WegePunkt get(int index) {
        if (index < 0 || index >= wegepunkte.size()) {
            return null;
        }
        return wegepunkte.get(index);
    } // END WegePunkt get


    public int size() {
        return wegepunkte.size();
    } // END INT SIZE


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (WegePunkt wegePunkt : wegepunkte) {
            sb.append(wegePunkt.toString());
            sb.append(String.format("%n"));
        }
        return sb.toString();
    }
} // END CLASS
