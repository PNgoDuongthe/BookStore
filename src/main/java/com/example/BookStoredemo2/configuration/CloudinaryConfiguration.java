package com.example.BookStoredemo2.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {
    private final String CLOUD_NAME ="djdjlx9j8";
    private final String API_KEY = "443731641216231";
    private final String API_SECRET = "3C6HnGaCZ2l9d_g1qFRwGlA_u6k";

    @Bean
    public Cloudinary cloudinary(){
        Map<String ,String > config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);
        return new Cloudinary(config);
    }

}
