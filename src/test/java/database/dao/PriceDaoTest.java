package database.dao;

import database.entities.PriceEntity;
import org.junit.Assert;
import org.junit.Before;

import java.util.List;

public class PriceDaoTest extends AbstractDaoImplTest<PriceEntity> {

    public PriceDaoTest() {
        super(new PriceDao());
    }

    @Before
    public void setUp() {
        final ProductDao productDao = new ProductDao();
        productDao.save(PRODUCT_1);
        final StoreDao storeDao = new StoreDao();
        storeDao.save(STORE_1);
        STORE_1.addPrice(PRICE_1);
        PRODUCT_1.addPrice(PRICE_1);
        dao.save(PRICE_1);
    }

    @Override
    public void findById() {
        final PriceEntity price = dao.findById(PriceEntity.class, PRICE_1.getId());
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
        Assert.assertEquals(PRICE_1, dao.findById(PriceEntity.class, PRICE_1.getId()));
    }

    @Override
    public void save() {
        PRODUCT_1.addPrice(PRICE_2);
        STORE_1.addPrice(PRICE_2);
        dao.save(PRICE_2);
        Assert.assertEquals(PRICE_2, dao.findById(PriceEntity.class, PRICE_2.getId()));
    }

    @Override
    public void delete() {
        dao.delete(PRICE_1);
        dao.findById(PriceEntity.class, PRICE_1.getId());
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
        final PriceEntity price = dao.findById(PriceEntity.class, PRICE_1.getId());
        Assert.assertNull(price);
    }
}