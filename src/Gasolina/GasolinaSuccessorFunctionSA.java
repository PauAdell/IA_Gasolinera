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

        //new Succesor("No fa res", e)
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
                            String S = "Centre Afegit: " + nouEstat.toString();
                            retVal.add(new Successor(S, nouEstat));
                            System.out.println(nouEstat.getBenefici());
                        }
                    } else {
                        nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getFantasma().getPosicioRecorregut(p));
                        if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                            String S = "Peticio Afegida: "; //+ nouEstat.toString();
                            retVal.add(new Successor(S, nouEstat));
                            System.out.println(nouEstat.getBenefici());
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

                //if (e.getCisternaX(i).getRecorregut().size() > 0 && e.getCisternaX(j).getRecorregut().size() > 0) {
                    if (e.getCisternaX(i).getPosicioRecorregut(k).getDia() != -1 && e.getCisternaX(j).getPosicioRecorregut(h).getDia() != -1) {
                        GasolinaEstat nouEstat = new GasolinaEstat(e);
                        Cisterna c1 = nouEstat.getCisternaX(i);
                        Cisterna c2 = nouEstat.getCisternaX(j);
                        nouEstat.swapPetitions(c1, c2, k, h);
                        if (c1.getDist() <= 640 && c2.getDist() <= 640) {
                            String S = "Swap: "; //+ nouEstat.toString();
                            retVal.add(new Successor(S, nouEstat));
                            System.out.println(nouEstat.getBenefici());
                            operadorAplicat = true;
                        }
                        //else retVal.add(new Successor("No fa res", e));
                    }
                    //else retVal.add(new Successor("No fa res", e));
                //}
            }

            else {                                                              // operador swap entre cisterna i fantasma
                if (e.getFantasma().getRecorregut().size() > 0) {
                    int i = random.nextInt(e.getCisternes().size());
                    int pi = random.nextInt(e.getCisternaX(i).getRecorregut().size());

                    int pf = random.nextInt(e.getFantasma().getRecorregut().size());

                    if (e.getCisternaX(i).getPosicioRecorregut(pi).getDia() != -1) {
                        //if (e.getCisternaX(i).getRecorregut().size() > 0) {
                        GasolinaEstat nouEstat = new GasolinaEstat(e);
                        Cisterna c1 = nouEstat.getCisternaX(i);
                        Cisterna c2 = nouEstat.getFantasma();
                        nouEstat.swapPetitions(c1, c2, pi, pf);
                        String S = "Swap: "; //+ nouEstat.toString();
                        if (c1.getDist() <= 640) {
                            retVal.add(new Successor(S, nouEstat));
                            System.out.println(nouEstat.getBenefici());
                            operadorAplicat = true;
                        } //else retVal.add(new Successor("No fa res", e));
                        //}
                        //else retVal.add(new Successor("No fa res", e));
                    }
                }
            }

        }

        return retVal;

    }
}
