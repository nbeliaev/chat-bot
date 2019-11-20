package database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_price")
@SuppressWarnings("unused")
public class PriceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    public PriceEntity() {
    }

    public PriceEntity(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (!product.equals(that.product)) return false;
        return store.equals(that.store);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = (int) (31 * result + price);
        result = 31 * result + product.hashCode();
        result = 31 * result + store.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductPriceEntity{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}
