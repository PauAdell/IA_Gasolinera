package Gasolina;

import IA.Gasolina.Gasolineras;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.*;

import java.lang.reflect.Array;
import java.util.*;

public class GasolinaEstat {

    public static int K;
    public static int V;
    public static Gasolineras Gaso;
    public static CentrosDistribucion Centres;
    private int nPeticions;
    private int Economia;
    private ArrayList<Distribucion> Camions;

    private int peticionsTotal;

    public GasolinaEstat(int nGaso, int seed, int nCent, int mult) {
        Gaso = new Gasolineras(nGaso, seed);
        Centres = new CentrosDistribucion(nCent, mult, seed);
        Camions = new ArrayList<Distribucion>();
        for (int i = 0; i < Centres.size(); i++){
            Camions.add(Centres.get(i));                    // Camions[i] = Centres[i]
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

    }

    public void generarEstatInicial2() {            // solucio millor

    }

    public void imprimirEstat() {
        for(int i = 0; i < Gaso.size(); i++) {
            System.out.println(peticionsTotal);
        }
    }


}