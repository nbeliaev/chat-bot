package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@SuppressWarnings("unused")
public class StoreEntity implements Serializable {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JoinColumn(name = "store_id")
    private List<PriceEntity> prices;

    public StoreEntity() {
    }

    public StoreEntity(String external_id, String name, String address) {
        this(external_id, name);
        this.address = address;
    }

    private StoreEntity(String external_id, String name) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PriceEntity> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceEntity> prices) {
        this.prices = prices;
    }

    public void addPrice(PriceEntity price) {
        if (prices == null) {
            prices = new ArrayList<>();
        }
        prices.add(price);
        price.setStore(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreEntity that = (StoreEntity) o;

        if (!name.equals(that.name)) return false;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
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
