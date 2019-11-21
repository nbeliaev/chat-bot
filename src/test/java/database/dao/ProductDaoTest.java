package database.dao;

import database.entities.ProductEntity;
import exceptions.ExistDataBaseException;
import exceptions.NotExistDataBaseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductDaoTest extends AbstractDaoImplTest<ProductEntity> {

    public ProductDaoTest() {
        super(new ProductDao());
    }

    @Before
    public void setUp() {
        dao.save(PRODUCT_1);
    }

    @After
    public void tearDown() {
        dao.deleteAll();
    }

    @Test
    public void findByPattern() {
        PRODUCT_1.setSynonym("dummy");
        dao.update(PRODUCT_1);
        final List<ProductEntity> analogs = dao.findByPattern(ProductEntity.class, "synonym", "dummy");
        final int expectedSize = 1;
        Assert.assertEquals(expectedSize, analogs.size());
    }

    @Override
    public void findById() {
        final ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getUuid());
        Assert.assertEquals(PRODUCT_1, product);
    }

    @Override
    public void getAll() {
        final List<ProductEntity> list = dao.getAll(ProductEntity.class);
        final int expectedSize = 1;
        Assert.assertEquals(expectedSize, list.size());
    }

    @Override
    public void update() {
        PRODUCT_1.setName("dummy");
        dao.update(PRODUCT_1);
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getUuid());
        Assert.assertEquals(PRODUCT_1, product);
    }

    @Test(expected = NotExistDataBaseException.class)
    public void updateNotExist() {
        dao.update(PRODUCT_2);
    }

    @Override
    public void save() {
        dao.save(PRODUCT_2);
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_2.getUuid());
        Assert.assertEquals(PRODUCT_2, product);
    }

    @Test(expected = ExistDataBaseException.class)
    public void saveExist() {
        dao.save(PRODUCT_1);
    }

    @Override
    public void delete() {
        dao.delete(PRODUCT_1);
        dao.findByUuid(ProductEntity.class, PRODUCT_1.getUuid());
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getUuid());
        Assert.assertNull(product);
    }
}