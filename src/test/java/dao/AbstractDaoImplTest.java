package dao;

import entities.ProductEntity;
import entities.StoreEntity;
import exceptions.ExistDataBaseException;
import exceptions.NotExistDataBaseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractDaoImplTest<T> {
    final static String UUID_PRODUCT_1 = "uuid_product_1";
    final static String UUID_STORE_1 = "uuid_store_1";
    final static ProductEntity PRODUCT_1 = new ProductEntity(UUID_PRODUCT_1, "product1");
    final static ProductEntity PRODUCT_2 = new ProductEntity("uuid_product_2", "product2");
    final static StoreEntity STORE_1 = new StoreEntity(UUID_STORE_1, "store1", "address1");
    final static StoreEntity STORE_2 = new StoreEntity("uuid_store_2", "store2", "address2");
    Dao<T> dao;

    AbstractDaoImplTest(Dao<T> dao) {
        this.dao = dao;
    }

    @Before
    public void setUp() {
        dao.save((T) STORE_1);
        dao.save((T) PRODUCT_1);
    }

    @After
    public void tearDown() {
        dao.deleteAll();
    }

    @Test
    public abstract void findByUuid();

    @Test
    public abstract void update();

    @Test(expected = NotExistDataBaseException.class)
    public abstract void updateNotExist();

    @Test
    public abstract void save();

    @Test(expected = ExistDataBaseException.class)
    public abstract void saveExist();

    @Test(expected = NotExistDataBaseException.class)
    public abstract void delete();

    @Test(expected = NotExistDataBaseException.class)
    public abstract void deleteAll();
}