import configs.ConfigTest;
import database.dao.AllDaoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utils.PriceFormatter;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllDaoTest.class,
        ConfigTest.class,
        PriceFormatter.class
})
public class AllTests {
}
