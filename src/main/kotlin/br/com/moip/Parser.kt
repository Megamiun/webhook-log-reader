package br.com.moip

import java.io.InputStream

/**
 * A parser for the log stream
 */
interface Parser {
    fun parse(stream: InputStream): Report
}

/**
 * Aggregation of log information
 */
interface Report {
    /**
     * @param n Quantity of items to fetch
     * @return the most common [n] urls of the log
     */
    fun getFirst(n: Int): List<Count>

    /**
     * @return a list of all status codes delivered, ordered by frequency
     */
    fun getStatusCounts(): List<Count>
}

/**
 * Grouping of an item identification with count of occurrences
 */
interface Count {
    val id: String

    val count: Int
}