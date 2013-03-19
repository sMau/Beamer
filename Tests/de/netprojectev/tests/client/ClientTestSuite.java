package de.netprojectev.tests.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ClientHandlerTest.class, ClientMediaModelTest.class, ClientMessageProxyTest.class, ClientTickerModelTest.class})
public class ClientTestSuite {

}
