package br.com.moip.common

import br.com.moip.Count
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hobsoft.hamcrest.compose.ComposeMatchers.compose
import org.hobsoft.hamcrest.compose.ComposeMatchers.hasFeature
import org.junit.Test

class MapReportTest {
    
    @Test
    fun `given no items added in Report, when asked for first elements, return empty`() {
        val report = MapReport()
        
        assertThat(report.getFirst(3), `is`(empty()))
    }

    @Test
    fun `given no items added in Report, when asked for statusCode counts, return empty`() {
        val report = MapReport()

        assertThat(report.getStatusCounts(), `is`(empty()))
    }

    @Test
    fun `given only one of each url added in Report, when asked for first elements, return in input order`() {
        val report = MapReport()
        
        report.add("http://google.com", "404")
        report.add("http://moip.com.br", "300")
        report.add("http://feedly.com", "200")

        assertThat(report.getFirst(3), contains(
            countOf("http://google.com", 1),
            countOf("http://moip.com.br", 1),
            countOf("http://feedly.com", 1)
        ))
    }

    @Test
    fun `given only one of each status added in Report, when asked for statusCode counts, return in input order`() {
        val report = MapReport()

        report.add("http://google.com", "404")
        report.add("http://moip.com.br", "300")
        report.add("http://feedly.com", "200")

        assertThat(report.getStatusCounts(), contains(
            countOf("404", 1),
            countOf("300", 1),
            countOf("200", 1)
        ))
    }

    @Test
    fun `given three different urls added a different number of times in Report, when asked for first two elements, return first two elements in descending order`() {
        val report = MapReport()

        report.add("http://moip.com.br", "300")
        report.add("http://feedly.com", "418")
        report.add("http://feedly.com", "418")
        report.add("http://google.com", "300")
        report.add("http://google.com", "418")
        report.add("http://feedly.com", "418")
        report.add("http://feedly.com", "200")

        assertThat(report.getFirst(2), contains(
            countOf("http://feedly.com", 4),
            countOf("http://google.com", 2)
        ))
    }

    @Test
    fun `given three different status added a different number of times in Report, when asked for statusCode counts, return in descending order`() {
        val report = MapReport()

        report.add("http://moip.com.br", "300")
        report.add("http://feedly.com", "418")
        report.add("http://feedly.com", "418")
        report.add("http://google.com", "300")
        report.add("http://google.com", "418")
        report.add("http://feedly.com", "418")
        report.add("http://feedly.com", "200")

        assertThat(report.getStatusCounts(), contains(
            countOf("418", 4),
            countOf("300", 2),
            countOf("200", 1)
        ))
    }

    @Test
    fun `given an empty report and a single url, when asking for the first elements, return only the item`() {
        val report = MapReport()

        report.add("http://moip.com.br", "418")

        assertThat(report.getFirst(3), contains(
            countOf("http://moip.com.br", 1)
        ))
    }

    @Test
    fun `given an empty report and a single statusCode, when asking for the first elements, return only the item`() {
        val report = MapReport()

        report.add("http://moip.com.br", "418")

        assertThat(report.getStatusCounts(), contains(
            countOf("418", 1)
        ))
    }

    private fun countOf(id: String, count: Int): Matcher<Count> =
        compose(
            hasFeature("id", Count::id, `is`(id)),
            hasFeature("count", Count::count, `is`(count)))
}