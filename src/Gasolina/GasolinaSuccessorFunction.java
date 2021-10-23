package Gasolina;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class GasolinaSuccessorFunction implements SuccessorFunction {

    public GasolinaSuccessorFunction(){ }

    public List getSuccessors(Object aState) {

        ArrayList<Successor> retVal = new ArrayList<>();
        GasolinaEstat e = (GasolinaEstat) aState;

        for (int i = 0; i < e.getCisternes().size(); ++i) {
            for (int j = 0; j < e.getFantasma().getRecorregut().size(); ++j) {
                    GasolinaEstat nouEstat = new GasolinaEstat(e);
                    if (nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getFantasma().getPosicioRecorregut(j))) {
                        String S = nouEstat.toString();
                        retVal.add(new Successor(S, nouEstat));
                    }
            }

            Cisterna aux = new Cisterna(e.getCisternaX(i));        // Cisterna i

            for (int j = 1; j < e.getCisternaX(i).getRecorregut().size()-1; ++j) {
                GasolinaEstat nouEstat = new GasolinaEstat(e);


                for (int k = 0; k < e.getCisternes().size(); ++k) {
                    Cisterna aux2 = new Cisterna(nouEstat.getCisternaX(k));        // Cisterna k
                    for (int h = 1; h < e.getCisternaX(k).getRecorregut().size()-1; ++h) {
                        if (aux.getPosicioRecorregut(j).getDia() != -1 && aux2.getPosicioRecorregut(h).getDia() != -1) {
                            if (nouEstat.swapPetitions(aux, aux2, j, h)) {
                                String S = "Intercanvi entre Cisterna " + i + " i Cisterna " + j + nouEstat.toString();
                                retVal.add(new Successor(S, nouEstat));
                            }
                        }
                    }
                }
            }
        }
        return retVal;
    }

}