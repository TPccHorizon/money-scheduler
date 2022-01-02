package money.scheduler

import money.scheduler.model.APRIL
import money.scheduler.model.AUGUST
import money.scheduler.model.DECEMBER
import money.scheduler.model.FEBRUARY
import money.scheduler.model.JANUARY
import money.scheduler.model.JULY
import money.scheduler.model.JUNE
import money.scheduler.model.MARCH
import money.scheduler.model.MAY
import money.scheduler.model.NOVEMBER
import money.scheduler.model.OCTOBER
import money.scheduler.model.SEPTEMBER
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CalendarMonthTest {

    @Test
    fun `months should be numbered naturally`() {
        assertEquals(1, JANUARY)
        assertEquals(2, FEBRUARY)
        assertEquals(3, MARCH)
        assertEquals(4, APRIL)
        assertEquals(5, MAY)
        assertEquals(6, JUNE)
        assertEquals(7, JULY)
        assertEquals(8, AUGUST)
        assertEquals(9, SEPTEMBER)
        assertEquals(10, OCTOBER)
        assertEquals(11, NOVEMBER)
        assertEquals(12, DECEMBER)
    }
}