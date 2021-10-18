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

    private int entregues;

    private int benefici;

    private ArrayList<Cisterna> cisternes;
    private ArrayList<Peticio> peticions;

    public GasolinaEstat(int nGaso, int seed, int nCent, int mult) {
        gaso = new Gasolineras(nGaso, seed);
        centres = new CentrosDistribucion(nCent, mult, seed);
        k = 640;
        v = 5;
        benefici = 0;
        entregues = 0;

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

                for (int j = 0; j < peticions.size(); ++j) {

                    int dist = cisternes.get(i).getDist() + 2* calcularDistancia(cisternes.get(i).getPos(), cisternes.get(i).getCentre());

                    if (cisternes.get(i).getViatges() <= v && dist <= k) {          // si v <= 5 && d <= 640

                        if (cisternes.get(i).getTancs() == 0) {             // si els tancs estan buits tornem al centre

                            cisternes.get(i).setViatges(cisternes.get(i).getViatges() + 1);
                            cisternes.get(i).setTancs(2);

                            int distNova = cisternes.get(i).getDist() + calcularDistancia(cisternes.get(i).getPos(), cisternes.get(i).getCentre());
                            cisternes.get(i).setPos(cisternes.get(i).getCentre());
                            cisternes.get(i).setDist(distNova);
                        }

                        else {          // tancs > 0
                            ++entregues;

                            int distNova = cisternes.get(i).getDist() + calcularDistancia(cisternes.get(i).getPos(), peticions.get(j).getPos());
                            //System.out.println("Distancia cist: " + cisternes.get(i).getDist() + " Calcul distanci: " + distNova)

                            cisternes.get(i).setDist(distNova);
                            cisternes.get(i).setTancs(cisternes.get(i).getTancs() - 1);

                            cisternes.get(i).setPos(peticions.get(j).getPos());
                            int d = peticions.get(j).getDia();

                            benefici += 1000 * ((100 - Math.pow(2.0, d))/100);


                            peticions.remove(j);

                        }

                    }

                }
                benefici -= 2 * cisternes.get(i).getDist();
        }
    }

    public void imprimirEstat() {
        for (int i = 0; i < cisternes.size(); ++i) {
            System.out.println("Camio: " + i + "   Distancia: " + cisternes.get(i).getDist() + "   Viatges: " + cisternes.get(i).getViatges());
        }
        System.out.println("Num de peticions restants: " + peticions.size());
        System.out.println("Beneficis: " + benefici);
        System.out.println("Entregues: " + entregues);
        /*

        System.out.println("Informacio Cisternes: ");
        for (int i = 0; i < cisternes.size(); ++i) {
            System.out.println("Viatges: " + cisternes.get(i).getViatges() + " Distancia: " + cisternes.get(i).getDist());
        }
        System.out.println("Informacio Peticions: " + peticions.size());
        for (int i = 0; i < peticions.size(); ++i) {
            System.out.println("Dia: " + peticions.get(i).getDia());
        }*/
    }

    private int calcularDistancia(Posicio centre, Posicio gasolinera) {

        int coordX = Math.abs(centre.getCoordX() - gasolinera.getCoordX());
        int coordY = Math.abs(centre.getCoordY() - gasolinera.getCoordY());

        return coordX + coordY;

    }

}