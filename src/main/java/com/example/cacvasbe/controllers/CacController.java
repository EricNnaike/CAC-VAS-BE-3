

package com.example.cacvasbe.controllers;

import com.example.cacvasbe.dto.CACSearchLoginRequestPojo;
import com.example.cacvasbe.dto.CompanySearchDto;
import com.example.cacvasbe.pojo.CACSearchLoginResponsePojo;
import com.example.cacvasbe.pojo.JsonResponse;
import com.google.gson.Gson;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CacController {

    @PostMapping("/cac")
    public ResponseEntity<?> searchByRcNumber(@Valid @RequestBody CompanySearchDto companySearchDto,
                                              BindingResult bindingResult,
                                              HttpServletResponse response,
                                              HttpServletRequest request,
                                              Authentication authentication) {
//        ApiError apiError = null;
//
//        if (bindingResult.hasErrors()) {
//            return new BindingResultException().bindingErrorException(bindingResult);
//        }

        try {

            String baseUrl = "https://searchapp.cac.gov.ng/searchapp";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", "application/json");
            headers.add("Content-Type", "application/json");
            CACSearchLoginRequestPojo cacSearchLoginRequestPojo = new CACSearchLoginRequestPojo();

            cacSearchLoginRequestPojo.setUsername("firscacapi");
            cacSearchLoginRequestPojo.setPassword("firscacapi2021");
            String result = restTemplate.postForObject(baseUrl+"/api/auth/login", cacSearchLoginRequestPojo, String.class);



            Gson gson = new Gson();
            CACSearchLoginResponsePojo responseToken = gson.fromJson(result, CACSearchLoginResponsePojo.class);

            System.out.println("here is the response: " + responseToken.getToken());


            MultiValueMap<String, String> headersAuth = new LinkedMultiValueMap<String, String>();
            Map map = new HashMap<String, String>();
            map.put("Content-Type", "application/json");
            map.put("Authorization", "Bearer "+responseToken.getToken());
            headersAuth.setAll(map);

            System.out.println(responseToken.getToken());
            String url = baseUrl+"/api/report/find/company/info/"+companySearchDto.getRcNumber()+"/2";

            ResponseEntity<Object> responseRC = null;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(headersAuth);
            try {

                responseRC = restTemplate.exchange(builder.toUriString(),
                        HttpMethod.GET,
                        entity, Object.class);
                System.out.println("Here is the response:::" + responseRC.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(new JsonResponse<>("message", responseRC.getBody()));
        }
        catch (Exception e) {
        }

        return null;


    }
}
