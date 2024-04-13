package com.kokusz19

import com.kokusz19.model.Winner
import spock.lang.Specification


class WinnerHandlerTest extends Specification {

	def winnerHandler = Main.WinnerHandler
	def stateLoader = Main.StateLoader
	def main = new Main()

	def "isWinnerPosition"() {
		setup:  "_ _ _ X _ X X _ O O X _ |X X X| _ _ X"
			Map<Integer, Character> line = Map.of(3, "X", 5, "X", 6, "X", 8, "O", 9, "O", 10, "X", 12, "X", 13, "X", 14, "X", 17, "X")
			main.CONNECTIONS_TO_WIN = 3

		when:
			def result = winnerHandler.isWinnerPosition(line, lineElement, character)
		then:
			0 * _
		and:
			assert result == expected

		where:
			lineElement | character || expected
			3           | "X"       || false        // "element 3 ->  _ _ _ |X| _ X X _ O O X _ X X X _ _ X"
			5           | "X"       || false        // "element 5 ->  _ _ _ X _ |X| X _ O O X _ X X X _ _ X"
			8           | "O"       || false        // "element 8 ->  _ _ _ X _ X X _ |O| O X _ X X X _ _ X"
			12          | "X"       || true         // "element 12 -> _ _ _ X _ X X _ O O X _ |X| X X _ _ X"
			17          | "X"       || false        // "element 17 -> _ _ _ X _ X X _ O O X _ X X X _ _ |X|"
	}

	def "checkLine"() {
		setup:  "_ _ _ X _ X X _ O O X _ |X X X| _ _ X"
			Map<Integer, Character> line = Map.of(3, "X", 5, "X", 6, "X", 8, "O", 9, "O", 10, "X", 12, "X", 13, "X", 14, "X", 17, "X")
			main.CONNECTIONS_TO_WIN = connectionsToWin

		when:
			def result = winnerHandler.checkRow(line)
		then:
			0 * _
		and:
			assert result == expected

		where:
			connectionsToWin    || expected
			3                   || Winner.PLAYER_A
			10                  || Winner.NO_WINNER
	}

	def "findWinner - #testCase"() {
		setup:
			stateLoader.loadState(inputFile)

		when:
			def result = winnerHandler.findWinner()
		then:
			assert result == expected

		where:
			testCase        | inputFile     || expected
			"simple game"   | "input1.txt"  || Winner.PLAYER_B
			"complex game"  | "input2.txt"  || Winner.PLAYER_A
			"no winner"     | "input3.txt"  || Winner.NO_WINNER
	}
}