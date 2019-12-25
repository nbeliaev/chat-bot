package dev.fr13.database.dao;

import dev.fr13.database.entities.StoreEntity;
import dev.fr13.exceptions.ExistDataBaseException;
import dev.fr13.exceptions.NotExistDataBaseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class StoreDaoTest extends AbstractDaoImplTest<StoreEntity> {

    public StoreDaoTest() {
        super(new StoreDao());
    }

    @Before
    public void setUp() {
        dao.save(STORE_1);
    }

    @After
    public void tearDown() {
        dao.deleteAll();
    }

    @Test
    public void findByPattern() {
        final List<StoreEntity> stores = dao.findByPattern(StoreEntity.class, "address", STORE_1.getAddress());
        final int expectedSize = 1;
        Assert.assertEquals(expectedSize, stores.size());
    }

    @Override
    public void findById() {
        final StoreEntity store = dao.findByUuid(StoreEntity.class, STORE_1.getUuid());
        Assert.assertEquals(STORE_1, store);
    }

    @Override
    public void getAll() {
        final List<StoreEntity> list = dao.getAll(StoreEntity.class);
        final int expectedSize = 1;
        Assert.assertEquals(expectedSize, list.size());
    }

    @Override
    public void update() {
        STORE_1.setName("dummy");
        dao.update(STORE_1);
        StoreEntity store = dao.findByUuid(StoreEntity.class, STORE_1.getUuid());
        Assert.assertEquals(STORE_1, store);
    }

    @Test(expected = NotExistDataBaseException.class)
    public void updateNotExist() {
        dao.update(STORE_2);
    }

    @Override
    public void save() {
        dao.save(STORE_2);
        StoreEntity store = dao.findByUuid(StoreEntity.class, STORE_2.getUuid());
        Assert.assertEquals(STORE_2, store);
    }

    @Test(expected = ExistDataBaseException.class)
    public void saveExist() {
        dao.save(STORE_1);
    }

    @Override
    public void delete() {
        dao.delete(STORE_1);
        dao.findByUuid(StoreEntity.class, STORE_1.getUuid());
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
        StoreEntity store = dao.findByUuid(StoreEntity.class, STORE_1.getUuid());
        Assert.assertNull(store);
    }
}