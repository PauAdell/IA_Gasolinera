package Gasolina;

import aima.search.framework.SuccessorFunction;
import java.util.List;
import java.util.ArrayList;

public class GasolinaSuccessorFunction implements SuccessorFunction {
    public GasolinaSuccessorFunction(){ }

    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();
        GasolinaEstat e = (GasolinaEstat) aState;
        GasolinaHeuristic heu = new GasolinaHeuristic();

        return retVal;

        //inacabadissim
    }

}