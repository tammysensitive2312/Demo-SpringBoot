package com.example.demospringsecurity.Service;

import com.example.demospringsecurity.Entity.Product;
import com.example.demospringsecurity.Entity.User;
import com.example.demospringsecurity.Repository.ProductRepository;
import com.example.demospringsecurity.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    // PostConstruct có tác dụng đánh dấu phương thức sẽ chạy ngay khi kởi tạo bean ở đây là service bean
    // khởi tạo service sẽ convert hết data từ db

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(long id) {
        return getProducts().stream()
                .filter(product -> product.getId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("product" + id + " not found"));
    }

}
