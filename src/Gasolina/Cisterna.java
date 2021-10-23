package Gasolina;

import java.util.ArrayList;

public class Cisterna {

    private Posicio pos;
    private Posicio centre;
    private ArrayList<Posicio> recorregut;
    private double dist;
    private int viatges;
    private int tancs;

    private int entregues;

    public Cisterna( int CoordX, int CoordY) {
        pos = new Posicio( CoordX, CoordY, -1);
        centre = new Posicio( CoordX, CoordY, -1);
        recorregut = new ArrayList<Posicio>();
        if (CoordX != -1 && CoordY != -1) recorregut.add(0, centre);
        dist = 0;
        viatges = 1;
        tancs = 2;
        entregues = 0;
    }

    public Cisterna(Cisterna c) {
        pos = new Posicio(c.getPos());
        centre = new Posicio(c.getCentre());
        Posicio aux;
        recorregut = new ArrayList<Posicio>();
        for(int i = 0; i < c.getRecorregut().size(); ++i) {
            aux = new Posicio(c.getPosicioRecorregut(i));
            recorregut.add(aux);
        }
        this.dist = c.dist;
        this.viatges = c.viatges;
        this.tancs = c.tancs;
        this.entregues = c.entregues;
    }

    public double getDist() {
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

    public void setDist(double newDist) {
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

    public void addPosicioARecorregut ( Posicio p ) {
        recorregut.add(p);
    }

    public void eliminaPosicio( Posicio x ) { recorregut.remove(x);}

    public void setPosicioARecorregut ( int i, Posicio b) {
        recorregut.set(i, b);
    }


}
