package trunk.Control;
import trunk.Model.*;


/**
 * With this simulation, the user shall indicate a per annum percentage that each
 * equity will decrease. This class implements the FutureSimulation interface, and
 * acts as a concrete strategy within the Strategy design pattern.
 */

public class BearMarket  implements FutureSimulation {

    /**
     * The method to run the simulation.
     * @param portfolio : A Portfolio object that will run the simulation.
     * @param percentage : A double that represents a per annum percentage.
     * @param interval : An int that represents a period of time.
     * @param steps : An int that represents the number of intervals to run.
     * @return : A double representing the equity price after the simulation.
     */
    public double[] simulate(Portfolio portfolio, double percentage, int interval, int steps) {

        steps += 1;
        double[] finalValues = new double[steps];
        double initValue = 0;
        double finalValue;
        double cashValue = 0;
        double totChange = 0;

        for (int i = 0; i < portfolio.equityList1.size(); i++) {
            Equity e = portfolio.equityList1.get(i);
            initValue += e.shares * e.acquisitionPrice;
        }


        for (int i = 0; i < portfolio.cashAccList.size(); i++) {
            CashAccount ca = portfolio.cashAccList.get(i);
            cashValue += ca.balance;
        }

        initValue += cashValue;
        finalValues[0] = initValue;
        double sub = initValue * (percentage / 100);

        for (int j = 1; j  < steps; j++) {
            if (finalValues[j - 1] - sub >= 0)
                finalValues[j] = finalValues[j - 1] - sub;
            else finalValues[j] = 0;
        }

        return finalValues;

    }

    @Override
    public double[] simulate(Equity e, double percentage, int interval, int steps) {
        double[] vals = new double[steps];
        double initValue = e.shares * e.acquisitionPrice;
        vals[0] = initValue;

        for (int i = 1; i < steps; i++) {
            initValue -= initValue * (percentage / 100);
            if (initValue >= 0)
                vals[i] = initValue;
            else vals[i] = 0;
        }

        return vals;
    }
}
