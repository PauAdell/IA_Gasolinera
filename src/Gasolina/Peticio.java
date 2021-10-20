package Gasolina;

public class Peticio {
    private int dia;
    private int cisternaAssignada;
    private Posicio posGaso;

    public Peticio(int cx, int cy, int d) {
        cisternaAssignada = -1;
        dia = d;
        posGaso = new Posicio( cx, cy);
    }

    public int getDia() {
        return this.dia;
    }

    public Posicio getPos() {
        return this.posGaso;
    }

    public int getCisternaAssignada() { return this.cisternaAssignada; }

    public void setDia(int newDia) {
        dia = newDia;
    }

    public void setPos( Posicio p) {
        posGaso = p;
    }

    public void setCisternaAssignada(int ca) { cisternaAssignada = ca; }


}
