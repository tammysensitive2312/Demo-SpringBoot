package com.example.demospringsecurity.Controller;

import com.example.demospringsecurity.DTO.AuthRequest;
import com.example.demospringsecurity.DTO.JwtResponse;
import com.example.demospringsecurity.Entity.Product;
import com.example.demospringsecurity.DTO.RefreshTokenRequest;
import com.example.demospringsecurity.Entity.RefreshToken;
import com.example.demospringsecurity.Entity.User;
import com.example.demospringsecurity.Service.JwtService;
import com.example.demospringsecurity.Service.ProductService;
import com.example.demospringsecurity.Service.RefreshTokenService;
import com.example.demospringsecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to this endpoint is not secure";
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public List<Product> getProducts() {
        return productService.getProducts();
    }
    // PathVariable có tác dụng trích xuất 1 giá trị trong url
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable int id) {
        return productService.getProduct(id);
    }


}
