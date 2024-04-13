package com.kokusz19

import spock.lang.Specification


class StateHandlerTest extends Specification {

	def stateHandler = Main.StateHandler

	def "printState"() {
		when:
			stateHandler.printState()
		then:
			0 * _
		and:
			noExceptionThrown()
	}

	def "getColumnValues"() {
		setup:
			Main.StateLoader.loadState()
		when:
			def result = stateHandler.getColumnValues(0)
		then:
			assert result == [(0): "O",
			                  (1): "O",
			                  (2): "X"]
	}
}