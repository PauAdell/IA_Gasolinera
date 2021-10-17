package Gasolina;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        GasolinaEstat e = new GasolinaEstat(12, 4, 5, 6);
        e.generarEstatInicial1();
        e.imprimirEstat();
        System.out.println("Hola bon dia");
    }
}
