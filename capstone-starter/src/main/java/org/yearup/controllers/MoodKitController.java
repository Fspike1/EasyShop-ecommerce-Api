package org.yearup.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.data.ProductDao;
import org.yearup.models.Product;

import java.util.List;

@RestController
@RequestMapping("/moodkits")
@CrossOrigin(origins = "http://localhost:63342")

public class MoodKitController {

    private final ProductDao productDao;

    public MoodKitController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping
    public List<Product> getMoodKits() {
        return productDao.getByCategory("Mental Health"); 
    }
}

