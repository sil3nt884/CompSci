package com.fileencryptor;

import java.io.File;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * 
 *Java Config for Java Beans 
 * 
 * @author Richard, Adam, Curries
 *
 */


@Configuration
@ComponentScan("com.fileencryptor")
@Import({ThymeleafAutoConfiguration.class, DispatcherServlet.class, StandardServletMultipartResolver.class, MultipartAutoConfiguration.class })
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	/**
	 * Creates a ViewResolver allows HTML to be render and viewed.
	 * 
	 * @return ViewResolver
	 */
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());

		return resolver;
	}
	
	/**
	 * Tells the server to user Spring template Engine#
	 * 
	 * @return TemplateEngine
	 */

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());

		return engine;
	}
	
	/**
	 * Creates a JettyEmbededServer to expose endpoints
	 * 
	 * @return JettyEmbeddedServletContainerFactory
	 */

	@Bean
	public JettyEmbeddedServletContainerFactory jetty() {
		JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
		factory.setPort(8080);
		factory.setDocumentRoot(new File("web"));
		return factory;
	}

	/**
	 * Tells the resovles to user HTML parsing
	 * @return  ITemplateResolver
	 */

	@Bean
	public ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;

	}
}