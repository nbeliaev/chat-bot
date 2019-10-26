package entities;

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
    @Column(name = "name")
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

}
