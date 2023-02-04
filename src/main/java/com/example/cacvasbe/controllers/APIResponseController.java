package com.example.cacvasbe.controllers;

import com.example.cacvasbe.dto.product.AffiliateRequest;
import com.example.cacvasbe.dto.product.HistoryOfDeniedNameRequest;
import com.example.cacvasbe.dto.product.SearchForAffiliationsRequest;
import com.example.cacvasbe.entities.Company;
import com.example.cacvasbe.pojo.APIRequestResponse;
import com.example.cacvasbe.services.products.requests.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class APIResponseController {

    private final ResponseService responseService;

    @GetMapping("/company-info/{rcNumber}")
    public ResponseEntity<Company> companyDetails(@PathVariable("rcNumber") String rcNumber) {
        Company response = responseService.findCompany(rcNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/affiliates-info/{publicKey}")
    public ResponseEntity<APIRequestResponse<?>> getAffiliates(@RequestBody AffiliateRequest affiliateRequest, @PathVariable("publicKey") String publicKey) {
        APIRequestResponse<?> response = responseService.findAffiliateByAffiliateType(affiliateRequest, publicKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deniedName-history/{publicKey}")
    public ResponseEntity<APIRequestResponse<?>> getAffiliates(@RequestBody HistoryOfDeniedNameRequest request, @PathVariable("publicKey") String publicKey) {
        APIRequestResponse<?> response = responseService.historyOfDeniedNames(request, publicKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search-affiliate-forName/{publicKey}")
    public ResponseEntity<APIRequestResponse<?>> searchAffiliationsForName(@RequestBody SearchForAffiliationsRequest request, @PathVariable("publicKey") String publicKey) {
        APIRequestResponse<?> response = responseService.searchAffiliationsForName(request, publicKey);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
