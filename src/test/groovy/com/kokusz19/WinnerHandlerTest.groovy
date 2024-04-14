package com.kokusz19

import com.kokusz19.model.InputFiles
import com.kokusz19.model.Winner
import spock.lang.Specification
import spock.lang.Unroll


class WinnerHandlerTest extends Specification {

	def main = new Main()
	def winnerHandler = Main.WinnerHandler
	def stateLoader = Main.StateLoader

	// "_ _ _ X _ X X _ O O X _ |X X X| _ _ X"
	def row = [(3) : "X", (5) : "X", (6) : "X", (8) : "O", (9) : "O",
	           (10): "X", (12): "X", (13): "X", (14): "X", (17): "X"]

	@Unroll
	def "isWinnerPosition"() {
		setup:
			main.CONNECTIONS_TO_WIN = 3

		when:
			def result = winnerHandler.isWinnerPosition(row, lineElement, character)
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

	@Unroll
	def "checkLine"() {
		setup:
			main.CONNECTIONS_TO_WIN = connectionsToWin

		when:
			def result = winnerHandler.checkRow(row)
		then:
			0 * _
		and:
			assert result == expected

		where:
			connectionsToWin    || expected
			3                   || Winner.PLAYER_A
			10                  || Winner.NO_WINNER
	}

	@Unroll
	def "findWinner - #testCase"() {
		setup:
			stateLoader.loadState(inputFilePath)

		when:
			def result = winnerHandler.findWinner()
		then:
			assert result == expected

		where:
			testCase        | inputFilePath                 || expected
			"simple game"   | InputFiles.SIMPLE_GAME.path   || Winner.PLAYER_B
			"complex game"  | InputFiles.COMPLEX_GAME.path  || Winner.PLAYER_A
			"no winner"     | InputFiles.NO_WINNER.path     || Winner.NO_WINNER
	}
}