package database.dao;

import database.entities.PriceEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.util.List;

public class PriceDaoTest extends AbstractDaoImplTest<PriceEntity> {
    private final static ProductDao productDao = new ProductDao();
    private final static StoreDao storeDao = new StoreDao();

    public PriceDaoTest() {
        super(new PriceDao());
    }

    @Before
    public void setUp() {
        productDao.save(PRODUCT_1);
        storeDao.save(STORE_1);
        STORE_1.addPrice(PRICE_1);
        PRODUCT_1.addPrice(PRICE_1);
        dao.save(PRICE_1);
    }

    @After
    public void tearDown() {
        dao.deleteAll();
        productDao.deleteAll();
        storeDao.deleteAll();
    }

    @Override
    public void findById() {
        final PriceEntity price = dao.findByUuid(PriceEntity.class, PRICE_1.getUuid());
        Assert.assertEquals(PRICE_1, price);
    }

    @Override
    public void getAll() {
        final List<PriceEntity> list = dao.getAll(PriceEntity.class);
        int expectedSize = 1;
        Assert.assertEquals(expectedSize, list.size());
    }

    @Override
    public void update() {
        PRICE_1.setPrice(200.02);
        dao.update(PRICE_1);
        Assert.assertEquals(PRICE_1, dao.findByUuid(PriceEntity.class, PRICE_1.getUuid()));
    }

    @Override
    public void save() {
        PRODUCT_1.addPrice(PRICE_2);
        STORE_1.addPrice(PRICE_2);
        dao.save(PRICE_2);
        Assert.assertEquals(PRICE_2, dao.findByUuid(PriceEntity.class, PRICE_2.getUuid()));
    }

    @Override
    public void delete() {
        dao.delete(PRICE_1);
        dao.findByUuid(PriceEntity.class, PRICE_1.getUuid());
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
        final PriceEntity price = dao.findByUuid(PriceEntity.class, PRICE_1.getUuid());
        Assert.assertNull(price);
    }
}