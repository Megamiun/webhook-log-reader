package br.com.moip.cli;

import br.com.moip.regex.RegexParser
import br.com.moip.regex.SplitParser
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.io.File

class CliArguments(parser: ArgParser) {
    val timed by parser.flagging(
        "-t", "--timed",
        help = "print execution time"
    ).default(false)

    val benchmark by parser.flagging(
        "-b", "--benchmark",
        help = "run all parsers to test performance, 100 times each"
    ).default(false)

    val file: File? by parser.positional(
        name = "FILEPATH",
        help = "file path"
    ) { File(this) }
        .default { null }

    val implementation by parser.mapping(
        "--split" to SplitParser(),
        "--regex" to RegexParser(),
        help = "implementation choice"
    ).default(SplitParser())
}
