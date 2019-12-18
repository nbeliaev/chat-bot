package database.dao;

import database.entities.ProductsInStoresEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.util.List;

public class ProductsInStoresDaoTest extends AbstractDaoImplTest<ProductsInStoresEntity> {
    private final static ProductDao productDao = new ProductDao();
    private final static StoreDao storeDao = new StoreDao();

    public ProductsInStoresDaoTest() {
        super(new ProductsInStoresDao());
    }

    @Before
    public void setUp() {
        productDao.deleteAll();
        storeDao.deleteAll();

        storeDao.save(STORE_1);
        STORE_1.addPrice(PRODUCT_IN_STORE_1);
        PRODUCT_1.addProductsInStores(PRODUCT_IN_STORE_1);
        productDao.save(PRODUCT_1);
    }

    @After
    public void tearDown() {
        dao.deleteAll();
        productDao.deleteAll();
        storeDao.deleteAll();
    }

    @Override
    public void findById() {
        Assert.assertEquals(PRODUCT_IN_STORE_1, dao.findByUuid(ProductsInStoresEntity.class, PRODUCT_IN_STORE_1.getUuid()));
    }

    @Override
    public void getAll() {
        final List<ProductsInStoresEntity> list = dao.getAll(ProductsInStoresEntity.class);
        int expectedSize = 1;
        Assert.assertEquals(expectedSize, list.size());
    }

    @Override
    public void update() {
        PRODUCT_IN_STORE_1.setPrice(200.02);
        PRODUCT_IN_STORE_1.setQuantity(20.02);
        dao.update(PRODUCT_IN_STORE_1);
        Assert.assertEquals(PRODUCT_IN_STORE_1, dao.findByUuid(ProductsInStoresEntity.class, PRODUCT_IN_STORE_1.getUuid()));
    }

    @Override
    public void save() {
        PRODUCT_1.addProductsInStores(PRODUCT_IN_STORE_2);
        STORE_1.addPrice(PRODUCT_IN_STORE_2);
        dao.save(PRODUCT_IN_STORE_2);
        Assert.assertEquals(PRODUCT_IN_STORE_2, dao.findByUuid(ProductsInStoresEntity.class, PRODUCT_IN_STORE_2.getUuid()));
    }

    @Override
    public void delete() {
        dao.delete(PRODUCT_IN_STORE_1);
        dao.findByUuid(ProductsInStoresEntity.class, PRODUCT_IN_STORE_1.getUuid());
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
        final ProductsInStoresEntity price = dao.findByUuid(ProductsInStoresEntity.class, PRODUCT_IN_STORE_1.getUuid());
        Assert.assertNull(price);
    }
}