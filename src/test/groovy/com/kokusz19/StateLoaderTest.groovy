package com.kokusz19

import spock.lang.Specification


class StateLoaderTest extends Specification {
	def stateLoader = Main.StateLoader

	def "loadState"() {
		when:
			stateLoader.loadState()
		then:
			0 * _
		and:
			noExceptionThrown()
	}
}