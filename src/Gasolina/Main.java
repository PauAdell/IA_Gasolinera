package Gasolina;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        int seed = 0;
        int nGasos = 0;
        int nCentres = 0;
        int mult = 1;
        int heu = 1;

        Scanner in = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("------------ PRACTICA DE CERCA LOCAL ------------");
        System.out.println();

        System.out.println("Per usar Hill-Clibming insereix '1'");
        System.out.println("Per usar Simulated Annealing insereix '2'");
        System.out.println("Per acabar insereix '0'");

        int ops = -1;
        while (ops != 0) {

            ops = in.nextInt();
            if (ops == 1) {

                System.out.println("Insereix el nombre de Centres de Distribucio, de Gasolineres i la multiplicitat d'aquestes");
                nCentres = in.nextInt();
                nGasos = in.nextInt();
                mult = in.nextInt();

                System.out.println("Per obtenir una seed random insereix '-1', altrament insereix la seed ");
                ops = in.nextInt();

                if (ops == -1) seed = rand.nextInt();
                else seed = ops;

                System.out.println("Per generar la solucio inicial 2 insereix 2, altrament prem qualssevol boto per obtenir la solucio inicial 1");
                int solIni = in.nextInt();


                System.out.println("Per usar l'Heuristic 1 insereix 1, i per usar l'Heuristic 2 insereix '2'");
                ops = in.nextInt();
                while (ops != 1 && ops != 2) {
                    if (ops == 1) heu = 1;
                    else if (ops == 2) heu = 2;
                    else System.out.println("Insereix 1 o 2");
                }

                long start = System.currentTimeMillis();

                Gasolineras gaso = new Gasolineras(nGasos, seed);
                CentrosDistribucion centres = new CentrosDistribucion(nCentres, mult, seed);
                GasolinaEstat e = new GasolinaEstat(gaso, centres);
                GasolinaHillClimbingSearch(e, heu);
                if (solIni == 2) e.generarEstatSolucio1();

                long finish = System.currentTimeMillis();

                long temps = finish - start;

                System.out.println("Temps trigat en generar la solucio i executar l'algorisme: " + temps + "ms");

            }

            else if (ops == 2) {

                System.out.println("Insereix el nombre de Centres de Distribucio, de Gasolineres i la multiplicitat d'aquestes");
                nCentres = in.nextInt();
                nGasos = in.nextInt();
                mult = in.nextInt();

                System.out.println("Per obtenir una seed random insereix 'rand', altrament insereix la seed ");
                int s = in.nextInt();

                if (s == -1) seed = rand.nextInt();
                else seed = s;

                System.out.println("Per generar la solucio inicial 2 insereix 2, altrament prem qualssevol boto per obtenir la solucio inicial 1");
                int solIni = in.nextInt();

                System.out.println("Per usar l'Heuristic 1 insereix 1, i per usar l'Heuristic 2 insereix '2'");
                ops = in.nextInt();
                while (ops != 1 && ops != 2) {
                    if (ops == 1) heu = 1;
                    else if (ops == 2) heu = 2;
                    else System.out.println("Insereix 1 o 2");
                }

                long start = System.currentTimeMillis();

                Gasolineras gaso = new Gasolineras(nGasos, seed);
                CentrosDistribucion centres = new CentrosDistribucion(nCentres, mult, seed);
                GasolinaEstat e = new GasolinaEstat(gaso, centres);
                if (solIni == 2) e.generarEstatSolucio1();
                GasolinaSimulatedAnnealingSearch(e, heu);

                long finish = System.currentTimeMillis();

                long temps = finish - start;

                System.out.println("Temps trigat en generar la solucio i executar l'algorisme: " + temps + "ms");
            }
        }
    }

    // Funcions bàsiques necessaries per correr l'algorisme POTSER NECESSITEN ALGUNS CANVIS

    private static void GasolinaHillClimbingSearch(GasolinaEstat e, int heuristic) {
        System.out.println("\nGasolina HillClimbing  -->");

        try {
            Problem problem;
            if (heuristic == 1) problem = new Problem(e, new GasolinaSuccessorFunction(), new GasolinatGoalTest(), new GasolinaHeuristic());
            else problem = new Problem(e, new GasolinaSuccessorFunction(), new GasolinatGoalTest(), new GasolinaHeuristic2());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem, search);
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private static void GasolinaSimulatedAnnealingSearch(GasolinaEstat e, int heuristic) {
        System.out.println("\nGasolina Simulated Annealing  -->");

        try {
            Problem problem;
            if (heuristic == 1) problem = new Problem(e, new GasolinaSuccessorFunctionSA(), new GasolinatGoalTest(), new GasolinaHeuristic());
            else problem = new Problem(e, new GasolinaSuccessorFunctionSA(), new GasolinatGoalTest(), new GasolinaHeuristic2());
            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(2000, 100, 5, 0.001D);
            SearchAgent agent = new SearchAgent(problem, search);
            System.out.println();
            //printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

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