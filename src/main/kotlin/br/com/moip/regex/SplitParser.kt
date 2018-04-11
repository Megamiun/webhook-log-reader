package br.com.moip.regex

import br.com.moip.Parser
import br.com.moip.Report
import br.com.moip.common.MapReport
import java.io.InputStream

/**
 * Parse through the log using String splits.
 *
 * @author Gabryel Monteiro (Last Modified By: $Author: gabryel $)
 * @version $Id: v 1.1 Apr 06, 2018 gabryel Exp $
 */
class SplitParser : Parser {

    override fun parse(stream: InputStream): Report {
        val report = MapReport()
        stream.reader().forEachLine { line -> parseLine(line, report) }

        return report
    }

    /**
     * Parses lines separating it in sets of six tokens,
     * each corresponding to an entry. If the line has less
     * than six tokens, there will be no data there. If it
     * has between 6 and 11, it will have one item, 12 to 17
     * there will be two and so on. The count is done from back
     * to front because there are occurrences of lines that
     * have dirty starts, usually the first, but never the last.
     */
    private fun parseLine(line: String, report: MapReport) {
        val tokensBySet = 6
        val info = line.split("\"")

        if (info.size < tokensBySet)
            return

        for (base in info.size - 1 downTo 6 step tokensBySet) {
            report.add(info[base - 3], info[base - 1])
        }
    }
}