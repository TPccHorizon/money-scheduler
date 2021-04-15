package money.scheduler.model;

public class Month {
    private int month;
    private boolean hasPayment;

    public Month(int month, boolean hasPayment) {
        this.month = month;
        this.hasPayment = hasPayment;
    }

    public int getMonth() {
        return month;
    }

    public boolean getHasPayment() {
        return hasPayment;
    }
}
