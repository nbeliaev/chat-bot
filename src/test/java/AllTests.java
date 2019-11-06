import configs.ConfigTest;
import database.dao.AllDaoTest;
import database.externaldata.DataReceiverTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllDaoTest.class,
        ConfigTest.class,
        DataReceiverTest.class
})
public class AllTests {
}
