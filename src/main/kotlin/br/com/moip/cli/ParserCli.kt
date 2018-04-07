package br.com.moip.cli

import br.com.moip.Parser
import br.com.moip.Report
import br.com.moip.common.MapReport
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import java.io.File
import java.io.FileInputStream
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = mainBody {
    ArgParser(args)
        .parseInto(::CliArguments)
        .run { execute(implementation, filePath, timed) }
}

private fun execute(implementation: Parser, filePath: String, timed: Boolean) {
    var result: Report = MapReport()

    val time = measureTimeMillis {
        result = implementation.parse(FileInputStream(File(filePath)))
    }

    println(result.getFirst(3).joinToString(separator = System.lineSeparator()))
    println()
    println(result.getStatusCounts().joinToString(separator = System.lineSeparator()))

    if (timed) {
        println()
        println("Took $time ms")
    }
}

