package Gasolina;

import IA.Gasolina.Gasolineras;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.*;

import java.lang.Math;
import java.util.*;


public class GasolinaEstat {

    public static Gasolineras gaso;
    public static CentrosDistribucion centres;
    public static double k;
    public static int v;

    private double benefici;

    private Cisterna fantasma;

    private ArrayList<Cisterna> cisternes;

    public GasolinaEstat(int nGaso, int seed, int nCent, int mult) {
        gaso = new Gasolineras(nGaso, seed);
        centres = new CentrosDistribucion(nCent, mult, seed);
        k = 640;
        v = 5;
        benefici = 0;

        cisternes = new ArrayList<Cisterna>(nCent);
        for (int i = 0; i < nCent; ++i) {
            Cisterna aux = new Cisterna(centres.get(i).getCoordX(), centres.get(i).getCoordX());
            cisternes.add( aux );
            System.out.println("Centre: " + centres.get(i).getCoordX() + "," + centres.get(i).getCoordX());
        }

        fantasma = new Cisterna (-1, -1);

        int comptador = 0;
        for (int i = 0; i < nGaso; ++i) {
            for (int j = 0; j < gaso.get(i).getPeticiones().size(); ++j) {
                Posicio aux = new Posicio(gaso.get(i).getCoordX(), gaso.get(i).getCoordY(), gaso.get(i).getPeticiones().get(j));
                fantasma.addPosicioARecorregut(aux);
                System.out.println("Posicio recorregut: " + comptador + " Coords de peticio: " + fantasma.getPosicioRecorregut(comptador).getCoordX() + ',' + fantasma.getPosicioRecorregut(comptador).getCoordY() + " Dia pet: " + fantasma.getPosicioRecorregut(comptador).getDia());
                //System.out.println("Peticio: " + aux.getCoordX() + "," + aux.getCoordY());
                ++comptador;
            }
        }
        System.out.println(fantasma.getRecorregut().size());

    }

    public GasolinaEstat(GasolinaEstat estat) {
        gaso = estat.gaso;
        centres = estat.centres;
        cisternes = new ArrayList<Cisterna>(estat.getCisternes());
        fantasma = new Cisterna(estat.getFantasma());
        benefici = estat.benefici;
        k = estat.k;
        v = estat.v;
    }

    public ArrayList getCisternes() { return cisternes; }

    public Cisterna getFantasma() { return fantasma; }

    public double getBenefici() { return benefici; }

    public Cisterna getCisternaX(int i) {
        return cisternes.get(i);
    }

    //Benefici = 0, tenir tots els camions parats, sense assignacions/peticions
    //public void generarEstatSolucio1() {

    //}

    /*
    // per cada cisterna assignem totes les peticions possibles fins que ja no li quedin viatges i passem a la seguent
    public void generarEstatSolucio2() {
        int nPeticions = fantasma.getRecorregut().size();

        for (int i = 0; i < cisternes.size(); ++i) {

                Cisterna c = cisternes.get(i);

                for (int j = 0; j < nPeticions; ++j) {

                    Posicio p = fantasma.getPeticio(j);

                    int dist = c.getDist() + calcularDistancia(c.getPos(), p.getPos()) + calcularDistancia(p.getPos(), c.getCentre());

                    if (c.getViatges() <= v && dist <= k) {

                        if (c.getTancs() == 0) {             // si els tancs estan buits tornem al centre

                            c.setViatges(c.getViatges() + 1);
                            c.setTancs(2);

                            int distNova = c.getDist() + calcularDistancia(c.getPos(), c.getCentre());
                            c.setPos(c.getCentre());
                            c.setDist(distNova);
                        }

                        else {          // tancs > 0

                            int distGaso = calcularDistancia(c.getPos(), p.getPos());
                            int distReco = c.getDist();
                            int distTornada = calcularDistancia(p.getPos(), c.getCentre());

                            //System.out.print("Camio: " + i + " esta a (" + c.getPos().getCoordX() + "," + c.getPos().getCoordY());
                            //System.out.print(") vol anar a (" + p.getPos().getCoordX() + "," + p.getPos().getCoordY());
                            //System.out.println(") a una distancia " + distGaso + " amb tornada " + distTornada + " ha recorregut " + distReco );

                            if (distGaso + distReco + distTornada < k) {

                                c.setDist(c.getDist() + distGaso);
                                c.setTancs(c.getTancs() - 1);

                                c.setPos(p.getPos());
                                int d = p.getDia();

                                c.setEntregues(c.getEntregues() + 1);

                                benefici += 1000 * ((100 - Math.pow(2.0, d)) / 100);
                                c.addPeticioARecorregut(p);
                                fantasma.removePeticio(j);
                            }

                        }
                        if (nPeticions != fantasma.getRecorregut().size())  {
                            j -= 1;
                            nPeticions = fantasma.getRecorregut().size();
                        }
                    }

                }
                if (c.getPos().getCoordX() != c.getCentre().getCoordX() && c.getPos().getCoordY() != c.getCentre().getCoordY()) {
                    int dist = calcularDistancia(c.getPos(), c.getCentre());
                    c.setDist(c.getDist() + dist);
                    c.setPos(c.getCentre());
                    c.setViatges(c.getViatges() + 1);
                }
                benefici -= 2 * c.getDist();
        }
    }
     */

