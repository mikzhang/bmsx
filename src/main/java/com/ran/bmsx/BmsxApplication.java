package com.ran.bmsx;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ran.bmsx.core.auth.EtpCache;
import com.ran.bmsx.core.auth.UserRealm;
import com.ran.bmsx.core.exception.ExceptionHandler;
import com.ran.bmsx.core.utils.RedisUtil;
import com.wf.etp.authz.ApiInterceptor;
import com.wf.etp.authz.IEtpCache;
import com.wf.etp.authz.IUserRealm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
	public RedisConnectionFactory redisConnectionFactory(){
		return new JedisConnectionFactory();
	}

	@Bean
	public StringRedisTemplate redisTemplate(){
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory());
		return template;
	}

	@Bean
	public RedisUtil redisUtil(){
		RedisUtil redisUtil = new RedisUtil();
		redisUtil.setRedisTemplate(redisTemplate());
		return redisUtil;
	}

	@Bean
	public IUserRealm userRealm(){
		return new UserRealm();
	}

	@Bean
	public IEtpCache etpCache(){
		return new EtpCache();
	}

	@Bean
	public WebMvcConfigurerAdapter adapter() {
		return new WebMvcConfigurerAdapter() {

			@Override
			public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
				resolvers.add(new ExceptionHandler());
			}

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {

				registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
				registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
				registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");

				super.addResourceHandlers(registry);
			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				// 多个拦截器组成一个拦截器链
				// addPathPatterns 用于添加拦截规则
				// excludePathPatterns 用户排除拦截
				ApiInterceptor apiInterceptor = new ApiInterceptor();
				apiInterceptor.setCache(etpCache());
				apiInterceptor.setUserRealm(userRealm());
                registry.addInterceptor(new ApiInterceptor()).addPathPatterns("/api/**")
                        .excludePathPatterns("/api/login");
                super.addInterceptors(registry);
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

	@Autowired
	private DispatcherServlet dispatcherServlet;

	@Bean
	public ServletRegistrationBean apiServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
		//注入上传配置到自己注册的ServletRegistrationBean
		bean.addUrlMappings("/api/*","/","/static/*","/templates/*","/templates/index.html","/templates/login.html");
		bean.setName("apiServlet");
		return bean;
	}
}
