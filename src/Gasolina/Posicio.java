package Gasolina;

public class Posicio {

    private int CoordX;
    private int CoordY;
    private int dia;

    ///////////////
    // CREADORES //
    ///////////////

    public Posicio(int cx, int cy, int d) {
        this.CoordX = cx;
        this.CoordY = cy;
        this.dia = d;
    }

    public Posicio( Posicio p) {
        this.CoordX = p.getCoordX();
        this.CoordY = p.getCoordY();
        this.dia = p.getDia();
    }

    ///////////////////////
    // GETTERS I SETTERS //
    ///////////////////////

    public int getCoordX() {
        return this.CoordX;
    }

    public void setCoordX(int CoordX) {
        this.CoordX = CoordX;
    }

    public int getCoordY() {
        return this.CoordY;
    }

    public void setCoordY(int CoordY) {
        this.CoordY = CoordY;
    }

    public int getDia() {
        return this.dia;
    }

}

