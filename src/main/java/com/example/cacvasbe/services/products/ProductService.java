package com.example.cacvasbe.services.products;

import com.example.cacvasbe.entities.ProductList;
import com.example.cacvasbe.pojo.ApprovalResponse;
import com.example.cacvasbe.pojo.PurchasedProductResponse;
import com.example.cacvasbe.pojo.SelectProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void saveProduct(MultipartFile file) throws IOException;
    List<ProductList> getProducts();
    SelectProductResponse<?> selectProductById(Long id);
    List<PurchasedProductResponse> ListOfPurchasedProducts(String rcNumber);
    void deletePurchasedProduct(Long productId);


}
