package database.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ProductDaoTest.class,
        StoreDaoTest.class,
        PriceDaoTest.class
})
public class AllDaoTest {
}
