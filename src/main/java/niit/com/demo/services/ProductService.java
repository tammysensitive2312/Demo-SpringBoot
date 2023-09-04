package niit.com.demo.services;

import jakarta.servlet.http.HttpSession;
import niit.com.demo.Entity.Product;
import niit.com.demo.Repositories.ProductRepository;
import niit.com.demo.models.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public final String CART = "cart";

    public void saveProduct(String title) {
        Product product = new Product();
        product.setTitle(title);
        productRepository.save(product);
    }

    public void addToCart(HttpSession httpSession, int id) {
        Product product = productRepository.findById(id).get();
        // case 1: chưa có giỏ hàng
        // case 2: có giỏ hàng
        if (httpSession.getAttribute(CART) == null) {
            ArrayList<CartItem> items = new ArrayList<>();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            items.add(cartItem);
            httpSession.setAttribute(CART, items);
        } else {
            // case 1: đã có sản phẩm
            // case 2: chưa có sản phầm
            ArrayList<CartItem> items = (ArrayList<CartItem>) httpSession.getAttribute(CART);
            boolean isExit = false;
            for (CartItem cartItem : items) {
                // so sáng giá trị "==" còn muốn so sánh đối tượng phải equals
                if(cartItem.getProduct().getId() == product.getId()) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    isExit = true;
                    break;
                }
            }
            if(!isExit) {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                items.add(cartItem);
            }
            httpSession.setAttribute(CART, getCartItem(httpSession));
        }
    }

    public ArrayList<CartItem> getCartItem(HttpSession httpSession) {
        if (httpSession.getAttribute(CART) == null) {
            return null;
        }
        return (ArrayList<CartItem>) httpSession.getAttribute(CART);
    }

    public void emptyCart(HttpSession httpSession) {
        httpSession.removeAttribute(CART);
    }

    public void removeCartItem(HttpSession httpSession, int id) {
        Product product = productRepository.findById(id).get();
        ArrayList<CartItem> cartItems = getCartItem(httpSession);
        for (CartItem item : cartItems
             ) {
            if(item.getProduct().getId() == product.getId()) {
                cartItems.remove(item);
                break;
            }
        }
        httpSession.setAttribute(CART, cartItems);
    }

    public void updateQuantity(HttpSession httpSession, int quanity, int id) {

        ArrayList<CartItem> cartItems = getCartItem(httpSession);
        for (CartItem item : cartItems
             ) {
            if(item.getProduct().getId() == id) {
                if (item.getQuantity() + quanity == 0) {
                    cartItems.remove(item);
                    break;
                }
                item.setQuantity(item.getQuantity() + quanity);
                break;
            }
        }
        httpSession.setAttribute(CART, cartItems);
    }
}
