package de.netprojectev.guiserver;

import uk.co.flamingpenguin.jewel.cli.ArgumentValidationException;
import uk.co.flamingpenguin.jewel.cli.Cli;
import uk.co.flamingpenguin.jewel.cli.CliFactory;

public class StarterServer {

	public static void main(String[] args) {

		System.setProperty("sun.java2d.opengl", "True");

		CliServer commands = null;
		final Cli<CliServer> cli = CliFactory.createCli(CliServer.class);
		try {
			commands = cli.parseArguments(args);
		} catch (ArgumentValidationException e) {
			System.out.println(cli.getHelpMessage());
			System.exit(0);
		}

		int port = commands.getPort();

		if (!(port < 65535 && port > 1024)) {
			System.out.println(cli.getHelpMessage());
			System.exit(0);
		}

		boolean fullscreen = commands.isFullscreen();

		new DisplayFrame(fullscreen, port);
	}

}