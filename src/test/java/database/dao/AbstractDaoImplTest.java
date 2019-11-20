package database.dao;

import database.entities.PriceEntity;
import database.entities.ProductEntity;
import database.entities.StoreEntity;
import exceptions.NotExistDataBaseException;
import org.junit.After;
import org.junit.Test;

public abstract class AbstractDaoImplTest<T> {
    final ProductEntity PRODUCT_1 = new ProductEntity("uuid_product_1", "product1", "synonym1");
    final StoreEntity STORE_1 = new StoreEntity("uuid_store_1", "store1", "address1");
    final PriceEntity PRICE_1 = new PriceEntity(100.01);
    final ProductEntity PRODUCT_2 = new ProductEntity("uuid_product_2", "product2");
    final StoreEntity STORE_2 = new StoreEntity("uuid_store_2", "store2", "address2");
    final PriceEntity PRICE_2 = new PriceEntity(100.02);
    Dao<T> dao;

    AbstractDaoImplTest(Dao<T> dao) {
        this.dao = dao;
    }

    @After
    public void tearDown() {
        dao.deleteAll();
    }

    @Test
    public abstract void findById();

    @Test
    public abstract void getAll();

    @Test
    public abstract void update();

    @Test
    public abstract void save();

    @Test(expected = NotExistDataBaseException.class)
    public abstract void delete();

    @Test(expected = NotExistDataBaseException.class)
    public abstract void deleteAll();
}