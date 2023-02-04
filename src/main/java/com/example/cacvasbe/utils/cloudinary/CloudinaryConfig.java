package com.example.cacvasbe.utils.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Value("dwwpp4esc")
    private String cloudName;
    @Value("951837282891627")
    private String apiKey;
    @Value("N4b_g91WqXEHhDIAojN7i9y12mg")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary;
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        config.put("secure", true);
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }

}
