package Gasolina;

import IA.Gasolina.Gasolineras;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.*;

import java.lang.reflect.Array;
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

    public void generarEstatSolucio1() {

    }

    public void imprimirEstat() {

    }


}