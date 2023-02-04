package com.example.cacvasbe.controllers;

import com.example.cacvasbe.pojo.ApprovalResponse;
import com.example.cacvasbe.pojo.PurchasedProductResponse;
import com.example.cacvasbe.services.products.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadProductData(@RequestParam("file")MultipartFile file) throws IOException {
        this.productService.saveProduct(file);
        return ResponseEntity.ok(Map.of("Message", "Product data uploaded and saved to database") );
    }

    @GetMapping("/filelist")
    public ResponseEntity<?> listOfProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/select-product/{id}")
    public ResponseEntity<?> selectProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.selectProductById(id), HttpStatus.OK);
    }

    @GetMapping("/purchased-productlist/{rcNumber}")
    public ResponseEntity<List<PurchasedProductResponse>> ListOfPurchasedProduct(@PathVariable("rcNumber") String rcNumber) {
        return new ResponseEntity<>(productService.ListOfPurchasedProducts(rcNumber), HttpStatus.OK);
    }

    @DeleteMapping("/delete-product/{productid}")
    public ResponseEntity<ApprovalResponse> deletePurchasedProduct(@PathVariable("productid") Long productId) {
        productService.deletePurchasedProduct(productId);
        return new ResponseEntity<>(new ApprovalResponse("Product deleted successfully"), HttpStatus.OK);
    }



}
