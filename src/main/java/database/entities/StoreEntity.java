package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "store")
@SuppressWarnings("unused")
public class StoreEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String uuid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address")
    private String address;
    @ManyToMany(
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "product_store",
            joinColumns = @JoinColumn(name = "store_uuid"),
            inverseJoinColumns = @JoinColumn(name = "product_uuid")
    )
    private List<ProductEntity> products;

    public StoreEntity() {
    }

    public StoreEntity(String uuid, String name, String address) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreEntity that = (StoreEntity) o;

        if (!uuid.equals(that.uuid)) return false;
        if (!name.equals(that.name)) return false;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StoreEntity{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
