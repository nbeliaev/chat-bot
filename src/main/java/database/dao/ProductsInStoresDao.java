package database.dao;

import database.entities.ProductsInStoresEntity;

public class ProductsInStoresDao extends AbstractDaoImpl<ProductsInStoresEntity> {

    @Override
    public void deleteAll() {
        interactWithDB((session) -> {
            session.createQuery("DELETE FROM ProductsInStoresEntity").executeUpdate();
            return null;
        });
    }
}
