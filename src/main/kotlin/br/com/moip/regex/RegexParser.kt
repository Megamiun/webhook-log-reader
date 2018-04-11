package br.com.moip.regex

import br.com.moip.Parser
import br.com.moip.Report
import br.com.moip.common.MapReport
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Pattern

/**
 * Parse through the log using regexps.
 *
 * @author Gabryel Monteiro (Last Modified By: $Author: gabryel $)
 * @version $Id: v 1.1 Apr 06, 2018 gabryel Exp $
 */
class RegexParser : Parser {

    // This pattern finds the shortest possible version of the string in the line.
    // It needs to be the shortest so when we have more than one occurrence by line,
    // we don't get a version that takes the entire line.
    private val lineRegex = Pattern.compile("""request_to="(.*?)".*? response_status="(.*?)"""")
    
    override fun parse(stream: InputStream): Report {
        val report = MapReport()
        stream.getReader().forEachLine { line -> parseLine(line, report) }
        
        return report
    }

    private fun InputStream.getReader() = BufferedReader(InputStreamReader(this))

    private fun parseLine(line: String, report: MapReport) {
        val matcher = lineRegex.matcher(line)
        
        while (matcher.find()) {
            val result = matcher.toMatchResult()
            val url = result.group(1)
            val status = result.group(2)

            report.add(url, status)
        }
    }
}