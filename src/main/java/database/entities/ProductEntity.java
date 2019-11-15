package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product", indexes = {@Index(columnList = "name"), @Index(columnList = "synonym")})
@SuppressWarnings("unused")
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "synonym")
    private String synonym;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductPriceEntity> prices;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductBalanceEntity> balances;

    public ProductEntity() {
    }

    public ProductEntity(String externalId, String name) {
        this.externalId = externalId;
        this.name = name;
    }

    public ProductEntity(String externalId, String name, String synonym) {
        this(externalId, name);
        this.synonym = synonym;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String uuid) {
        this.externalId = uuid;
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

    public List<ProductPriceEntity> getPrices() {
        return prices;
    }

    public void setPrices(List<ProductPriceEntity> prices) {
        this.prices = prices;
    }

    public void addPrice(ProductPriceEntity price) {
        if (prices == null) {
            prices = new ArrayList<>();
        }
        prices.add(price);
    }

    public List<ProductBalanceEntity> getBalances() {
        return balances;
    }

    public void setBalances(List<ProductBalanceEntity> balances) {
        this.balances = balances;
    }

    public void addBalance(ProductBalanceEntity balance) {
        if (balances == null) {
            balances = new ArrayList<>();
        }
        balances.add(balance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (!externalId.equals(that.externalId)) return false;
        if (!name.equals(that.name)) return false;
        return Objects.equals(synonym, that.synonym);
    }

    @Override
    public int hashCode() {
        int result = externalId.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (synonym != null ? synonym.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", synonym='" + synonym + '\'' +
                '}';
    }
}
