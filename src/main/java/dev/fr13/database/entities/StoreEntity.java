package dev.fr13.database.entities;

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

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JoinColumn(name = "store_id")
    private List<ProductsInStoresEntity> productsInStores;

    public StoreEntity() {
    }

    private StoreEntity(String name) {
        this.name = name;
    }

    public StoreEntity(String name, String address) {
        this(name);
        this.address = address;
    }

    public StoreEntity(String name, String address, String phoneNumber) {
        this(name, address);
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ProductsInStoresEntity> getProductsInStores() {
        return productsInStores;
    }

    public void setProductsInStores(List<ProductsInStoresEntity> prices) {
        this.productsInStores = prices;
    }

    public void addPrice(ProductsInStoresEntity price) {
        if (productsInStores == null) {
            productsInStores = new ArrayList<>();
        }
        productsInStores.add(price);
        price.setStore(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoreEntity that = (StoreEntity) o;

        if (!uuid.equals(that.uuid)) return false;
        if (!name.equals(that.name)) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return phoneNumber != null ? phoneNumber.equals(that.phoneNumber) : that.phoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StoreEntity{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
