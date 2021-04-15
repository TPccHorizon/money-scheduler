package money.scheduler;

import money.scheduler.calculators.MonthPaymentCalculator;

import java.time.LocalDate;
import java.util.List;

public class Scheduler implements MoneyScheduler {

    private static final List<Integer> DEFAULT_MONTHS = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

    public Scheduler() { }

    public List<Integer> calculateCurrentYearSchedule(int startingMonth, int startingYear, int paymentInterval) {
        validateInput(startingYear, startingMonth, paymentInterval);
        if (paymentInterval == 1) {
            // This means the schedule is the same for every year
            return DEFAULT_MONTHS;
        }
        int currentYearLastPayment = getCurrentYearLastPayment(startingMonth, startingYear, paymentInterval);
        return MonthPaymentCalculator.calculateMonthsOfPayment(currentYearLastPayment, paymentInterval);
    }

    public List<Integer> calculateCurrentYearSchedule(LocalDate startDate, int paymentInterval) {
        return calculateCurrentYearSchedule(startDate.getMonthValue(), startDate.getYear(), paymentInterval);
    }

    private int getCurrentYearLastPayment(int startingMonth, int startingYear, int paymentInterval) {
        int months = getTotalMonths(startingYear);
        int mod = months % paymentInterval;
        int initialYearLastPayment = 12 - mod;
        int currentYearLastPayment = initialYearLastPayment + startingMonth;
        if (currentYearLastPayment > 12) {
            currentYearLastPayment -= paymentInterval;
        }
        return currentYearLastPayment;
    }

    private void validateInput(int startingYear, int startingMonth, int paymentInterval) {
        if (startingMonth < 1 || startingMonth > 12) {
            throw new IllegalArgumentException("Invalid month " + startingMonth + ". It should range from 1 (inclusive) to 12 (inclusive)");
        }
        if (startingYear > LocalDate.now().getYear()) {
            throw new IllegalArgumentException(startingYear + " is in the future");
        }
        if (startingYear < 0) {
            throw new IllegalArgumentException("The Year cannot be negative");
        }
        if (paymentInterval > 12 || paymentInterval < 1) {
            throw new IllegalArgumentException("Invalid Payment Interval " + paymentInterval + ". It should range from 1 (inclusive) to 12 (inclusive)");
        }
    }

    private int getTotalMonths(int startingYear){
        int thisYear = LocalDate.now().getYear();
        return getYearDelta(thisYear, startingYear) * 12;
    }

    private int getYearDelta(int year1, int year2) {
        return Math.abs(year2 - year1);
    }
}
