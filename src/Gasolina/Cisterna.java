package Gasolina;

import java.util.ArrayList;

public class Cisterna {

    private Posicio pos;
    private Posicio centre;
    ArrayList<Posicio> recorregut;
    private int dist;
    private int viatges;
    private int tancs;

    private int entregues;

    public Cisterna( int CoordX, int CoordY) {
        pos = new Posicio( CoordX, CoordY, -1);
        centre = new Posicio( CoordX, CoordY, -1);
        recorregut = new ArrayList<Posicio>();
        dist = 0;
        viatges = 0;
        tancs = 2;
        entregues = 0;
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

    public int getEntregues() {return entregues;}

    public ArrayList getRecorregut() {return recorregut;}

    public Posicio getPosicioRecorregut( int i ) { return recorregut.get(i);};

    public void setEntregues(int e) {entregues = e;}

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

    public void addPosicioARecorregut ( Posicio p) {
        recorregut.add(p);
    }

    public void removePeticio( int i ) { recorregut.remove(i);}

    public void setPosicioARecorregut ( int i, Posicio b) {
        recorregut.set(i, b);
    }


}
