import dev.fr13.configs.ConfigTest;
import dev.fr13.database.dao.AllDaoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import dev.fr13.utils.PriceFormatter;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AllDaoTest.class,
        ConfigTest.class,
        PriceFormatter.class
})
public class AllTests {
}
