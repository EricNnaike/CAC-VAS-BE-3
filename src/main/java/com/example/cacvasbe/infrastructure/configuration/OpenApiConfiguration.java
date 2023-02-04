//package com.example.cacvasbe.infrastructure.configuration;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springdoc.core.GroupedOpenApi;
//import org.springdoc.core.SpringDocUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.view.RedirectView;
//
//
///**
// * @author uhuegbulem chinomso
// * email: chimaisaac60@gmail.com
// * Oct, 2022
// **/
//
//@Configuration
//public class OpenApiConfiguration {
//
//    public static final String SEC_SCHEME_NAME = "ApiKey";
//
//    static {
//        SpringDocUtils.getConfig().addResponseTypeToIgnore(RedirectView.class);
//    }
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes(SEC_SCHEME_NAME,
//                                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
//                                        .in(SecurityScheme.In.HEADER)
//                                        .name("Authorization"))
//                )
//                .info(new Info().title("ANAMBRA API").version("1.0")
//                        .contact(contact())
//                        .description(
//                                "The ANAMBRA RESTful service using springdoc-openapi and OpenAPI 3."));
//    }
//
//    private Contact contact() {
//        Contact contact = new Contact();
//        contact.asetName("Oasis Bot");
//        contact.setEmail("uchinomso@oasismgt.net");
//        return contact;
//    }
//
//    @Bean
//    public GroupedOpenApi anambraAppOpenApi() {
//        String[] paths = {
//                "/**"};
//        return GroupedOpenApi.builder().group("anambraOpenApi").pathsToMatch(paths).build();
//    }
//
//}
