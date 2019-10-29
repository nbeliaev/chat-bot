package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product", indexes = @Index(columnList = "description"))
@SuppressWarnings("unused")
public class ProductEntity implements Serializable {
    @Id
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "description", nullable = false)
    private String description;
    @ManyToMany(
            fetch = FetchType.EAGER,
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

    public ProductEntity(String uuid, String description) {
        this.uuid = uuid;
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (!uuid.equals(that.uuid)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
