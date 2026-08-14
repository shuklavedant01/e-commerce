package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void seedDatabase() {
        if (productRepository.count() == 0) {
            productRepository.save(new Product("Titan Wireless Headphones", "Premium active noise-canceling headphones with 40-hour battery life, spatial audio, and memory foam ear cushions.", 149.99, "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500", "Electronics", 15, 4.8));
            productRepository.save(new Product("Nova Smartwatch Series 5", "Always-on Retina display smartwatch featuring advanced heart rate monitoring, fitness tracking, and cellular connectivity.", 229.00, "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500", "Electronics", 8, 4.5));
            productRepository.save(new Product("Horizon Mechanical Keyboard", "Compact 75% mechanical keyboard with customizable RGB backlighting, hot-swappable tactile switches, and wireless Bluetooth.", 89.99, "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500", "Electronics", 20, 4.6));
            
            productRepository.save(new Product("Classic Denim Jacket", "Timeless regular-fit denim jacket crafted from 100% organic cotton. Features classic button closures and chest pockets.", 69.50, "https://images.unsplash.com/photo-1576995853123-5a10305d93c0?w=500", "Fashion", 30, 4.4));
            productRepository.save(new Product("Aero Running Shoes", "Ultralight, breathable mesh running shoes with responsive foam cushioning and high-grip rubber outsoles for peak performance.", 110.00, "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500", "Fashion", 25, 4.7));
            productRepository.save(new Product("Minimalist Leather Backpack", "Sleek water-resistant leather backpack with a padded 15-inch laptop sleeve, hidden security pocket, and ergonomic straps.", 125.00, "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500", "Fashion", 12, 4.3));
            
            productRepository.save(new Product("Nordic Ceramic Vase Set", "Set of 3 minimalist ceramic vases in organic matte finishes. Perfect for dried botanicals or as standalone accent pieces.", 34.00, "https://images.unsplash.com/photo-1612196808214-b8e1d6145a8c?w=500", "Home", 18, 4.2));
            productRepository.save(new Product("Aroma Diffuser & Humidifier", "Quiet ultrasonic essential oil diffuser with customizable LED ambient lighting and auto-shutoff safety feature.", 45.99, "https://images.unsplash.com/photo-1602928321679-560bb453f190?w=500", "Home", 22, 4.5));
            productRepository.save(new Product("Velvet Accent Cushion", "Soft luxury velvet pillow cover with a hidden zipper. Elevates the look of any sofa, armchair, or bed.", 19.99, "https://images.unsplash.com/photo-1584100936595-c0654b55a2e2?w=500", "Home", 40, 4.6));
            
            productRepository.save(new Product("The Art of Clean Code", "An essential guide to software craftsmanship, offering practical tips and clean patterns for writing maintainable code.", 24.99, "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=500", "Books", 50, 4.9));
            productRepository.save(new Product("Design Systems Handbook", "A comprehensive book on creating cohesive, scalable digital design languages across multiple platforms and products.", 29.99, "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=500", "Books", 35, 4.7));
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> searchAndFilterProducts(String category, String query) {
        boolean hasCategory = category != null && !category.trim().isEmpty() && !"All".equalsIgnoreCase(category);
        boolean hasQuery = query != null && !query.trim().isEmpty();

        if (hasCategory && hasQuery) {
            return productRepository.findByCategoryIgnoreCaseAndNameContainingIgnoreCase(category, query);
        } else if (hasCategory) {
            return productRepository.findByCategoryIgnoreCase(category);
        } else if (hasQuery) {
            return productRepository.findByNameContainingIgnoreCase(query);
        } else {
            return productRepository.findAll();
        }
    }

    public List<String> getAllCategories() {
        return productRepository.findAll().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
