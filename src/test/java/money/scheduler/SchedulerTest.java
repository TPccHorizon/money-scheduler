package money.scheduler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SchedulerTest {

    @Test
    void testSchedule() {
        // given
        Scheduler scheduler = new Scheduler();
        int startingMonth = 1;
        int startingYear = 2011;
        int paymentInterval = 1;
        // when
        List<Integer> expectedPayments = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        // then
        Assertions.assertEquals(expectedPayments, scheduler.calculateCurrentYearSchedule(startingMonth, startingYear, paymentInterval));
    }

    @Test
    void test() {
        // given
        Scheduler scheduler = new Scheduler();
        int startingMonth = 1;
        int startingYear = 2021;
        int paymentInterval = 2;
        // when
        List<Integer> actualPayments = scheduler.calculateCurrentYearSchedule(startingMonth, startingYear, paymentInterval);
        List<Integer> expectedPayments = List.of(1, 3, 5, 7, 9, 11);
        // then
        Assertions.assertEquals(expectedPayments, actualPayments);
    }
}
