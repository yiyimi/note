# Java learning note

## 2023-01-17

- ResourceHandlerRegistry

```Java

@Configuration
public class WebMvcConfig extend DefaultWebMvcConfig {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String userDirect= System.getProperty("user.idr");
        registry.addResourceHandler("/**), "/upload/**
        .addResourceLocations("file:" + userDir + "//");
    }
}

```
