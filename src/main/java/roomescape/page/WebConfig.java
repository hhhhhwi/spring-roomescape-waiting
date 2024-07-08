package roomescape.page;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/admin").setViewName("/admin/index");
        registry.addViewController("/admin/reservation").setViewName("admin/reservation");
        registry.addViewController("/admin/time").setViewName("/admin/time");
        registry.addViewController("/admin/theme").setViewName("/admin/theme");
        registry.addViewController("/reservation").setViewName("/reservation");
        registry.addViewController("/reservation-mine").setViewName("/reservation-mine");
        registry.addViewController("/signup").setViewName("/signup");
    }
}