    // Operadors

    public boolean swapPetitions (Cisterna a, Cisterna b, int x , int y) {

        if ( a.getPos().getCoordX() == -1 &&  a.getPos().getCoordY() == -1) {
            int distB = 0;
            for (int i = 1; i < b.getRecorregut().size(); ++i) {
                if ( i == x ) distB += calcularDistancia( b.getPosicioRecorregut(i-1), a.getPosicioRecorregut(x));
                else if ( i == x+1 ) distB += calcularDistancia( a.getPosicioRecorregut(x), b.getPosicioRecorregut(i));
                else distB += calcularDistancia( b.getPosicioRecorregut(i-1), b.getPosicioRecorregut(i));
            }
            if ( distB < k) {
                int distBAux = 2 * (calcularDistancia( b.getPosicioRecorregut(y-1), b.getPosicioRecorregut(y)) + calcularDistancia( b.getPosicioRecorregut(y), b.getPosicioRecorregut(y+1)));
                Posicio aux = new Posicio(a.getPosicioRecorregut(x));
                a.setPosicioARecorregut(x, b.getPosicioRecorregut(y));
                b.setPosicioARecorregut(y, aux);
                b.setDist(distB);
                benefici -= (distBAux);
                distBAux = 2 * (calcularDistancia( b.getPosicioRecorregut(y-1), b.getPosicioRecorregut(y)) + calcularDistancia( b.getPosicioRecorregut(y), b.getPosicioRecorregut(y+1)));
                benefici += (distBAux);
                return true;
            }
            return false;
        } else if ( b.getPos().getCoordX() == -1 &&  b.getPos().getCoordY() == -1) {
            int distA = 0;
            for (int i = 1; i < a.getRecorregut().size(); ++i) {
                if ( i == x ) distA += calcularDistancia( a.getPosicioRecorregut(i-1), b.getPosicioRecorregut(y));
                else if ( i == x+1 ) distA += calcularDistancia( b.getPosicioRecorregut(y), a.getPosicioRecorregut(i));
                else distA += calcularDistancia( a.getPosicioRecorregut(i-1), a.getPosicioRecorregut(i));
            }
            if ( distA < k) {
                int distAAux = 2 * (calcularDistancia( a.getPosicioRecorregut(x-1), a.getPosicioRecorregut(x)) + calcularDistancia( a.getPosicioRecorregut(x), a.getPosicioRecorregut(x+1)));
                Posicio aux = new Posicio(a.getPosicioRecorregut(x));
                a.setPosicioARecorregut(x, b.getPosicioRecorregut(y));
                b.setPosicioARecorregut(y, aux);
                b.setDist(distA);
                benefici -= (distAAux);
                distAAux = 2 * (calcularDistancia( a.getPosicioRecorregut(x-1), a.getPosicioRecorregut(x)) + calcularDistancia( a.getPosicioRecorregut(x), a.getPosicioRecorregut(x+1)));
                benefici += (distAAux);
                return true;
            }
            return false;
        } else {
            int distA = 0;
            for (int i = 1; i < a.getRecorregut().size(); ++i) {
                if (i == x) distA += calcularDistancia(a.getPosicioRecorregut(i - 1), b.getPosicioRecorregut(y));
                else if (i == x + 1) distA += calcularDistancia(b.getPosicioRecorregut(y), a.getPosicioRecorregut(i));
                else distA += calcularDistancia(a.getPosicioRecorregut(i - 1), a.getPosicioRecorregut(i));
            }
            int distB = 0;
            for (int i = 1; i < b.getRecorregut().size(); ++i) {
                if (i == x) distB += calcularDistancia(b.getPosicioRecorregut(i - 1), a.getPosicioRecorregut(x));
                else if (i == x + 1) distB += calcularDistancia(a.getPosicioRecorregut(x), b.getPosicioRecorregut(i));
                else distB += calcularDistancia(b.getPosicioRecorregut(i - 1), b.getPosicioRecorregut(i));
            }
            if (distA < k && distB < k) {
                int distAAux = 2 * (calcularDistancia(a.getPosicioRecorregut(x - 1), a.getPosicioRecorregut(x)) + calcularDistancia(a.getPosicioRecorregut(x), a.getPosicioRecorregut(x + 1)));
                int distBAux = 2 * (calcularDistancia(b.getPosicioRecorregut(y - 1), b.getPosicioRecorregut(y)) + calcularDistancia(b.getPosicioRecorregut(y), b.getPosicioRecorregut(y + 1)));
                Posicio aux = new Posicio(a.getPosicioRecorregut(x));
                a.setPosicioARecorregut(x, b.getPosicioRecorregut(y));
                b.setPosicioARecorregut(y, aux);
                a.setDist(distA);
                b.setDist(distB);
                benefici -= (distAAux + distBAux);
                distAAux = 2 * (calcularDistancia(a.getPosicioRecorregut(x - 1), a.getPosicioRecorregut(x)) + calcularDistancia(a.getPosicioRecorregut(x), a.getPosicioRecorregut(x + 1)));
                distBAux = 2 * (calcularDistancia(b.getPosicioRecorregut(y - 1), b.getPosicioRecorregut(y)) + calcularDistancia(b.getPosicioRecorregut(y), b.getPosicioRecorregut(y + 1)));
                benefici += (distAAux + distBAux);
                return true;
            }
            return false;
        }
    }

