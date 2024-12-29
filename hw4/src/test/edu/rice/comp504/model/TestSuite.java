package edu.rice.comp504.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    StrategyFactoryTest.class,
    UpdateStrategyTest.class,
    CollisionStrategyTest.class,
    PaintWorldStoreTest.class,
    BallCollisionsTest.class,
    FishWallCollisionsTest.class
})
public class TestSuite {
}
