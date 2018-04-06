package br.com.moip.regex

import br.com.moip.Parser
import br.com.moip.Report
import br.com.moip.common.MapReport
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Pattern

/**
 * @author Gabryel Monteiro (Last Modified By: $Author: gabryel $)
 * @version $Id: v 1.1 Apr 06, 2018 gabryel Exp $
 */
class RegexParser : Parser {
    
    private val lineRegex = Pattern.compile("""request_to="(.*?)".*? response_status="(.*?)"""")
    
    override fun parse(stream: InputStream): Report {
        val report = MapReport()
        val parser = { line: String -> parseLine(line, report) }

        stream.getReader().useLines { it.forEach(parser) }
        
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