    public boolean afegirDesti (Cisterna a, Posicio x) {
        if (a.getPos().getCoordX() == -1 &&  a.getPos().getCoordY() == -1) return false;
        else {
            double d = a.getDist() + calcularDistancia(a.getPos(), x);
            System.out.println(d);
            if ( d <= k) {
                if (x.getCoordX() == a.getCentre().getCoordX() && x.getCoordY() == a.getCentre().getCoordY()) {
                    a.setTancs(2);
                    a.setViatges(a.getViatges() + 1);
                    a.setPos(x);
                    a.setDist(d);
                    a.addPosicioARecorregut(x);
                } else if (a.getTancs() == 0 ) {
                    return false;
                } else {
                    a.setTancs(a.getTancs() - 1);
                    a.setPos(x);
                    a.setDist(d);
                    a.addPosicioARecorregut(x);
                }
                benefici -= 2 * calcularDistancia(a.getPos(), x);
                if (x.getDia() != -1) benefici += 1000 * ((100 - Math.pow(2.0, x.getDia())) / 100);
                System.out.println("Ara eliminare la pos: " + x.getCoordX() + "," + x.getCoordY());
                fantasma.eliminaPosicio(x);
                return true;
            }
            return false;
        }
    }

    // Funcions Auxiliars

    private int calcularDistancia(Posicio centre, Posicio gasolinera) {

        int coordX = Math.abs(centre.getCoordX() - gasolinera.getCoordX());
        int coordY = Math.abs(centre.getCoordY() - gasolinera.getCoordY());

        return coordX + coordY;

    }

    public void imprimirEstat() {
        int comptador = 0;
        for (int i = 0; i < cisternes.size(); ++i) {
            System.out.println("Cisterna: " + i);
            for (int j = 0; j < cisternes.get(i).getRecorregut().size(); ++j) {
                System.out.println("Posicio : " + comptador + " Coords de pes: " + cisternes.get(i).getPosicioRecorregut(j).getCoordX() + ',' + cisternes.get(i).getPosicioRecorregut(j).getCoordY() + " Dia pet: " + cisternes.get(i).getPosicioRecorregut(j).getDia());
            }
        }
        System.out.println("Benefici: " + benefici);
    }

}