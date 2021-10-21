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

        //ArrayList<Cisterna> cist = new ArrayList<Cisterna>(e.getCisternes());
        //Cisterna fant = new Cisterna(e.getFantasma());

        for (int i = 0; i < e.getCisternes().size(); i++) {

            for (int j = 0; j < e.getFantasma().getRecorregut().size(); j++) {
                GasolinaEstat nouEstat = new GasolinaEstat(e);
                if (nouEstat.afegirDesti((Cisterna) nouEstat.getCisternes().get(i), nouEstat.getFantasma().getPosicioRecorregut(j))) {
                    String S = "Ruta Afegida de Cisterna " + i + " a Peticio " + j;
                    retVal.add(new Successor(S, nouEstat));
                    --j;
                    System.out.println("Afegicio " + i);
                }
            }
            /*
            int k = 0;          //index recorregut cisterna
            j = 0;              // index recorregut fantasma
            while (e.getFantasma().getRecorregut().size() != 0 && j < e.getFantasma().getRecorregut().size()-2) {
                Cisterna aux = new Cisterna((Cisterna) e.getCisternes().get(i));
                if (aux.getPosicioRecorregut(k).getDia() != -1 && k < aux.getRecorregut().size()-2) {
                    if (e.swapPetitions((Cisterna) e.getCisternes().get(i), e.getFantasma(), k+1, j+1)) {
                        GasolinaEstat nouEstat = new GasolinaEstat(e);
                        String S = "Intercanvi entre Cisterna " + i + " i Cisterna " + j;
                        retVal.add(new Successor(S, nouEstat));
                        ++k;
                    }
                }
                ++j;
            }*/
        }

        return retVal;

        //inacabadissim
    }

}