package niit.com.demo.controller;

import jakarta.servlet.http.HttpSession;
import niit.com.demo.Entity.Product;
import niit.com.demo.Repositories.ProductRepository;
import niit.com.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("homecontroller")
public class HomeController {
    @Autowired
    ProductRepository repository;
    @Autowired
    ProductService service;
    @RequestMapping(value = "/list-product")
    public String list(Model model) {
        Iterable<Product> products = repository.findAll();
        model.addAttribute("products", products);
        return "list-product";
    }
    @RequestMapping(value = "/add-to-cart")
    public String addToCart(HttpSession httpSession, @RequestParam("id") int id) {
        service.addToCart(httpSession, id);
        return "redirect:/list-product";
    }

    @RequestMapping(value = "/cart")
    public String cart(HttpSession httpSession, Model model) {
        var obj = service.getCartItem(httpSession);
        //chuyển dữ liệu từ controller sang view thông qua model
        //key là cartItems, nếu ko đúng key view ko nhận được giá trị
        model.addAttribute("cartItems", obj);
        return "cart";
    }
    @RequestMapping(value = "/empty-cart")
    public String emptyCart(HttpSession httpSession) {
        service.emptyCart(httpSession);
        return "redirect:/cart";
    }

    @RequestMapping("/remove-cart-item")
    public String removeCartItems(HttpSession httpSession, @RequestParam("id") int id) {
        service.removeCartItem(httpSession, id);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/update-quantity")
    public String updateQuantity(@RequestParam("id") int id,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession httpSession) {
        service.updateQuantity(httpSession, quantity, id);
        return "redirect:/cart";
    }
}
