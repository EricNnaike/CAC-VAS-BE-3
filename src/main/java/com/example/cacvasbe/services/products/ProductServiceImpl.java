package com.example.cacvasbe.services.products;

import com.example.cacvasbe.entities.LicensedPartners;
import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.ProductList;
import com.example.cacvasbe.entities.PurchasedProducts;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.enums.LicensePaymentStatus;
import com.example.cacvasbe.enums.UserAccountStatus;
import com.example.cacvasbe.error_handler.CustomNotFoundException;
import com.example.cacvasbe.pojo.PurchasedProductResponse;
import com.example.cacvasbe.pojo.SelectProductResponse;
import com.example.cacvasbe.repository.LicensePartnerRepository;
import com.example.cacvasbe.repository.PortalUserRepository;
import com.example.cacvasbe.repository.ProductRepository;
import com.example.cacvasbe.repository.PurchasedProductRepository;
import com.example.cacvasbe.security.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final PortalUserRepository portalUserRepository;
    private final LicensePartnerRepository licensePartnerRepository;
    private final PurchasedProductRepository purchasedProductRepository;

    @Override
    public void saveProduct(MultipartFile file) throws IOException {
        if (ExcelUploadService.isValidExcelFile(file)) {
            try {
                List<ProductList> products = ExcelUploadService.getProductDataFromExcel(file.getInputStream());
                this.productRepository.saveAll(products);
            }catch (IOException e) {
                throw new CustomNotFoundException("File is not a valid excel file");
            }
        }
    }

    @Override
    public List<ProductList> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public SelectProductResponse<?> selectProductById(Long id) {
        System.out.println("running.....................");
        String username = UserDetails.getLoggedInUserDetails();
        Optional<PortalUsers> portalUser = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE);
        if (portalUser.isEmpty()) {
            throw new CustomNotFoundException("Portal user not found");
        }
        Optional<LicensedPartners> licensedPartner = licensePartnerRepository.findLicensedPartnersByRcNumberAndPaymentStatus(portalUser.get().getRcNumber(), LicensePaymentStatus.APPROVED);
        if (licensedPartner.isEmpty()) {
            throw new CustomNotFoundException("License user not found");
        }

        Optional<ProductList> product =  productRepository.findProductListById(id);
        if (product.isEmpty()) {
            throw new CustomNotFoundException("Product not found");
        }else{

            List<PurchasedProducts> listOfPurchasedProducts = purchasedProductRepository.findAllByLicensedPartner(licensedPartner.get());

            for(int i=0; i<listOfPurchasedProducts.size();i++){
               if(listOfPurchasedProducts.get(i).getProducts().getId().equals(id)){
                   return new SelectProductResponse<>("Product has already been added", false, licensedPartner.get().getId(), product);
               }
             }
                PurchasedProducts purchasedProduct = new PurchasedProducts();
                purchasedProduct.setLicensedPartner(licensedPartner.get());
                purchasedProduct.setPurchasedAt(LocalDateTime.now());
                purchasedProduct.setProducts(product.get());
                purchasedProduct.setLastUpdatedAt(product.get().getModifiedAt());
                purchasedProduct.setProposed_price(product.get().getProposed_price());
                purchasedProductRepository.save(purchasedProduct);

        }

        return new SelectProductResponse<>("Product added successfully", true, licensedPartner.get().getId(), product);
    }

    @Override
    public List<PurchasedProductResponse> ListOfPurchasedProducts(String rcNumber) {
        LicensedPartners licensedPartner = licensePartnerRepository.findLicensedPartnersByRcNumberAndAccountStatus(rcNumber, UserAccountStatus.VERIFIED)
                .orElseThrow(() -> new CustomNotFoundException("Licensed User not Found"));

        List<PurchasedProducts> purchasedProducts = purchasedProductRepository.findAllByLicensedPartner(licensedPartner);
        List<PurchasedProductResponse> responseList = new ArrayList<>();
        PurchasedProductResponse response = null;
        for (PurchasedProducts x: purchasedProducts) {
            response = new PurchasedProductResponse();
            response.setDescription(x.getProducts().getDescription());
            response.setName(x.getProducts().getProducts());
            response.setId(x.getProducts().getId());
            response.setPrice(x.getProposed_price());
            responseList.add(response);

        }
        System.out.println(responseList);
        return responseList;
    }

    @Override
    public void deletePurchasedProduct(Long productId) {
        String username = UserDetails.getLoggedInUserDetails();
       PortalUsers portalUsers = portalUserRepository.findByUsernameIgnoreCaseAndStatus(username, GenericStatusConstant.ACTIVE)
                .orElseThrow(() -> new CustomNotFoundException("Portal User not found"));

        LicensedPartners licensedPartner = licensePartnerRepository.findLicensedPartnersByUsernameAndAccountStatus(portalUsers.getUsername(), UserAccountStatus.VERIFIED)
                .orElseThrow(() -> new CustomNotFoundException("Invalid Licensed User"));
        purchasedProductRepository.deleteByProductsId(productId);

    }


}
