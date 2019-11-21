package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "product",
        indexes = {
                @Index(columnList = "name"),
                @Index(columnList = "synonym")
        })
@SuppressWarnings("unused")
public class ProductEntity implements Serializable {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "synonym")
    private String synonym;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private List<PriceEntity> prices;

    public ProductEntity() {
    }

    public ProductEntity(String name) {
        this.name = name;
    }

    public ProductEntity(String name, String synonym) {
        this(name);
        this.synonym = synonym;
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

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String activeIngredient) {
        this.synonym = activeIngredient;
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
        price.setProduct(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (!name.equals(that.name)) return false;
        return Objects.equals(synonym, that.synonym);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (synonym != null ? synonym.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", synonym='" + synonym + '\'' +
                '}';
    }
}
