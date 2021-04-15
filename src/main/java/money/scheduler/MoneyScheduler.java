package money.scheduler;

import java.time.LocalDate;
import java.util.List;

public interface MoneyScheduler {

    List<Integer> calculateCurrentYearSchedule(int startingMonth, int startingYear, int paymentInterval);

    List<Integer> calculateCurrentYearSchedule(LocalDate startDate, int paymentInterval);
}
