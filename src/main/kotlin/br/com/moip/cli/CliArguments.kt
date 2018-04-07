package br.com.moip.cli;

import br.com.moip.regex.RegexParser
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import java.lang.System.getProperty

class CliArguments(parser: ArgParser) {
    val timed by parser.flagging(
        "-t", "--timed",
        help = "print execution time"
    ).default(false)

    val filePath by parser.positional(
        name = "FILEPATH",
        help = "file path"
    ).default("""${getProperty("user.dir")}/src/main/resources/log.txt""")

    val implementation by parser.mapping(
        "--regex" to RegexParser(),
        help = "file path"
    ).default(RegexParser())
}
