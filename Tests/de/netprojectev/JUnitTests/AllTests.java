package de.netprojectev.JUnitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DisplayHandlerTest.class, MediaHandlerTest.class, LiveTickerTest.class})
public class AllTests {

}
