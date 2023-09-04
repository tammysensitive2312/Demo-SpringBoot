package niit.com.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity(name = "products")
@Data
public class Product implements Serializable {
    @Id
    @Column(name = "product_id")
    private int id;

    @Column(name = "product_title")
    private String title;
    @Column(name = "product_price")
    private double price;
}
