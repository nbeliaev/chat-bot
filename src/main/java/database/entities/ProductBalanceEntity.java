package database.entities;

import javax.persistence.*;

@Entity
@Table(name = "product_balance")
@SuppressWarnings("unused")
public class ProductBalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "balance")
    private double balance;

    public ProductBalanceEntity() {
    }

    public ProductBalanceEntity(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ProductBalance{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
