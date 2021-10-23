package Gasolina;

import aima.search.framework.HeuristicFunction;

public class GasolinaHeuristic implements HeuristicFunction {
    public GasolinaHeuristic() { }

    public double getHeuristicValue(Object state) {

        GasolinaEstat estat = (GasolinaEstat) state;

        return -1 * estat.getBenefici();

    }
}