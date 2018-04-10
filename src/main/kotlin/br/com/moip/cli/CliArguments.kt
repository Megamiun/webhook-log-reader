package br.com.moip.cli;

import br.com.moip.regex.RegexParser
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File
import java.io.FileInputStream

class CliArguments(parser: ArgParser) {
    val timed by parser.flagging(
        "-t", "--timed",
        help = "print execution time"
    ).default(false)

    val file by parser.positional(
        name = "FILEPATH",
        help = "file path"
    ) {
        FileInputStream(File(this))
    }.default(javaClass.getResourceAsStream("/log.txt"))

    val implementation by parser.mapping(
        "--regex" to RegexParser(),
        help = "file path"
    ).default(RegexParser())
}
