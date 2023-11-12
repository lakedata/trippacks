package dwu.swcmop.trippacks.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /*@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 서버 응답시 json 을 자바스크립트에서 처리할 수 있음
        config.addAllowedOriginPattern("*");// 모든 ip 에 응답 허용
        config.addAllowedHeader("*"); //모든 header 응답 허용
        config.addExposedHeader("*"); //
        config.addAllowedMethod("*");//모든 요청 메소드 응답 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter();
    }*/

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // "*" 대신 특정 패턴을 지정할 수 있음
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true);
    }
}