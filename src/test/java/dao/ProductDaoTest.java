package dao;

import entities.ProductEntity;
import exceptions.NotExistDataBaseException;
import org.junit.Assert;
import org.junit.Test;

public class ProductDaoTest extends AbstractDaoImplTest<ProductEntity> {

    public ProductDaoTest() {
        super(new ProductDaoImpl());
    }

    @Test
    public void findByDescription() {
        final ProductEntity entity = dao.findByDescription(ProductEntity.class, "product1");
        Assert.assertEquals(PRODUCT_1, entity);
    }

    @Test(expected = NotExistDataBaseException.class)
    public void findByDescriptionNotExist() {
        dao.findByDescription(ProductEntity.class, "product2");
    }

    @Override
    public void findByUuid() {
        final ProductEntity product = dao.findByUuid(ProductEntity.class, UUID_PRODUCT_1);
        Assert.assertEquals(PRODUCT_1, product);
    }

    public void update() {
        PRODUCT_1.setDescription("dummy");
        dao.update(PRODUCT_1);
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getId());
        Assert.assertEquals(PRODUCT_1, product);
    }

    @Override
    public void updateNotExist() {
        dao.update(PRODUCT_2);
    }

    @Override
    public void save() {
        dao.save(PRODUCT_2);
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_2.getId());
        Assert.assertEquals(PRODUCT_2, product);
    }

    @Override
    public void saveExist() {
        dao.save(PRODUCT_1);
    }

    @Override
    public void delete() {
        dao.delete(PRODUCT_1);
        dao.findByUuid(ProductEntity.class, PRODUCT_1.getId());
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getId());
        Assert.assertNull(product);
    }

}