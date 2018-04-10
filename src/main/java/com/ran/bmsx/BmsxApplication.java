package com.ran.bmsx;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@SpringBootApplication
@ServletComponentScan("com.ran")
@ComponentScan({
		"com.ran"
})
public class BmsxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsxApplication.class, args);
	}

	@Bean
	public WebMvcConfigurerAdapter adapter() {
		return new WebMvcConfigurerAdapter() {

			/*@Override
			public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
				resolvers.add(exceptionHandlerConfig.exceptionHandlerExceptionResolver());
				resolvers.add(exceptionHandlerConfig.exceptionResolver());
			}*/

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {

				registry.addResourceHandler("/webjars/**")
						.addResourceLocations("classpath:/META-INF/resources/webjars/");

				super.addResourceHandlers(registry);
			}


			@Override
			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
				//  初始化转换器
				Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
				jackson2ObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
				MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(jackson2ObjectMapperBuilder.build());
				converters.add(converter);
			}
		};
	}
}
