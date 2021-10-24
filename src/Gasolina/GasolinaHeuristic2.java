package Gasolina;

import aima.search.framework.HeuristicFunction;

public class GasolinaHeuristic2 implements HeuristicFunction {

    public GasolinaHeuristic2() { }

    public double getHeuristicValue(Object state) {
        GasolinaEstat estat = (GasolinaEstat) state;

        return -1 * estat.getBenefici();
    }
}
