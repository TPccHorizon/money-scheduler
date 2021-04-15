package money.scheduler.calculators;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MonthPaymentCalculator {

    /**
     * Returns a List containing the month numbers (e.g. 5 for May) in which a payment occurs.
     * @param monthOfLastPayment : The last month in a year where a payment occurs.
     * @param paymentInterval : The interval (in months) every which a payment takes place.
     * */
    public static List<Integer> calculateMonthsOfPayment(int monthOfLastPayment, int paymentInterval) {
        List<Integer> paymentMonths = new LinkedList<>();
        paymentMonths.add(monthOfLastPayment);
        int currentMonth = monthOfLastPayment;
        // go from last month backwards
        do {
            currentMonth -= paymentInterval;
            paymentMonths.add(currentMonth);
        } while (currentMonth > paymentInterval);
        Collections.reverse(paymentMonths);
        return paymentMonths;
    }

}
