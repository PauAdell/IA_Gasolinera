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
        System.out.println("-----------EstatInicial------------");
        for (int k = 0; k < e.getFantasma().getRecorregut().size(); ++k ) {
            System.out.println("Pos pet: " + k + " " + e.getFantasma().getPosicioRecorregut(k).getCoordX() + "," + e.getFantasma().getPosicioRecorregut(k).getCoordY());
        }



        for (int i = 0; i < e.getCisternes().size(); i++) {
            for (int j = 0; j < e.getFantasma().getRecorregut().size(); j++) {
                    GasolinaEstat nouEstat = new GasolinaEstat(e);
                    System.out.println("-----------NouEstat------------");
                    for (int k = 0; k < nouEstat.getFantasma().getRecorregut().size(); ++k ) {
                        System.out.println("Pos pet: " + k + " " + nouEstat.getFantasma().getPosicioRecorregut(k).getCoordX() + "," + nouEstat.getFantasma().getPosicioRecorregut(k).getCoordY());
                    }
                    System.out.println("Posicio recorregut: " + j + " Coords de peticio: " + nouEstat.getFantasma().getPosicioRecorregut(j).getCoordX() + ',' + nouEstat.getFantasma().getPosicioRecorregut(j).getCoordY() + " Dia pet: " + nouEstat.getFantasma().getPosicioRecorregut(j).getDia());
                    if (nouEstat.afegirDesti(nouEstat.getCisternaX(i), nouEstat.getFantasma().getPosicioRecorregut(j))) {
                        String S = "Ruta Afegida de Cisterna " + i + " a Peticio " + j;
                        retVal.add(new Successor(S, nouEstat));
                        //--j;
                        System.out.println("Peticions restants: " + nouEstat.getFantasma().getRecorregut().size());
                        System.out.println("Cisterna: " + i + " Intenta afegir peticio a pos " + j + " de recorregut fantasma" );
                        for (int k = 0; k < nouEstat.getFantasma().getRecorregut().size(); ++k ) {
                            System.out.println("Pos pet: " + k + " " + nouEstat.getFantasma().getPosicioRecorregut(k).getCoordX() + "," + nouEstat.getFantasma().getPosicioRecorregut(k).getCoordY() + " dia pet: " + nouEstat.getFantasma().getPosicioRecorregut(k).getDia());
                        }
                    }
            }

            /*
            int k = 0;          //index recorregut cisterna
            j = 0;              // index recorregut fantasma
            while (e.getFantasma().getRecorregut().size() != 0 && j < e.getFantasma().getRecorregut().size()-2) {
                Cisterna aux = new Cisterna((Cisterna) e.getCisternes().get(i));
                if (aux.getPosicioRecorregut(k).getDia() != -1 && k < aux.getRecorregut().size()-2) {
                    if (e.swapPetitions((Cisterna) e.getCisternes().get(i), e.getFantasma(), k+1, j+1)) {
                        GasolinaEstat nouEstat = new GasolinaEstat(e);
                        String S = "Intercanvi entre Cisterna " + i + " i Cisterna " + j;
                        retVal.add(new Successor(S, nouEstat));
                        ++k;
                    }
                }
                ++j;
            }*/
        }

        return retVal;

        //inacabadissim
    }

}