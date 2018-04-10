package br.com.moip.cli

import br.com.moip.Parser
import br.com.moip.Report
import br.com.moip.common.MapReport
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import java.io.InputStream
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = mainBody {
    ArgParser(args)
        .parseInto(::CliArguments)
        .run { execute(implementation, file, timed) }
}

private fun execute(implementation: Parser, stream: InputStream, timed: Boolean) {
    var result: Report = MapReport()

    val time = measureTimeMillis {
        result = implementation.parse(stream)
    }

    println(result.getFirst(3).joinToString(separator = System.lineSeparator()))
    println()
    println(result.getStatusCounts().joinToString(separator = System.lineSeparator()))

    if (timed) {
        println()
        println("Took $time ms")
    }
}

