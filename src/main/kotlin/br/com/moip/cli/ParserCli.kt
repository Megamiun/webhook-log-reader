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
import java.io.InputStream
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = mainBody {
    ArgParser(args)
        .parseInto(::CliArguments)
        .run { implementation.execute(file, timed, benchmark) }
}

private fun Parser.execute(file: File?, timed: Boolean, benchmark: Boolean) {
    if (benchmark) {
        runBenchmark(file)
        return
    }

    var result: Report = MapReport()

    val time = measureTimeMillis { result = parse(file) }

    println(result.getFirst(3).joinToString(separator = System.lineSeparator()))
    println()
    println(result.getStatusCounts().joinToString(separator = System.lineSeparator()))

    if (timed) printTime(this, time)
}

/**
 * Runs a benchmark and prints the time each implementation took.
 *
 * @param file Nullable file to be parsed
 */
private fun runBenchmark(file: File?) {
    arrayOf(SplitParser(), RegexParser()).forEach { impl ->
        val time = measureTimeMillis {
            repeat(100) { impl.parse(file) }
        }

        println("${impl.javaClass.simpleName} - ${time}ms ")
    }
}

/**
 * Prints time that the implementation took to parse the file.
 *
 * @param implementation Implementation that will be registered
 * @param time Time in milliseconds to finish execution
 */
private fun printTime(implementation: Parser, time: Long): Unit =
    println("${implementation.javaClass.simpleName} - ${time}ms ")

/**
 * Generates a stream to the file and parse him, creating a
 * default stream if necessary.
 *
 * @receiver Parser that will be responsible for the parsing
 * @param file Nullable file
 *
 * @return Report with frequencies.
 */
private fun Parser.parse(file: File?): Report =
    StreamGenerator.createStream(file).use { parse(it) }

/**
 * Object create to load /log.txt stream. It is used because I
 * can only get a class object to getResourceAsStream from
 * inside a declared class.
 */
private object StreamGenerator {
    fun createStream(file: File?): InputStream =
        file?.let { FileInputStream(it) }
            ?: this::class.java.getResourceAsStream("/log.txt")
}