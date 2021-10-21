package Gasolina;

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
        Random myRandom = new Random();

        //int i, j;
        //i = myRandom.nextInt(e.getNPeticions());

        return retVal;

        //inacabadissim x2
    }
}
