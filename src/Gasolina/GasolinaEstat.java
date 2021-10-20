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

        fantasma = new Cisterna (0, 0);

        for (int i = 0; i < nGaso; ++i) {
            for (int j = 0; j < gaso.get(i).getPeticiones().size(); ++j) {
                Peticio aux = new Peticio(gaso.get(i).getCoordX(), gaso.get(i).getCoordY(), gaso.get(i).getPeticiones().get(j));
                fantasma.addPeticioARecorregut(aux);
                //System.out.println("Gaso: " + gaso.get(i).getCoordX() + "," + gaso.get(i).getCoordY());
                System.out.println("Peticio: " + aux.getPos().getCoordX() + "," + aux.getPos().getCoordY());
            }
        }

    }

    // per cada cisterna assignem totes les peticions possibles fins que ja no li quedin viatges i passem a la seguent
    public void generarEstatSolucio1() {
        int nPeticions = fantasma.getRecorregut().size();

        for (int i = 0; i < cisternes.size(); ++i) {

                Cisterna c = cisternes.get(i);

                for (int j = 0; j < nPeticions; ++j) {

                    Peticio p = fantasma.getPeticio(j);

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

    public void generarEstatSolucio2() {

        for (int i = 0; i < cisternes.size(); ++i) {

            for (int j = 0; j < gaso.size(); ++j) {

            }
        }

    }

    public void generaEstatSolucio2() {

    }

    public void imprimirEstat() {
        for (int i = 0; i < cisternes.size(); ++i) {
            System.out.println("Camio: " + i + "   Distancia: " + cisternes.get(i).getDist() + "   Viatges: " + cisternes.get(i).getViatges() + "   Entregues: " + cisternes.get(i).getEntregues());
            System.out.println("Vector de Peticions que fa el camio : ");
            for (int j = 0; j < cisternes.get(i).getRecorregut().size(); ++j) {
                System.out.print("Dia: " + cisternes.get(i).getPeticio(j).getDia());
                System.out.println(" pos: (" + cisternes.get(i).getPeticio(j).getPos().getCoordX() + "," + cisternes.get(i).getPeticio(j).getPos().getCoordY() + ")");
            }
        }

        System.out.println("Num de peticions restants: " + fantasma.getRecorregut().size() );

        for (int i = 0; i < fantasma.getRecorregut().size(); ++i) {
            System.out.print("Dia: " + fantasma.getPeticio(i).getDia());
            System.out.println(" pos: (" + fantasma.getPeticio(i).getPos().getCoordX() + "," + fantasma.getPeticio(i).getPos().getCoordY() + ")");
        }

        System.out.println("Beneficis: " + benefici);

    }

    private int calcularDistancia(Posicio centre, Posicio gasolinera) {

        int coordX = Math.abs(centre.getCoordX() - gasolinera.getCoordX());
        int coordY = Math.abs(centre.getCoordY() - gasolinera.getCoordY());

        return coordX + coordY;

    }

}