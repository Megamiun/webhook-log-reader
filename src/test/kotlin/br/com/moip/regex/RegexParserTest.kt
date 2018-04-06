package br.com.moip.regex

import br.com.moip.ParserTest

/**
 * @author Gabryel Monteiro (Last Modified By: $Author: gabryel $)
 * @version $Id: v 1.1 Apr 06, 2018 gabryel Exp $
 */
class RegexParserTest : ParserTest() {
    override fun getImplementation() = RegexParser()
}