package database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_price")
@SuppressWarnings("unused")
public class PriceEntity implements Serializable {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @Column(name = "price")
    private double price;

    public PriceEntity() {
    }

    public PriceEntity(double price) {
        this.price = price;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public StoreEntity getStore() {
        return store;
    }

    public void setStore(StoreEntity store) {
        this.store = store;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceEntity that = (PriceEntity) o;

        if (Double.compare(that.price, price) != 0) return false;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = uuid.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PriceEntity{" +
                "uuid='" + uuid + '\'' +
                ", price=" + price +
                '}';
    }
}
