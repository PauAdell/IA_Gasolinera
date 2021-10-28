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

    private Cisterna fantasma;

    private ArrayList<Cisterna> cisternes;

    public GasolinaEstat(int nGaso, int seed, int nCent, int mult) {
        gaso = new Gasolineras(nGaso, seed);
        centres = new CentrosDistribucion(nCent, mult, seed);
        k = 640;
        v = 5;

        cisternes = new ArrayList<Cisterna>(nCent);
        for (int i = 0; i < nCent; ++i) {
            Cisterna aux = new Cisterna(centres.get(i).getCoordX(), centres.get(i).getCoordX());
            cisternes.add( aux );
            //System.out.println("Centre: " + centres.get(i).getCoordX() + "," + centres.get(i).getCoordX());
        }

        fantasma = new Cisterna (-1, -1);

        int comptador = 0;
        for (int i = 0; i < nGaso; ++i) {
            for (int j = 0; j < gaso.get(i).getPeticiones().size(); ++j) {
                Posicio aux = new Posicio(gaso.get(i).getCoordX(), gaso.get(i).getCoordY(), gaso.get(i).getPeticiones().get(j));
                fantasma.addPosicioARecorregut(aux);
                //System.out.println("Posicio recorregut: " + comptador + " Coords de peticio: " + fantasma.getPosicioRecorregut(comptador).getCoordX() + ',' + fantasma.getPosicioRecorregut(comptador).getCoordY() + " Dia pet: " + fantasma.getPosicioRecorregut(comptador).getDia());
                //System.out.println("Peticio: " + aux.getCoordX() + "," + aux.getCoordY());
                ++comptador;
            }
        }
        //System.out.println(fantasma.getRecorregut().size());

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
        k = estat.k;
        v = estat.v;
    }

    public ArrayList getCisternes() { return cisternes; }

    public Cisterna getFantasma() { return fantasma; }

    public double getBenefici() {
        double benefici = 0.0;
        for (int i = 0; i < cisternes.size(); i++){
            for (int j = 1; j < getCisternaX(i).getRecorregut().size(); ++j) {
                benefici -= 2 * calcularDistancia(getCisternaX(i).getPosicioRecorregut(j-1), getCisternaX(i).getPosicioRecorregut(j));
                if (getCisternaX(i).getPosicioRecorregut(j).getDia() != -1) benefici += 1000 * ((100 - Math.pow(2.0, getCisternaX(i).getPosicioRecorregut(j).getDia())) / 100);
            }
        }
        return benefici;
    }

    public double getHeuristic() {
        double benefici = 0.0;
        for (int i = 0; i < cisternes.size(); i++){
            for (int j = 1; j < getCisternaX(i).getRecorregut().size(); ++j) {
                benefici -= 2 * calcularDistancia(getCisternaX(i).getPosicioRecorregut(j-1), getCisternaX(i).getPosicioRecorregut(j));
                if (getCisternaX(i).getPosicioRecorregut(j).getDia() != -1) benefici += 1000 * ((100 - Math.pow(2.0, getCisternaX(i).getPosicioRecorregut(j).getDia())) / 100);
                else if ( getCisternaX(i).getRecorregut().size() - 1 == j && getCisternaX(i).getPosicioRecorregut(j).getDia() == -1) benefici += 1000 * ((100 - Math.pow(2.0, getCisternaX(i).getPosicioRecorregut(j).getDia()+6)) / 100);
            }
        }
        return benefici;
    }

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
                if (getCisternaX(mesB).getTancs() == 0) {
                    double d = getCisternaX(mesB).getDist() + calcularDistancia( getCisternaX(mesB).getPos() , getCisternaX(mesB).getCentre()) + calcularDistancia(getCisternaX(mesB).getCentre(), millorsPeticions.get(mesB));
                    if (d <= k && getCisternaX(mesB).getViatges() < 5) {
                        getCisternaX(mesB).setTancs(1);
                        getCisternaX(mesB).setViatges(getCisternaX(mesB).getViatges() + 1);
                        getCisternaX(mesB).setPos(millorsPeticions.get(mesB));
                        getCisternaX(mesB).setDist(d);
                        getCisternaX(mesB).addPosicioARecorregut(getCisternaX(mesB).getCentre());
                        getCisternaX(mesB).addPosicioARecorregut(millorsPeticions.get(mesB));
                        fantasma.eliminaPosicio(millorsPeticions.get(mesB));
                    }

                } else {
                    double d = getCisternaX(mesB).getDist() + calcularDistancia(getCisternaX(mesB).getPos(), millorsPeticions.get(mesB));
                    if (d <= k) {
                        if (millorsPeticions.get(mesB).getCoordX() == getCisternaX(mesB).getCentre().getCoordX() && millorsPeticions.get(mesB).getCoordY() == getCisternaX(mesB).getCentre().getCoordY()) {
                            getCisternaX(mesB).setTancs(2);
                            getCisternaX(mesB).setViatges(getCisternaX(mesB).getViatges() + 1);
                            getCisternaX(mesB).setPos(millorsPeticions.get(mesB));
                            getCisternaX(mesB).setDist(d);
                            getCisternaX(mesB).addPosicioARecorregut(millorsPeticions.get(mesB));
                        } else {
                            getCisternaX(mesB).setTancs(getCisternaX(mesB).getTancs() - 1);
                            getCisternaX(mesB).setPos(millorsPeticions.get(mesB));
                            getCisternaX(mesB).setDist(d);
                            getCisternaX(mesB).addPosicioARecorregut(millorsPeticions.get(mesB));
                        }
                        fantasma.eliminaPosicio(millorsPeticions.get(mesB));
                    }
                }
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

    public void swapPetitions (Cisterna a, Cisterna b, int x , int y) {
        if (b.getPos().getCoordX() == -1 && b.getPos().getCoordY() == -1) {
            double distA = a.getDist();
            distA -= calcularDistancia(a.getPosicioRecorregut(x - 1), a.getPosicioRecorregut(x));
            distA += calcularDistancia(a.getPosicioRecorregut(x - 1), b.getPosicioRecorregut(y));
            if (a.getRecorregut().size() - 1 != x) {
                distA -= calcularDistancia(a.getPosicioRecorregut(x + 1), a.getPosicioRecorregut(x));
                distA += calcularDistancia(b.getPosicioRecorregut(y), a.getPosicioRecorregut(x + 1));
            }
            Posicio aux = new Posicio(a.getPosicioRecorregut(x));
            a.setPosicioARecorregut(x, b.getPosicioRecorregut(y));
            b.setPosicioARecorregut(y, aux);
            a.setDist(distA);
        }

        else {

            double distA = a.getDist();
            distA -= calcularDistancia(a.getPosicioRecorregut(x - 1), a.getPosicioRecorregut(x));
            distA += calcularDistancia(a.getPosicioRecorregut(x - 1), b.getPosicioRecorregut(y));
            if (a.getRecorregut().size() - 1 != x) {
                distA -= calcularDistancia(a.getPosicioRecorregut(x + 1), a.getPosicioRecorregut(x));
                distA += calcularDistancia(b.getPosicioRecorregut(y), a.getPosicioRecorregut(x + 1));
            }

            double distB = b.getDist();
            distB -= calcularDistancia(b.getPosicioRecorregut(y - 1), b.getPosicioRecorregut(y));
            distB += calcularDistancia(b.getPosicioRecorregut(y - 1), a.getPosicioRecorregut(x));
            if (b.getRecorregut().size() - 1 != y) {
                distB -= calcularDistancia(b.getPosicioRecorregut(y + 1), b.getPosicioRecorregut(y));
                distB += calcularDistancia(a.getPosicioRecorregut(x), b.getPosicioRecorregut(y + 1));
            }

            Posicio aux = new Posicio(a.getPosicioRecorregut(x));
            a.setPosicioARecorregut(x, b.getPosicioRecorregut(y));
            b.setPosicioARecorregut(y, aux);
            a.setDist(distA);
            b.setDist(distB);

        }

    }

    public void afegirDesti (Cisterna a, Posicio x) {
        double d = a.getDist() + calcularDistancia(a.getPos(), x);
        if (x.getDia() == -1) {
            if (a.getCentre().getCoordX() == x.getCoordX() && a.getCentre().getCoordY() == x.getCoordY()) {
                a.setTancs(2);
                a.setViatges(a.getViatges() + 1);
                a.setPos(x);
                a.setDist(d);
                a.addPosicioARecorregut(x);
            }
        } else {
                if (a.getTancs() != 0) {
                    a.setTancs(a.getTancs() - 1);
                    a.setPos(x);
                    a.setDist(d);
                    a.addPosicioARecorregut(x);
                    fantasma.eliminaPosicio(x);
                }
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
                System.out.println("Posicio : " + j + " Coords de pes: " + cisternes.get(i).getPosicioRecorregut(j).getCoordX() + ',' + cisternes.get(i).getPosicioRecorregut(j).getCoordY() + " Dia pet: " + cisternes.get(i).getPosicioRecorregut(j).getDia());
            }
        }
        System.out.println("Benefici: " + getBenefici());

    }

    public String toString() {
        String sortida = new String();
        /*
        for (int i = 0; i < cisternes.size(); ++i) {
            sortida = sortida + " Cisterna: " + i + "\n";
            for (int j = 0; j < cisternes.get(i).getRecorregut().size(); ++j) {
                sortida = sortida + "       Posicio : " + j + "\n" + "       Coords de pes: " + cisternes.get(i).getPosicioRecorregut(j).getCoordX() + ',' + cisternes.get(i).getPosicioRecorregut(j).getCoordY() + "\n" + "       Dia pet: " + cisternes.get(i).getPosicioRecorregut(j).getDia() + "\n";
            }
        } */
        sortida = sortida + "\n" + " Benefici: " + getBenefici() + "\n";
        sortida = sortida + "\n" + " Peticions que no s'han fet: " + fantasma.getRecorregut().size() + "\n";
        return sortida;
    }

}