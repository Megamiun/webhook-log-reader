package br.com.moip

import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.hobsoft.hamcrest.compose.ComposeMatchers.compose
import org.hobsoft.hamcrest.compose.ComposeMatchers.hasFeature
import org.junit.Test

abstract class ParserTest {

    abstract fun getImplementation(): Parser?

    @Test
    fun `given an empty stream, when asked for first and status counts, then both should return empty`() {
        val report = getImplementation()?.parse(javaClass.getResourceAsStream("/empty.txt"))!!

        assertThat(report.getFirst(5), `is`(empty()))
        assertThat(report.getStatusCounts(), `is`(empty()))
    }

    @Test
    fun `given a sample stream, when asked for first three, then should return three more frequent urls`() {
        val report = getImplementation()?.parse(javaClass.getResourceAsStream("/sample.txt"))!!

        TODO("Discover real return of sample")

        assertThat(report.getFirst(3), contains(
            countOf("https://woodenoyster.com.br", 100),
            countOf("https://grotesquemoon.de", 99),
            countOf("https://notoriouslonesome.com", 90)
        ))
    }

    @Test
    fun `given a sample stream, when asked for status count, then should return all status ordered`() {
        val report = getImplementation()?.parse(javaClass.getResourceAsStream("/sample.txt"))!!

        TODO("Discover real return of sample")

        assertThat(report.getStatusCounts(), contains(
            countOf("200", 100),
            countOf("100", 99)
        ))
    }

    private fun countOf(id: String, count: Int): Matcher<Count> =
        compose(
            hasFeature(Count::id, `is`(id)),
            hasFeature(Count::count, `is`(count)))
}