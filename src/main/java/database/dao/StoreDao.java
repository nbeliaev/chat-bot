package database.dao;

import database.entities.StoreEntity;

public class StoreDao extends AbstractDaoImpl<StoreEntity> {

    @Override
    public void deleteAll() {
        interactWithDB((session) -> {
            session.createQuery("DELETE FROM StoreEntity").executeUpdate();
            return null;
        });
    }
}
