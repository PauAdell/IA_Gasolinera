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
        Random random = new Random();

        boolean operadorAplicat = false;

        while (!operadorAplicat) {

            int rand = random.nextInt(3);

            if (rand == 0) {               // operador afegir
                int i = random.nextInt(e.getCisternes().size());            // cisterna i
                if (e.getFantasma().getRecorregut().size() > 0) {
                    int p = random.nextInt(e.getFantasma().getRecorregut().size());

                    GasolinaEstat nouEstat = new GasolinaEstat(e);

                    if (e.getCisternaX(i).getTancs() == 0) {
                        nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getCisternaX(i).getCentre());
                        if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                            String S = "AFEGIR DESTI";
                            S += "\n Cisterna " + i + " a posicio (" + nouEstat.getCisternaX(i).getCentre().getCoordX() + "," +  nouEstat.getCisternaX(i).getCentre().getCoordY() + ")" + nouEstat;
                            System.out.println(S);
                            retVal.add(new Successor(S, nouEstat));
                        }
                    } else {
                        String S = "AFEGIR DESTI";
                        S += "\n Cisterna " + i + " a posicio (" + nouEstat.getFantasma().getPosicioRecorregut(p).getCoordX() + "," +  nouEstat.getFantasma().getPosicioRecorregut(p).getCoordY() + ")" + nouEstat;
                        nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getFantasma().getPosicioRecorregut(p));
                        if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                            retVal.add(new Successor(S, nouEstat));
                            System.out.println(S);
                            operadorAplicat = true;
                        }
                    }

                }
            }

            else if (rand == 1) {                                       // operador swap entre cisternes
                int i = random.nextInt(e.getCisternes().size());
                int j = random.nextInt(e.getCisternes().size());

                int k = random.nextInt(e.getCisternaX(i).getRecorregut().size());
                int h = random.nextInt(e.getCisternaX(j).getRecorregut().size());

                if (e.getCisternaX(i).getPosicioRecorregut(k).getDia() != -1 && e.getCisternaX(j).getPosicioRecorregut(h).getDia() != -1) {
                    GasolinaEstat nouEstat = new GasolinaEstat(e);
                    Cisterna c1 = nouEstat.getCisternaX(i);
                    Cisterna c2 = nouEstat.getCisternaX(j);
                    nouEstat.swapPetitions(c1, c2, k, h);
                    if (c1.getDist() <= 640 && c2.getDist() <= 640) {
                        String S = "SWAP ENTRE CISTERNES";
                        S += "\n Cisterna "+ i + " en posicio del recorregut " + j + " amb Cisterna " + k + " en posicio del recorregut " + h + nouEstat;
                        retVal.add(new Successor(S, nouEstat));
                        System.out.println(nouEstat.getBenefici());
                        operadorAplicat = true;
                    }
                }
            }

            else {                                                              // operador swap entre cisterna i fantasma
                if (e.getFantasma().getRecorregut().size() > 0) {
                    int i = random.nextInt(e.getCisternes().size());
                    int pi = random.nextInt(e.getCisternaX(i).getRecorregut().size());

                    int pf = random.nextInt(e.getFantasma().getRecorregut().size());

                    if (e.getCisternaX(i).getPosicioRecorregut(pi).getDia() != -1) {

                        GasolinaEstat nouEstat = new GasolinaEstat(e);
                        Cisterna c1 = nouEstat.getCisternaX(i);
                        Cisterna c2 = nouEstat.getFantasma();
                        nouEstat.swapPetitions(c1, c2, pi, pf);
                        if (c1.getDist() <= 640) {
                            String S = "SWAP ENTRE CISTERNA I FATNASMA";
                            S += "\n Cisterna "+ i + " en posicio del recorregut " + pi + " amb Cisterna Fantasma en posicio del recorregut " + pf + nouEstat;
                            retVal.add(new Successor(S, nouEstat));
                            System.out.println(S);
                            operadorAplicat = true;
                        }
                    }
                }
            }

        }

        return retVal;

    }
}
