package com.kokusz19

import spock.lang.Specification


class StateLoaderTest extends Specification {
	def stateLoader = Main.StateLoader
	def stateHandler = Main.StateHandler

	def "loadState"() {
		when:
			Main.PATH_TO_INPUT = "input2.txt"
			stateLoader.loadState()
		then:
			0 * _
		and:
			noExceptionThrown()
	}

	def "loadState - sad cases"() {
		when: "The given filepath doesn't exist"
			Main.PATH_TO_INPUT = "nonExistingFile.txt"
			stateLoader.loadState()
		then:
			0 * _
		and:
			def ex = thrown(IllegalArgumentException)
			assert ex.message == "File could not be opened!"

		when: "A number field couldn't be parsed"
			Main.PATH_TO_INPUT = "numberFormatException.txt"
			stateLoader.loadState()
		then:
			0 * _
		and:
			ex = thrown(IllegalArgumentException)
			assert ex.message == "Number format exception occurred."

		when: "Higher CONNECTION_TO_WIN was passed, than available spaces"
			Main.PATH_TO_INPUT = "wrongConnectionToWinCount.txt"
			stateLoader.loadState()
		then:
			0 * _
		and:
			ex = thrown(IllegalArgumentException)
			assert ex.message == "Can't have a higher connection to win count, than available spaces on the board!"

		when: "The state contains illegal symbols"
			Main.PATH_TO_INPUT = "illegalArguments.txt"
			stateLoader.loadState()
		then:
			0 * _
		and:
			ex = thrown(IllegalArgumentException)
			assert ex.message.contains("The given state contains illegal values")
	}
}