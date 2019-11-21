package database.dao;

import database.entities.PriceEntity;

public class PriceDao extends AbstractDaoImpl<PriceEntity> {

    @Override
    public void deleteAll() {
        interactWithDB((session) -> {
            session.createQuery("DELETE FROM PriceEntity").executeUpdate();
            return null;
        });
    }
}
