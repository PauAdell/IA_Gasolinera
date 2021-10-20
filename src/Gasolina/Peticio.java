package Gasolina;

public class Peticio {
    private int dia;
    private Posicio posGaso;

    public Peticio(int cx, int cy, int d) {
        dia = d;
        posGaso = new Posicio( cx, cy);
    }

    public int getDia() {
        return this.dia;
    }

    public Posicio getPos() {
        return this.posGaso;
    }

    public void setDia(int newDia) {
        dia = newDia;
    }

    public void setPos( Posicio p) {
        posGaso = p;
    }

}
