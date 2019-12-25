package dev.fr13.database.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ProductDaoTest.class,
        StoreDaoTest.class,
        ProductsInStoresDaoTest.class
})
public class AllDaoTest {
}
