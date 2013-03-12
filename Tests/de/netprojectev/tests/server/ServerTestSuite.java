package de.netprojectev.tests.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ServerMediaModelTest.class, ServerTickerModelTest.class, ServerMessageProxyTest.class, ServerHandlerTest.class })
public class ServerTestSuite {

}
