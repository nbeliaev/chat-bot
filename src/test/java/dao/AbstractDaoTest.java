package dao;

import entities.ProductEntity;
import entities.StoreEntity;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractDaoTest<T> {
    Dao<T> dao;
    final static String UUID_PRODUCT_1 = "uuid_product_1";
    final static ProductEntity PRODUCT_1 = new ProductEntity(UUID_PRODUCT_1, "product1");
    private final static ProductEntity PRODUCT_2 = new ProductEntity("uuid_product_2", "product2");
    private final static StoreEntity STORE_1 = new StoreEntity("uuid_store_1", "store1", "address1");
    private final static StoreEntity STORE_2 = new StoreEntity("uuid_store_2", "store2", "address2");

    AbstractDaoTest(Dao<T> dao) {
        this.dao = dao;
    }

    @Before
    public void setUp() throws Exception {
        PRODUCT_1.addStore(STORE_1);
        PRODUCT_1.addStore(STORE_2);
        dao.save((T) STORE_1);
        dao.save((T) STORE_2);
        dao.save((T) PRODUCT_1);
    }

    @Test
    public abstract void findByUuid();

    @Test
    public void update() {
    }

    @Test
    public void save() {

    }

    @Test
    public void delete() {
    }

}