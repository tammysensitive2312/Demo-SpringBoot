package niit.com.demo.models;

import lombok.Data;
import niit.com.demo.Entity.Product;

import java.io.Serializable;
@Data
public class CartItem implements Serializable {
    private Product product;
    private int quantity = 0;

    public double getAmount() {
        return quantity * product.getPrice();
    }
}
