package database.dao;

import database.entities.ProductEntity;

public class ProductDao extends AbstractDaoImpl<ProductEntity> {

    @Override
    public void deleteAll() {
        interactWithDB((session) -> {
            session.createQuery("DELETE FROM ProductEntity").executeUpdate();
            return null;
        });
    }
}
