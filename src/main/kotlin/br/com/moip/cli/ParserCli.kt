package br.com.moip.cli

import br.com.moip.Parser
import br.com.moip.Report
import br.com.moip.common.MapReport
import br.com.moip.regex.RegexParser
import br.com.moip.regex.SplitParser
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import java.io.File
import java.io.FileInputStream
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = mainBody {
    ArgParser(args)
        .parseInto(::CliArguments)
        .run { execute(implementation, file, timed, benchmark) }
}

private fun execute(implementation: Parser, file: File?, timed: Boolean, benchmark: Boolean) {
    if (benchmark) {
        runBenchmark(file)
        return
    }

    var result: Report = MapReport()

    val time = measureTimeMillis { result = parse(file, implementation) }

    println(result.getFirst(3).joinToString(separator = System.lineSeparator()))
    println()
    println(result.getStatusCounts().joinToString(separator = System.lineSeparator()))

    if (timed) {
        println()
        println("Took $time ms")
    }
}

private fun runBenchmark(file: File?) {
    arrayOf(SplitParser(), RegexParser()).forEach { impl ->
        val time = measureTimeMillis {
            repeat(100) { parse(file, impl) }
        }

        println("${impl.javaClass.simpleName} - ${time}ms ")
    }
}

private fun parse(file: File?, implementation: Parser): Report =
    StreamGenerator.createStream(file).use { implementation.parse(it) }

private object StreamGenerator {
    fun createStream(file: File?) =
        file?.let { FileInputStream(it) }
            ?: this::class.java.getResourceAsStream("/log.txt")
}