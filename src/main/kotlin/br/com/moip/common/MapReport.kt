package br.com.moip.common

import br.com.moip.Count
import br.com.moip.Report
import kotlin.collections.Map.Entry

class MapReport : Report {

    private val urlMap = mutableMapOf<String, Int>()

    private val statusCodeMap = mutableMapOf<String, Int>()

    override fun getFirst(n: Int): List<Count> =
        urlMap.sortedDescending()
            .take(n)
            .mapToCount()

    override fun getStatusCounts(): List<Count> =
        statusCodeMap.sortedDescending()
            .mapToCount()

    override fun add(url: String, statusCode: String) {
        statusCodeMap.compute(statusCode, ::increment)
        urlMap.compute(url, ::increment)
    }

    /**
     * @receiver Map of number of word occurrences
     * @return Ordered list of occurrences
     */
    private fun Map<String, Int>.sortedDescending() = entries.sortedByDescending { entry -> entry.value }

    /**
     * @receiver List of entries
     * @return A list of Counts of occurrences
     */
    private fun List<Entry<String, Int>>.mapToCount() = map { entry -> Count(entry.key, entry.value) }

    /**
     * @param key Added just so we could use this function reference above
     * @param old Previous value of key
     * @return 1 in case of non existent value, otherwise the [old] plus 1
     */
    private fun increment(key: String, old: Int?) = old?.inc() ?: 1
}