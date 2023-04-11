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

## 2023-01-28 14:33:15

- feignClient

[高德地图接口-地理/逆地理编码](https://developer.amap.com/api/webservice/guide/api/georegeo)
[feign使用网络代理发起请求](https://www.jianshu.com/p/9342d0ac5ce0)