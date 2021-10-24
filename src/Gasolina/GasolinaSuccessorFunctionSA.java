package Gasolina;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class GasolinaSuccessorFunctionSA implements SuccessorFunction {

    public GasolinaSuccessorFunctionSA() { }

    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();
        GasolinaEstat e = (GasolinaEstat) aState;
        GasolinaHeuristic heu = new GasolinaHeuristic();
        Random random = new Random(1234);

        boolean success = false;

        while (!success) {
            int i = random.nextInt(e.getCisternes().size());                    // random Cisterna i
            int j = random.nextInt(e.getCisternes().size());                    // random Cisterna j
            int k = random.nextInt(e.getCisternaX(i).getRecorregut().size());   // random posicio k de recorregut cisterna i
            int h = random.nextInt(e.getCisternaX(j).getRecorregut().size());   // random posicio h de recorregut cisterna j

            if (random.nextInt(2) == 0) {               // operador afegir
                GasolinaEstat nouEstat = new GasolinaEstat(e);
                nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getFantasma().getPosicioRecorregut(j));
                if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                    String S = "Peticio Afegida: " + nouEstat.toString();
                    retVal.add(new Successor(S, nouEstat));
                }
            }

            else {                                              // opeardor swap
                Cisterna aux2 = new Cisterna(e.getCisternaX(j));        // Cisterna k
                if (aux2.getPosicioRecorregut(h).getDia() != -1) {
                    GasolinaEstat nouEstat = new GasolinaEstat(e);
                    Cisterna c1 = nouEstat.getCisternaX(i);
                    Cisterna c2 = nouEstat.getCisternaX(k);
                    nouEstat.swapPetitions(c1, c2, j, h);
                    String S = "Swap: " + nouEstat.toString();
                    if (c1.getDist() <= 640 && c2.getDist() <= 640)
                        retVal.add(new Successor(S, nouEstat));
                }
            }

        }

        // FALTA IMPLEMENTAR AMB CISTERNA FANTASMA

        //int i, j;
        //i = myRandom.nextInt(e.getNPeticions());

        return retVal;


    }
}
