package com.kokusz19

import com.kokusz19.model.InputFiles
import spock.lang.Specification


class StateHandlerTest extends Specification {

	def stateLoader = Main.StateLoader
	def stateHandler = Main.StateHandler

	def "printState"() {
		setup:
			stateLoader.loadState(InputFiles.COMPLEX_GAME.path)

		when:
			stateHandler.printState()
		then:
			0 * _
		and:
			noExceptionThrown()
	}

	def "getColumnValuesAsRow"() {
		setup:
			stateLoader.loadState(InputFiles.SIMPLE_GAME.path)

		when:
			def result = stateHandler.getColumnValuesAsRow(0)
		then:
			assert result == [(0): "X",
			                  (1): "X",
			                  (2): "O"]
	}

	def "getDiagonalValuesAsRow"() {
		setup:
			stateLoader.loadState(InputFiles.COMPLEX_GAME.path)

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