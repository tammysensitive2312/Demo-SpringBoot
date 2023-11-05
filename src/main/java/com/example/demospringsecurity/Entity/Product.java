package com.example.demospringsecurity.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product  {
    @Id
    @GeneratedValue

    @Column(name = "product_id")
    private long id;
    @Column(name = "product_title")
    private String title;
    @Column(name = "product_price")
    private float price;


}
