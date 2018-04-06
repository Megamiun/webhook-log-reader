package br.com.moip

import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hobsoft.hamcrest.compose.ComposeMatchers.compose
import org.hobsoft.hamcrest.compose.ComposeMatchers.hasFeature
import org.junit.Test

abstract class ParserTest {

    abstract fun getImplementation(): Parser

    @Test
    fun `given an empty stream, when asked for first and status counts, then both should return empty`() {
        val report = getImplementation().parse(javaClass.getResourceAsStream("/empty.txt"))

        assertThat(report.getFirst(5), `is`(empty()))
        assertThat(report.getStatusCounts(), `is`(empty()))
    }

    @Test
    fun `given a sample stream, when asked for first three, then should return three more frequent urls`() {
        val report = getImplementation().parse(javaClass.getResourceAsStream("/sample.txt"))

        assertThat(report.getFirst(3), contains(
            countOf("https://eagerhaystack.com", 750),
            countOf("https://surrealostrich.com.br", 734),
            countOf("https://grimpottery.net.br", 732)
        ))
    }

    @Test
    fun `given a sample stream, when asked for status count, then should return all status ordered`() {
        val report = getImplementation().parse(javaClass.getResourceAsStream("/sample.txt"))

        assertThat(report.getStatusCounts(), contains(
            countOf("404", 1474),
            countOf("503", 1451),
            countOf("400", 1440),
            countOf("500", 1428),
            countOf("200", 1417),
            countOf("201", 1402),
            countOf("204", 1388)
        ))
    }

    private fun countOf(id: String, count: Int): Matcher<Count> =
        compose(
            hasFeature("id", Count::id, `is`(id)),
            hasFeature("count", Count::count, `is`(count)))
}