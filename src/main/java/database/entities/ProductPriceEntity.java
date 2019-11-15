package database.entities;

import javax.persistence.*;

@Entity
@Table(name = "product_price")
@SuppressWarnings("unused")
public class ProductPriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private int price;

    public ProductPriceEntity() {
    }

    public ProductPriceEntity(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductPriceEntity{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}
