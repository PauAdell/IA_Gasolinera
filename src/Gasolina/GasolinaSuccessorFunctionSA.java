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
        //GasolinaHeuristic heu = new GasolinaHeuristic();
        Random random = new Random(1234);

        boolean operadorAplicat = false;

        while (!operadorAplicat) {
            int i = random.nextInt(e.getCisternes().size());                    // random Cisterna i
            int k = random.nextInt(e.getCisternaX(i).getRecorregut().size());   // random posicio k del recorregut de la cisterna i

            int j = random.nextInt(e.getCisternes().size());                    // random Cisterna j
            int h = random.nextInt(e.getCisternaX(j).getRecorregut().size());   // random posicio h del recorregut de la cisterna j

            while (i == j)
                j = random.nextInt(e.getCisternes().size());                    // assegurem d'agafar cisternes diferents

            int rand = random.nextInt(3);

            if (rand == 0) {               // operador afegir
                GasolinaEstat nouEstat = new GasolinaEstat(e);
                nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getFantasma().getPosicioRecorregut(j));
                if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                    String S = "Peticio Afegida: "; //+ nouEstat.toString();
                    retVal.add(new Successor(S, nouEstat));
                    operadorAplicat = true;
                }
            }

            else if (rand == 1) {                                       // opeardor swap entre cisternes
                if (e.getCisternaX(i).getPosicioRecorregut(k).getDia() != -1 && e.getCisternaX(j).getPosicioRecorregut(h).getDia() != -1 ) {
                    GasolinaEstat nouEstat = new GasolinaEstat(e);
                    Cisterna c1 = nouEstat.getCisternaX(i);
                    Cisterna c2 = nouEstat.getCisternaX(j);
                    nouEstat.swapPetitions(c1, c2, j, h);
                    if (c1.getDist() <= 640 && c2.getDist() <= 640) {
                        String S = "Swap: "; //+ nouEstat.toString();
                        retVal.add(new Successor(S, nouEstat));
                        operadorAplicat = true;
                    }
                }
            }

            else {                                                              // operador swap entre cisterna i fantasma
                h = random.nextInt(e.getFantasma().getRecorregut().size());
                if (e.getCisternaX(i).getPosicioRecorregut(k).getDia() != -1) {
                    GasolinaEstat nouEstat = new GasolinaEstat(e);
                    Cisterna c1 = nouEstat.getCisternaX(i);
                    Cisterna c2 = nouEstat.getFantasma();
                    nouEstat.swapPetitions(c1, c2, k, h);
                    String S = "Swap: "; //+ nouEstat.toString();
                    if (c1.getDist() <= 640 && c2.getDist() <= 640) {
                        retVal.add(new Successor(S, nouEstat));
                        operadorAplicat = true;
                    }
                }
            }

        }

        return retVal;

    }
}
