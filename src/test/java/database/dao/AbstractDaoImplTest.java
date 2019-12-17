package database.dao;

import database.entities.ProductsInStoresEntity;
import database.entities.ProductEntity;
import database.entities.StoreEntity;
import exceptions.NotExistDataBaseException;
import org.junit.Test;

public abstract class AbstractDaoImplTest<T> {
    final ProductEntity PRODUCT_1 = new ProductEntity("product1", "synonym1");
    final StoreEntity STORE_1 = new StoreEntity("uuid_store_1", "store1", "address1");
    final ProductsInStoresEntity PRODUCT_IN_STORE_1 = new ProductsInStoresEntity(100.01, 10.01);
    final ProductEntity PRODUCT_2 = new ProductEntity("product2");
    final StoreEntity STORE_2 = new StoreEntity("uuid_store_2", "store2", "address2");
    final ProductsInStoresEntity PRODUCT_IN_STORE_2 = new ProductsInStoresEntity(100.02, 10.02);
    Dao<T> dao;

    {
        PRODUCT_1.setUuid("product_uuid1");
        PRODUCT_2.setUuid("product_uuid2");
        STORE_1.setUuid("store_uuid1");
        STORE_2.setUuid("store_uuid2");
        PRODUCT_IN_STORE_1.setUuid("price_uuid1");
        PRODUCT_IN_STORE_2.setUuid("price_uuid2");
    }

    AbstractDaoImplTest(Dao<T> dao) {
        this.dao = dao;
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