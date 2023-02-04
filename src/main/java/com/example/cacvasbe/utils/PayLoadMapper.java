//package com.example.cacvasbe.utils;
//
//import com.example.cacvasbe.entities.User;
//import com.example.cacvasbe.pojo.RegistrationResponse;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PayLoadMapper {
//
//    public RegistrationResponse preLicensedPartnerEntityMapper(User preLicensedPartner) {
//        RegistrationResponse registrationResponse = new RegistrationResponse();
//
//        if (preLicensedPartner.getId() != null) {
//            registrationResponse.setId(preLicensedPartner.getId());
//        }
//
//        if (preLicensedPartner.getFirstName() != null) {
//            registrationResponse.setName(preLicensedPartner.getFirstName());
//        }
//
//        if (preLicensedPartner.getEmail() != null) {
//            registrationResponse.setEmail(preLicensedPartner.getEmail());
//        }
//        return registrationResponse;
//    }
//
//}
