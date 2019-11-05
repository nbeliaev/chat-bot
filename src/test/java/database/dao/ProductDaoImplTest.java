package database.dao;

import database.entities.ProductEntity;
import exceptions.NotExistDataBaseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ProductDaoImplTest extends AbstractDaoImplTest<ProductEntity> {

    public ProductDaoImplTest() {
        super(new ProductDaoImpl());
    }

    @Test
    public void findByDescription() {
        final ProductEntity entity = dao.findByPattern(ProductEntity.class, "description", "product1");
        Assert.assertEquals(PRODUCT_1, entity);
    }

    @Test(expected = NotExistDataBaseException.class)
    public void findByDescriptionNotExist() {
        dao.findByPattern(ProductEntity.class, "description", "product2");
    }

    @Test
    public void storeCount() {
        PRODUCT_1.addStore(STORE_1);
        dao.update(PRODUCT_1);
        final ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getUuid());
        Assert.assertEquals(1, product.getStores().size());
    }

    @Override
    public void findByUuid() {
        final ProductEntity product = dao.findByUuid(ProductEntity.class, UUID_PRODUCT_1);
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
        PRODUCT_1.setDescription("dummy");
        dao.update(PRODUCT_1);
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getUuid());
        Assert.assertEquals(PRODUCT_1, product);
    }

    @Override
    public void updateNotExist() {
        dao.update(PRODUCT_2);
    }

    @Override
    public void save() {
        dao.save(PRODUCT_2);
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_2.getUuid());
        Assert.assertEquals(PRODUCT_2, product);
    }

    @Override
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