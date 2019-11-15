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
    public void findByName() {
        final ProductEntity entity = dao.findByName(ProductEntity.class, PRODUCT_1_NAME);
        Assert.assertEquals(PRODUCT_1, entity);
    }

    @Test(expected = NotExistDataBaseException.class)
    public void findByNameNotExist() {
        dao.findByName(ProductEntity.class, "dummy");
    }

    /*@Test
    public void storeCount() {
        PRODUCT_1.addStore(STORE_1);
        dao.update(PRODUCT_1);
        final ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getUuid());
        Assert.assertEquals(1, product.getStores().size());
    }*/

    @Override
    public void findByPattern() {
        PRODUCT_1.setSynonym(PRODUCT_1_ANALOG);
        dao.update(PRODUCT_1);
        final List<ProductEntity> analogs = dao.findByPattern(ProductEntity.class, "activeIngredient", PRODUCT_1_ANALOG);
        final int expectedSize = 1;
        Assert.assertEquals(expectedSize, analogs.size());
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
        PRODUCT_1.setName("dummy");
        dao.update(PRODUCT_1);
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getExternalId());
        Assert.assertEquals(PRODUCT_1, product);
    }

    @Override
    public void updateNotExist() {
        dao.update(PRODUCT_2);
    }

    @Override
    public void save() {
        dao.save(PRODUCT_2);
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_2.getExternalId());
        Assert.assertEquals(PRODUCT_2, product);
    }

    @Override
    public void saveExist() {
        dao.save(PRODUCT_1);
    }

    @Override
    public void delete() {
        dao.delete(PRODUCT_1);
        dao.findByUuid(ProductEntity.class, PRODUCT_1.getExternalId());
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getExternalId());
        Assert.assertNull(product);
    }

}