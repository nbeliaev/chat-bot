package dev.fr13.database.dao;

import dev.fr13.database.entities.ProductEntity;

public class ProductDao extends AbstractDaoImpl<ProductEntity> {

    @Override
    public void deleteAll() {
        interactWithDB((session) -> {
            session.createQuery("DELETE FROM ProductEntity").executeUpdate();
            return null;
        });
    }
}
