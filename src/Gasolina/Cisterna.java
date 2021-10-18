package Gasolina;

import java.util.ArrayList;

public class Cisterna {

    private Posicio pos;
    private Posicio centre;
    private int dist;
    private int viatges;
    private int tancs;

    public Cisterna( int CoordX, int CoordY) {
        pos = new Posicio( CoordX, CoordY);
        centre = new Posicio( CoordX, CoordY);
        dist = 0;
        viatges = 0;
        tancs = 2;
    }

    public int getDist() {
        return this.dist;
    }

    public int getViatges() {
        return this.viatges;
    }

    public int getTancs() {
        return this.tancs;
    }

    public void setDist(int newDist) {
        this.dist = newDist;
    }

    public Posicio getPos() {
        return this.pos;
    }

    public Posicio getCentre() {
        return this.centre;
    }

    public void setViatges(int newViatges) {
        this.viatges = newViatges;
    }

    public void setTancs(int newTancs) {
        this.tancs = newTancs;
    }

    public void setPos( Posicio p) {
        pos = p;
    }

}
