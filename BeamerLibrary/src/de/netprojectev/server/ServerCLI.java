package de.netprojectev.server;

import uk.co.flamingpenguin.jewel.cli.CommandLineInterface;
import uk.co.flamingpenguin.jewel.cli.Option;

@CommandLineInterface(application = "Beamer Server")
public interface ServerCLI {

	@Option(helpRequest = true, description = "Display this help message", shortName = "h")
	boolean getHelp();

	@Option(description = "The (numeric) port on which the server listen for incoming connections. The port needs to be bigger than 1024 and smaller than 65535", shortName = "p", longName = "port")
	int getPort();

	@Option(description = "This flag forces the server to start in fullscreen", shortName = "f", longName = "fullscreen")
	boolean isFullscreen();
}
