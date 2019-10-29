package database.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ProductDaoImplTest.class,
        StoreDaoImplTest.class
})
public class AllDaoTest {
}
