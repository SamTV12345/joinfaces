/*
 * Copyright 2016-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.joinfaces.autoconfigure.faces;

import jakarta.faces.webapp.FacesServlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;

public class FacesServletAutoConfigurationTest {

	private WebApplicationContextRunner webApplicationContextRunner;

	@BeforeEach
	public void setUp() {
		this.webApplicationContextRunner = new WebApplicationContextRunner()
				.withConfiguration(AutoConfigurations.of(FacesServletAutoConfiguration.class, JakartaFaces3AutoConfiguration.class));
	}

	@Test
	public void testDefaultMapping() {
		this.webApplicationContextRunner
				.run(context -> {
					ServletRegistrationBean<FacesServlet> facesServletRegistrationBean = (ServletRegistrationBean<FacesServlet>) context.getBean("facesServletRegistrationBean");

					assertThat(facesServletRegistrationBean.getUrlMappings()).containsExactlyInAnyOrder("/faces/*", "*.jsf", "*.faces", "*.xhtml");
				});
	}

	@Test
	public void testCustomMapping() {
		this.webApplicationContextRunner
				.withPropertyValues("joinfaces.faces-servlet.url-mappings=foo,bar")
				.run(context -> {
					ServletRegistrationBean<FacesServlet> facesServletRegistrationBean = (ServletRegistrationBean<FacesServlet>) context.getBean("facesServletRegistrationBean");

					assertThat(facesServletRegistrationBean.getUrlMappings()).containsExactlyInAnyOrder("foo", "bar");
				});
	}

	@Test
	public void testDisableFacesservletToXhtmlDefaultMapping_false() {
		this.webApplicationContextRunner
				.withPropertyValues("joinfaces.faces.disable-facesservlet-to-xhtml=false")
				.run(context -> {
					ServletRegistrationBean<FacesServlet> facesServletRegistrationBean = (ServletRegistrationBean<FacesServlet>) context.getBean("facesServletRegistrationBean");

					assertThat(facesServletRegistrationBean.getUrlMappings()).containsExactlyInAnyOrder("/faces/*", "*.jsf", "*.faces", "*.xhtml");
				});
	}

	@Test
	public void testDisableFacesservletToXhtmlDefaultMapping_true() {
		this.webApplicationContextRunner
				.withPropertyValues("joinfaces.faces.disable-facesservlet-to-xhtml=true")
				.run(context -> {
					ServletRegistrationBean<FacesServlet> facesServletRegistrationBean = (ServletRegistrationBean<FacesServlet>) context.getBean("facesServletRegistrationBean");

					assertThat(facesServletRegistrationBean.getUrlMappings()).containsExactlyInAnyOrder("/faces/*", "*.jsf", "*.faces");
				});
	}

	@Test
	public void testDisableFacesservletToXhtmlCustomMapping() {
		this.webApplicationContextRunner
				.withPropertyValues("joinfaces.faces.disable-facesservlet-to-xhtml=true", "joinfaces.faces-servlet.url-mappings=*.xhtml")
				.run(context -> {
					ServletRegistrationBean<FacesServlet> facesServletRegistrationBean = (ServletRegistrationBean<FacesServlet>) context.getBean("facesServletRegistrationBean");

					assertThat(facesServletRegistrationBean.getUrlMappings()).containsExactly("*.xhtml");
				});
	}

	@Test
	public void testServletContextAttributes_added() {
		this.webApplicationContextRunner
				.run(context -> {
					ServletRegistrationBean<FacesServlet> facesServletRegistrationBean = (ServletRegistrationBean<FacesServlet>) context.getBean("facesServletRegistrationBean");

					ServletContext servletContext = mock(ServletContext.class);

					when(servletContext.addServlet(anyString(), any(Servlet.class))).thenReturn(mock(ServletRegistration.Dynamic.class));

					facesServletRegistrationBean.onStartup(servletContext);

					verify(servletContext, times(2)).setAttribute(any(), any());
				});
	}

	@Test
	public void testServletContextAttributes_notAdded() {
		this.webApplicationContextRunner
				.run(context -> {
					ServletRegistrationBean<FacesServlet> facesServletRegistrationBean = (ServletRegistrationBean<FacesServlet>) context.getBean("facesServletRegistrationBean");

					ServletContext servletContext = mock(ServletContext.class);

					when(servletContext.addServlet(anyString(), any(Servlet.class))).thenReturn(null);

					facesServletRegistrationBean.onStartup(servletContext);

					verify(servletContext, times(0)).setAttribute(any(), any());
				});
	}
}
