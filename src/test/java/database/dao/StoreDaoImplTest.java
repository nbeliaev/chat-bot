package database.dao;

import database.entities.StoreEntity;
import exceptions.NotExistDataBaseException;
import org.junit.Assert;
import org.junit.Test;

public class StoreDaoImplTest extends AbstractDaoImplTest<StoreEntity> {

    public StoreDaoImplTest() {
        super(new StoreDaoImpl());
    }

    @Test
    public void findByName() {
        final StoreEntity store = dao.findByPattern(StoreEntity.class, "name", "store1");
        Assert.assertEquals(STORE_1, store);
    }

    @Test(expected = NotExistDataBaseException.class)
    public void findByNameNotExist() {
        dao.findByPattern(StoreEntity.class, "name", "store2");
    }

    @Override
    public void findByUuid() {
        final StoreEntity store = dao.findByUuid(StoreEntity.class, UUID_STORE_1);
        Assert.assertEquals(STORE_1, store);
    }

    @Override
    public void update() {
        STORE_1.setName("dummy");
        dao.update(STORE_1);
        StoreEntity store = dao.findByUuid(StoreEntity.class, STORE_1.getUuid());
        Assert.assertEquals(STORE_1, store);
    }

    @Override
    public void updateNotExist() {
        dao.update(STORE_2);
    }

    @Override
    public void save() {
        dao.save(STORE_2);
        StoreEntity store = dao.findByUuid(StoreEntity.class, STORE_2.getUuid());
        Assert.assertEquals(STORE_2, store);
    }

    @Override
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