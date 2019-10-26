package dao;

import entities.ProductEntity;
import org.junit.Assert;

public class ProductDaoTest extends AbstractDaoTest<ProductEntity> {

    public ProductDaoTest() {
        super(new ProductDao());
    }

    @Override
    public void findByUuid() {
        final ProductEntity product = dao.findByUuid(ProductEntity.class, UUID_PRODUCT_1);
        Assert.assertEquals(PRODUCT_1, product);
    }
}