package Gasolina;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        GasolinaEstat estat = new GasolinaEstat(12, 1234, 5, 6);
        estat.generarEstatSolucio1();
        estat.imprimirEstat();
        System.out.println("Hola bon dia");
    }
}
