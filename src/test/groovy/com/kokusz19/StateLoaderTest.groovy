package com.kokusz19

import com.kokusz19.model.InputFiles
import spock.lang.Specification


class StateLoaderTest extends Specification {

	def stateLoader = Main.StateLoader

	def "loadState"() {
		when:
			stateLoader.loadState(InputFiles.COMPLEX_GAME.path)
		then:
			0 * _
		and:
			noExceptionThrown()
	}

	def "loadState - sad cases"() {
		when: "The given filepath doesn't exist"
			stateLoader.loadState(InputFiles.NON_EXISTING.path)
		then:
			0 * _
		and:
			def ex = thrown(IllegalArgumentException)
			assert ex.message == "File could not be opened!"

		when: "A number field couldn't be parsed"
			stateLoader.loadState(InputFiles.NUMBER_FORMAT_EXCEPTION.path)
		then:
			0 * _
		and:
			ex = thrown(IllegalArgumentException)
			assert ex.message == "Number format exception occurred."

		when: "Higher CONNECTION_TO_WIN was passed, than available spaces"
			stateLoader.loadState(InputFiles.WRONG_CONNECTION_TO_WIN_COUNT.path)
		then:
			0 * _
		and:
			ex = thrown(IllegalArgumentException)
			assert ex.message == "Can't have a higher connection to win count, than available spaces on the board!"

		when: "The state contains illegal symbols"
			stateLoader.loadState(InputFiles.ILLEGAL_ARGUMENTS.path)
		then:
			0 * _
		and:
			ex = thrown(IllegalArgumentException)
			assert ex.message.contains("The given state contains illegal values")
	}
}