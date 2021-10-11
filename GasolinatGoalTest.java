package IA.Gasolina;
import aima.search.framwork.GoalTest;

public class GasolinaGoalTest implements GoalTest {

    public boolean isGoalState(Object state) {

        Gasolina estat = (Gasolina) aState;
        return (estat.isGoalState());

    }

}