package de.netprojectev.tests.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.netprojectev.tests.client.ClientTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ ClientTestSuite.class, ServerTestSuite.class})
public class AllJUnitTests {

}
