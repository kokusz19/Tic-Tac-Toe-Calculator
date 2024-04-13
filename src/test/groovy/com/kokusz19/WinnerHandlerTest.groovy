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
			def result = winnerHandler.checkLine(line)
		then:
			0 * _
		and:
			assert result == expected

		where:
			connectionsToWin    || expected
			3                   || Winner.PLAYER_A
			10                  || Winner.NO_WINNER
	}

	def "findWinner"() {
		setup:
			Main.PATH_TO_INPUT = "input1.txt"
			stateLoader.loadState()

		when: "initGame"
			def result = winnerHandler.findWinner()
		then:
			assert result == Winner.PLAYER_A

		when: "no winner"
			main.STATE = [(0): [(0): "O"],
			              (1): [(1): "X"]]
			result = winnerHandler.findWinner()
		then:
			assert result == Winner.NO_WINNER
	}
}