package de.netprojectev.server;

import uk.co.flamingpenguin.jewel.cli.Option;

public interface ServerCLI {

	@Option(helpRequest = true)
	boolean getHelp();
	
	@Option(shortName = "p")
	int getPort();
	
}
