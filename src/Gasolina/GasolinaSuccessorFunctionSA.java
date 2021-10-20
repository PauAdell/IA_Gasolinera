package Gasolina;

import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
