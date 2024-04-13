package com.kokusz19

import spock.lang.Specification


class StateHandlerTest extends Specification {

	def stateLoader = Main.StateLoader
	def stateHandler = Main.StateHandler

	def "printState"() {
		setup:
			stateLoader.loadState()
		when:
			stateHandler.printState()
		then:
			0 * _
		and:
			noExceptionThrown()
	}

	def "getColumnValues"() {
		setup:
			stateLoader.loadState()
		when:
			def result = stateHandler.getColumnValues(0)
		then:
			assert result == [(0): "O",
			                  (1): "O",
			                  (2): "X"]
	}
}