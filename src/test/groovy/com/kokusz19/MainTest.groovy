package com.kokusz19

import spock.lang.Specification

class MainTest extends Specification {

	def main = new Main()

	def "initGame"() {
		when:
			main.initGame()
		then:
			0 * _
		and:
			noExceptionThrown()
	}

	def "printState"() {
		when:
			main.printState()
		then:
			0 * _
		and:
			noExceptionThrown()
	}
}