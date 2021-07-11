package money.scheduler

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

internal class SchedulerTest {
    internal class MonthsArgumentsProvider : ArgumentsProvider {
        @Throws(Exception::class)
        override fun provideArguments(context: ExtensionContext): Stream<out Arguments?> {
            return Stream.of(
                    Arguments.of(1, 2011, 1, java.util.List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
                    Arguments.of(1, 2021, 2, java.util.List.of(1, 3, 5, 7, 9, 11))
            )
        }
    }

    @ParameterizedTest
    @ArgumentsSource(MonthsArgumentsProvider::class)
    fun testSchedule(startingMonth: Int, startingYear: Int, paymentInterval: Int, expected: List<Int?>?) {
        // given
        val scheduler = Scheduler()
        // then
        Assertions.assertEquals(expected, scheduler.calculateCurrentYearSchedule(startingMonth, startingYear, paymentInterval))
    }
}