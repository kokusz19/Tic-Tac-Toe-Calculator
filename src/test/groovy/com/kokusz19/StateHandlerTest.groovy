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

	def "getColumnValuesAsRow"() {
		setup:
			Main.PATH_TO_INPUT = "input1.txt"
			stateLoader.loadState()

		when:
			def result = stateHandler.getColumnValuesAsRow(0)
		then:
			assert result == [(0): "O",
			                  (1): "O",
			                  (2): "X"]
	}

	def "getDiagonalValuesAsRow"() {
		setup:
			Main.PATH_TO_INPUT = "input2.txt"
			stateLoader.loadState()

		when:
			def result = stateHandler.getDiagonalValuesAsRow(0, 10, true)
		then:
			0 * _
		and:
			assert result == [
					(2): "O",
					(4): "O",
					(5): "X"
			]

		when:
			result = stateHandler.getDiagonalValuesAsRow(3, 0, false)
		then:
			0 * _
		and:
			assert result == [
					(4): "O"
			]
	}
}