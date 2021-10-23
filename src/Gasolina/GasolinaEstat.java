package Gasolina;

import IA.Gasolina.Gasolineras;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.*;

import java.lang.Math;
import java.lang.reflect.Array;
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
        Cisterna aux;
        cisternes = new ArrayList<Cisterna>();
        for (int i = 0; i < estat.getCisternes().size(); ++i) {
            aux = new Cisterna(estat.getCisternaX(i));
            cisternes.add(aux);
        }
        fantasma = new Cisterna(estat.getFantasma());
        benefici = estat.getBenefici();
        k = estat.k;
        v = estat.v;
    }

    public ArrayList getCisternes() { return cisternes; }

    public Cisterna getFantasma() { return fantasma; }

    public double getBenefici() { return benefici; }

    public Cisterna getCisternaX(int i) {
        return cisternes.get(i);
    }

    public void generarEstatSolucio1() {

        double benefMax = 0;
        double benefMaxTotal = 0;
        int mesB = -1;
        boolean cisternesEsPodenMoure = true;

        while (fantasma.getRecorregut().size() > 0 && cisternesEsPodenMoure) {
            ArrayList<Posicio> millorsPeticions = new ArrayList<Posicio>();
            mesB = -1;
            benefMaxTotal = 0;
            for (int i = 0; i < cisternes.size(); ++i) {
                Posicio millor = new Posicio(-1, -1, -1);
                benefMax = 0;
                Cisterna c = cisternes.get(i);
                for (int j = 0; j < fantasma.getRecorregut().size(); ++j) {
                    double benefAux = 0;

                    if (c.getTancs() == 0) {
                        double d = c.getDist() + calcularDistancia(c.getPos(), c.getCentre()) + calcularDistancia(c.getCentre(), fantasma.getPosicioRecorregut(j));
                        if (d <= k && c.getViatges() < 5) {
                            benefAux -= 2 * calcularDistancia(c.getPos(), c.getCentre()) + calcularDistancia(c.getCentre(), fantasma.getPosicioRecorregut(j));
                            benefAux += 1000 * ((100 - Math.pow(2.0, fantasma.getPosicioRecorregut(j).getDia())) / 100);
                            if (benefAux > benefMax) {
                                benefMax = benefAux;
                                millor = fantasma.getPosicioRecorregut(j);
                            }
                        }
                    } else {
                        double d = c.getDist() + calcularDistancia(c.getPos(), fantasma.getPosicioRecorregut(j));
                        if (d <= k && c.getViatges() <= 4) {
                            benefAux -= 2 * calcularDistancia(c.getPos(), fantasma.getPosicioRecorregut(j));
                            benefAux += 1000 * ((100 - Math.pow(2.0, fantasma.getPosicioRecorregut(j).getDia())) / 100);
                            if (benefAux > benefMax) {
                                benefMax = benefAux;
                                millor = fantasma.getPosicioRecorregut(j);
                            }
                        }
                    }

                    if (benefMax > benefMaxTotal) {
                        benefMaxTotal = benefMax;
                        mesB = i;
                    }
                }
                millorsPeticions.add(millor);
            }

            if (mesB != -1 && hiHaMillorsPeticions(millorsPeticions)) {
                afegirDesti(cisternes.get(mesB), millorsPeticions.get(mesB));
            } else {
                cisternesEsPodenMoure = false;
            }
        }
    }

    private boolean hiHaMillorsPeticions( ArrayList<Posicio> p) {
        boolean aux = false;
        for (int i = 0; i < p.size(); ++i) {
            if (p.get(i).getCoordX() != -1 && p.get(i).getCoordY() != -1 && p.get(i).getDia() != -1) aux = true;
        }
        return aux;
    }

    // Operadors

    public boolean swapPetitions (Cisterna a, Cisterna b, int x , int y) {

        if ( a.getPos().getCoordX() == -1 &&  a.getPos().getCoordY() == -1) {
            double distB = b.getDist();
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
            double distA = a.getDist();
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
            double distA = a.getDist();
            for (int i = 1; i < a.getRecorregut().size(); ++i) {
                if (i == x) distA += calcularDistancia(a.getPosicioRecorregut(i - 1), b.getPosicioRecorregut(y));
                else if (i == x + 1) distA += calcularDistancia(b.getPosicioRecorregut(y), a.getPosicioRecorregut(i));
                else distA += calcularDistancia(a.getPosicioRecorregut(i - 1), a.getPosicioRecorregut(i));
            }
            double distB = b.getDist();
            for (int i = 1; i < b.getRecorregut().size(); ++i) {
                if (i == x) distB += calcularDistancia(b.getPosicioRecorregut(i - 1), a.getPosicioRecorregut(x));
                else if (i == x + 1) distB += calcularDistancia(a.getPosicioRecorregut(x), b.getPosicioRecorregut(i));
                else distB += calcularDistancia(b.getPosicioRecorregut(i - 1), b.getPosicioRecorregut(i));
            }
            if (distA <= k && distB <= k) {
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
        if (a.getTancs() == 0) {
            double d = a.getDist() + calcularDistancia( a.getPos() , a.getCentre()) + calcularDistancia(a.getCentre(), x);
            if (d <= k && a.getViatges() < 5) {
                a.setTancs(1);
                a.setViatges(a.getViatges() + 1);
                a.setPos(x);
                a.setDist(d);
                a.addPosicioARecorregut(a.getCentre());
                a.addPosicioARecorregut(x);
                benefici -= 2 * calcularDistancia( a.getPos() , a.getCentre()) + calcularDistancia(a.getCentre(), x);
                benefici += 1000 * ((100 - Math.pow(2.0, x.getDia())) / 100);
                fantasma.eliminaPosicio(x);
                return true;
            }
            return false;
        } else {
                double d = a.getDist() + calcularDistancia(a.getPos(), x);
                if (d <= k) {
                    if (x.getCoordX() == a.getCentre().getCoordX() && x.getCoordY() == a.getCentre().getCoordY()) {
                        a.setTancs(2);
                        a.setViatges(a.getViatges() + 1);
                        a.setPos(x);
                        a.setDist(d);
                        a.addPosicioARecorregut(x);
                    } else {
                        a.setTancs(a.getTancs() - 1);
                        a.setPos(x);
                        a.setDist(d);
                        a.addPosicioARecorregut(x);
                    }
                    benefici -= 2 * calcularDistancia(a.getPos(), x);
                    if (x.getDia() != -1) benefici += 1000 * ((100 - Math.pow(2.0, x.getDia())) / 100);
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
        /*
        for (int i = 0; i < cisternes.size(); ++i) {
            System.out.println("Cisterna: " + i);
            for (int j = 0; j < cisternes.get(i).getRecorregut().size(); ++j) {
                System.out.println("Posicio : " + j + " Coords de pes: " + cisternes.get(i).getPosicioRecorregut(j).getCoordX() + ',' + cisternes.get(i).getPosicioRecorregut(j).getCoordY() + " Dia pet: " + cisternes.get(i).getPosicioRecorregut(j).getDia());
            }
        }
        System.out.println("Benefici: " + benefici);
        */
    }

    public String toString() {
        String sortida = new String();

        for (int i = 0; i < cisternes.size(); ++i) {
            sortida = sortida + " Cisterna: " + i + "\n";
            for (int j = 0; j < cisternes.get(i).getRecorregut().size(); ++j) {
                sortida = sortida + "       Posicio : " + j + "\n" + "       Coords de pes: " + cisternes.get(i).getPosicioRecorregut(j).getCoordX() + ',' + cisternes.get(i).getPosicioRecorregut(j).getCoordY() + "\n" + "       Dia pet: " + cisternes.get(i).getPosicioRecorregut(j).getDia() + "\n";
            }
        }
        sortida = sortida + "\n" + " Benefici: " + benefici + "\n";
        sortida = sortida + "\n" + " Peticions que no s'han fet: " + fantasma.getRecorregut().size() + "\n";
        return sortida;
    }

}