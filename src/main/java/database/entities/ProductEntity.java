package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product", indexes = @Index(columnList = "name, active_ingredient"))
@SuppressWarnings("unused")
public class ProductEntity implements Serializable {
    @Id
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "active_ingredient")
    private String activeIngredient;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    @JoinTable(
            name = "product_store",
            joinColumns = @JoinColumn(name = "product_uuid"),
            inverseJoinColumns = @JoinColumn(name = "store_uuid")
    )
    private List<StoreEntity> stores;

    public ProductEntity() {
    }

    public ProductEntity(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StoreEntity> getStores() {
        return stores;
    }

    public void setStores(List<StoreEntity> stores) {
        this.stores = stores;
    }

    public void addStore(StoreEntity store) {
        if (stores == null) {
            stores = new ArrayList<>();
        }
        stores.add(store);
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (!uuid.equals(that.uuid)) return false;
        if (!name.equals(that.name)) return false;
        return Objects.equals(activeIngredient, that.activeIngredient);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (activeIngredient != null ? activeIngredient.hashCode() : 0);
        return result;
    }
}
