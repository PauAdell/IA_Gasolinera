package Gasolina;

import IA.Gasolina.Gasolineras;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.*;

import java.lang.reflect.Array;
import java.util.*;

public class GasolinaEstat {

    public static Gasolineras Gaso;
    public static CentrosDistribucion Centres;
    private ArrayList<Distribucion> Camions;
    private ArrayList<Integer> visitedGaso;

    private int peticionsTotal;

    public GasolinaEstat(int nGaso, int seed, int nCent, int mult) {
        Gaso = new Gasolineras(nGaso, seed);
        Centres = new CentrosDistribucion(nCent, mult, seed);
        Camions = new ArrayList<Distribucion>();
        visitedGaso = new ArrayList<Integer>();
        for (int i = 0; i < Centres.size(); i++){
            Camions.add(Centres.get(i));                    // Camions[i] = Centres[i]
        }

        for (int i = 0; i < Centres.size(); i++) {
            visitedGaso.add(i, 0);
        }

        for (int i = 0; i < Gaso.size(); i++) {
            peticionsTotal += Gaso.get(i).getPeticiones().size();
        }

    }

    public void setDestinacio(Distribucion d, int coordX, int coordY) {
        d.setCoordX(coordX);
        d.setCoordY(coordY);
    }

    public void generarEstatInicial1() {            // solucio pocha
        for (int i = 0; i < Camions.size(); i++) {
            if (visitedGaso.get(i) < 2) {
                for (int j = 0; j < Gaso.size(); j++) {
                    if (Gaso.get(j).getPeticiones().size() > 0) {
                        setDestinacio(Camions.get(i), Gaso.get(j).getCoordX(), Gaso.get(j).getCoordY());
                        visitedGaso.set(i, visitedGaso.get(i)+1);
                    }
                }
            }
            else {
                setDestinacio(Camions.get(i), Centres.get(i).getCoordX(), Centres.get(i).getCoordY());
                visitedGaso.set(i, 0);
            }
        }
    }

    public void generarEstatInicial2() {            // solucio millor

    }

    public void imprimirEstat() {
        for(int i = 0; i < Gaso.size(); i++) {
            System.out.println(peticionsTotal);
        }
    }


}