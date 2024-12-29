package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    PacmanTest.class,
    GhostTest.class,
    ModelTest.class,
})
public class TestSuite {
}
