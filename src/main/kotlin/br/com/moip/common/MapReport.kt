package br.com.moip.common

import br.com.moip.Count
import br.com.moip.Report

class MapReport : Report {

    private val urlMap = mutableMapOf<String, Int>()

    private val statusCodeMap = mutableMapOf<String, Int>()

    override fun getFirst(n: Int): List<Count> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStatusCounts(): List<Count> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(url: String, statusCode: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}