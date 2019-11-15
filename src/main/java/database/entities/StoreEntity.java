package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "store")
@SuppressWarnings("unused")
public class StoreEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "external_id", nullable = false, unique = true)
    private String external_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private List<ProductPriceEntity> prices;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private List<ProductBalanceEntity> balances;

    public StoreEntity() {
    }

    public StoreEntity(String external_id, String name, String address) {
        this(external_id, name);
        this.address = address;
    }

    public StoreEntity(String external_id, String name) {
        this.external_id = external_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
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

    public List<ProductPriceEntity> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductPriceEntity> prices) {
        this.prices = prices;
    }

    public List<ProductBalanceEntity> getBalances() {
        return balances;
    }

    public void setBalances(List<ProductBalanceEntity> balances) {
        this.balances = balances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreEntity that = (StoreEntity) o;

        if (!external_id.equals(that.external_id)) return false;
        if (!name.equals(that.name)) return false;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        int result = external_id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StoreEntity{" +
                "uuid='" + external_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
