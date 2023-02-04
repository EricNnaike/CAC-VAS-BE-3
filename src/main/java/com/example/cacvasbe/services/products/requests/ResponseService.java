package com.example.cacvasbe.services.products.requests;

import com.example.cacvasbe.dto.product.AffiliateRequest;
import com.example.cacvasbe.dto.product.HistoryOfDeniedNameRequest;
import com.example.cacvasbe.dto.product.SearchForAffiliationsRequest;
import com.example.cacvasbe.entities.Affiliates;
import com.example.cacvasbe.entities.Company;
import com.example.cacvasbe.pojo.APIRequestResponse;

import java.util.List;

public interface ResponseService {
    Company findCompany(String rcNumber);
    List<Affiliates> findAffiliate(AffiliateRequest affiliateRequest);
    APIRequestResponse<?> findAffiliateByAffiliateType(AffiliateRequest affiliateRequest, String publicKey);
    APIRequestResponse<?> historyOfDeniedNames(HistoryOfDeniedNameRequest request, String publicKey);
    APIRequestResponse<?> searchAffiliationsForName(SearchForAffiliationsRequest searchForAffiliationsRequest, String publicKey);


//    List<Affiliates> findAffiliate(String rcNumber, Long affiliateTypeId);


}
