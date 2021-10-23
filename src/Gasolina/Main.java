package Gasolina;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class Main {

    public static void main(String[] args) {
        GasolinaEstat estat = new GasolinaEstat(20, 1234, 10, 2);
        estat.generarEstatSolucio1();
        //estat.generarEstatSolucio2();
        //estat.imprimirEstat();
        //GasolinaHillClimbingSearch(estat);
        estat.imprimirEstat();
        //GasolinaSimulatedAnnealingSearch(estat);
    }

    // Funcions bàsiques necessaries per correr l'algorisme POTSER NECESSITEN ALGUNS CANVIS

    private static void GasolinaHillClimbingSearch(GasolinaEstat e) {
        System.out.println("\nGasolina HillClimbing  -->");

        try {
            Problem problem = new Problem(e, new GasolinaSuccessorFunction(), new GasolinatGoalTest(), new GasolinaHeuristic());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private static void GasolinaSimulatedAnnealingSearch(GasolinaEstat e) {
        System.out.println("\nGasolina Simulated Annealing  -->");

        try {
            Problem problem = new Problem(e, new GasolinaSuccessorFunctionSA(), new GasolinatGoalTest(), new GasolinaHeuristic());
            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(2000, 100, 5, 0.001D);
            SearchAgent agent = new SearchAgent(problem, search);
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    // Necessari perque funcionin be les funcions d'abans

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();

        while(keys.hasNext()) {
            String key = (String)keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for(int i = 0; i < actions.size(); ++i) {
            String action = (String)actions.get(i);
            System.out.println(action);
        }

    }
}