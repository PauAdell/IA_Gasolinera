package Gasolina;

import IA.Gasolina.Gasolineras;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.*;

import java.lang.Math;
import java.util.*;


public class GasolinaEstat {

    public static Gasolineras gaso;
    public static CentrosDistribucion centres;
    public static int k;
    public static int v;

    private int benefici;

    private ArrayList<Cisterna> cisternes;
    private ArrayList<Peticio> peticions;

    public GasolinaEstat(int nGaso, int seed, int nCent, int mult) {
        gaso = new Gasolineras(nGaso, seed);
        centres = new CentrosDistribucion(nCent, mult, seed);
        k = 640;
        v = 5;
        benefici = 0;

        cisternes = new ArrayList<Cisterna>(nCent);
        for (int i = 0; i < nCent; ++i) {
            Cisterna aux = new Cisterna(centres.get(i).getCoordX(), centres.get(i).getCoordY());
            cisternes.add( aux );
        }

        peticions = new ArrayList<Peticio>();
        for (int i = 0; i < nGaso; ++i) {
            for (int j = 0; j < gaso.get(i).getPeticiones().size(); ++j) {
                Peticio aux = new Peticio(gaso.get(i).getCoordX(), gaso.get(i).getCoordY(), gaso.get(i).getPeticiones().get(j));
                peticions.add( aux );
            }
        }

    }

    // per cada cisterna assignem totes les peticions possibles fins que ja no li quedin viatges
    public void generarEstatSolucio1() {

        for (int i = 0; i < cisternes.size(); ++i) {

                Cisterna c = cisternes.get(i);

                for (int j = 0; j < peticions.size(); ++j) {

                    Peticio p = peticions.get(j);

                    int dist = c.getDist() + 2 * calcularDistancia(c.getPos(), c.getCentre());

                    if (c.getViatges() <= v && dist <= k) {          // si v <= 5 && d <= 640

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

                            if (distGaso + distReco + distTornada < k) {

                                c.setDist(c.getDist() + distGaso);
                                c.setTancs(c.getTancs() - 1);

                                c.setPos(p.getPos());
                                int d = p.getDia();

                                c.setEntregues(c.getEntregues() + 1);

                                benefici += 1000 * ((100 - Math.pow(2.0, d)) / 100);

                                peticions.remove(j);
                            }

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

    public void imprimirEstat() {
        for (int i = 0; i < cisternes.size(); ++i) {
            System.out.println("Camio: " + i + "   Distancia: " + cisternes.get(i).getDist() + "   Viatges: " + cisternes.get(i).getViatges() + "   Entregues: " + cisternes.get(i).getEntregues());
        }
        System.out.println("Num de peticions restants: " + peticions.size());
        System.out.println("Beneficis: " + benefici);

    }

    private int calcularDistancia(Posicio centre, Posicio gasolinera) {

        int coordX = Math.abs(centre.getCoordX() - gasolinera.getCoordX());
        int coordY = Math.abs(centre.getCoordY() - gasolinera.getCoordY());

        return coordX + coordY;

    }

}