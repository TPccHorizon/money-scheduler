package money.scheduler.model;

import java.util.LinkedList;
import java.util.List;

public class PaymentYear {
    private int year;
    private List<Month> months;

    public PaymentYear(int year, List<Month> months) {
        this.year = year;
        this.months = months;
    }

    public static PaymentYear of(int year, List<Integer> months) {
        List<Month> rawMonths = new LinkedList<>();
        for (int i=1; i<=12; i++) {
            rawMonths.add(new Month(i, months.contains(i)));
        }
        return new PaymentYear(year, rawMonths);
    }

    public int getYear() {
        return year;
    }

    public List<Month> getMonths() {
        return months;
    }
}
