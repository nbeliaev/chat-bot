package dao;

import entities.ProductEntity;
import org.junit.Assert;
import org.junit.Test;

public class ProductDaoTest extends AbstractDaoTest<ProductEntity> {

    public ProductDaoTest() {
        super(new ProductDao());
    }

    @Override
    public void findByUuid() {
        final ProductEntity product = dao.findByUuid(ProductEntity.class, UUID_PRODUCT_1);
        Assert.assertEquals(PRODUCT_1, product);
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

    @Test
    public void clear() {
        dao.deleteAll();
        ProductEntity product = dao.findByUuid(ProductEntity.class, PRODUCT_1.getId());
        Assert.assertNull(product);
    }

}