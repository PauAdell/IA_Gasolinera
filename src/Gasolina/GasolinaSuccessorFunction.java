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

        for (int i = 0; i < e.getCisternes().size(); ++i) {

            /*
            if (e.getCisternaX(i).getTancs() == 0) {
                GasolinaEstat nouEstat = new GasolinaEstat(e);
                nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getCisternaX(i).getCentre());
                if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                    String S = "Centre Afegit: " + nouEstat.toString();
                    retVal.add(new Successor(S, nouEstat));
                }
            } else {
                for (int j = 0; j < e.getFantasma().getRecorregut().size(); ++j) {
                    GasolinaEstat nouEstat = new GasolinaEstat(e);
                    nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getFantasma().getPosicioRecorregut(j));
                    if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                        String S = "Peticio Afegida: " + nouEstat.toString();
                        retVal.add(new Successor(S, nouEstat));
                    }
                }
            }

             */

            for (int j = 0; j < e.getFantasma().getRecorregut().size(); ++j) {


                    for (int m = 0; m < 2; ++m) {
                        if (m == 0) {
                            GasolinaEstat nouEstat = new GasolinaEstat(e);
                            nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getCisternaX(i).getCentre());
                            if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                                String S = "Peticio Afegida: " + nouEstat.toString();
                                retVal.add(new Successor(S, nouEstat));
                            }
                        } else {
                            GasolinaEstat nouEstat = new GasolinaEstat(e);
                            nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getFantasma().getPosicioRecorregut(j));
                            if (nouEstat.getCisternaX(i).getDist() <= 640 && nouEstat.getCisternaX(i).getViatges() <= 5) {
                                String S = "Peticio Afegida: " + nouEstat.toString();
                                retVal.add(new Successor(S, nouEstat));
                            }
                        }
                    }
            }



            Cisterna aux = new Cisterna(e.getCisternaX(i));        // Cisterna i

            for (int j = 1; j < e.getCisternaX(i).getRecorregut().size()-1; ++j) {

                if (aux.getPosicioRecorregut(j).getDia() != -1) {
                    for (int k = i+1; k < e.getCisternes().size(); ++k) {
                        Cisterna aux2 = new Cisterna(e.getCisternaX(k));        // Cisterna k

                        for (int h = 1; h < e.getCisternaX(k).getRecorregut().size() - 1; ++h) {
                            if (aux2.getPosicioRecorregut(h).getDia() != -1) {
                                GasolinaEstat nouEstat = new GasolinaEstat(e);
                                Cisterna c1 = nouEstat.getCisternaX(i);
                                Cisterna c2 = nouEstat.getCisternaX(k);
                                nouEstat.swapPetitions(c1, c2, j, h);
                                String S = "Swap: " + nouEstat.toString();
                                if (c1.getDist() <= 640 && c2.getDist() <= 640)
                                    retVal.add(new Successor(S, nouEstat));
                            }
                        }
                    }

                    for (int k = 0; k < e.getFantasma().getRecorregut().size(); ++k) {
                        GasolinaEstat nouEstat = new GasolinaEstat(e);
                        Cisterna c1 = nouEstat.getCisternaX(i);
                        Cisterna c2 = nouEstat.getFantasma();
                        nouEstat.swapPetitions(c1, c2, j, k);
                        String S = "Swap: " + nouEstat.toString();
                        if (c1.getDist() <= 640)
                            retVal.add(new Successor(S, nouEstat));
                    }

                }
            }

        }
        return retVal;
    }

}