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

package org.joinfaces.autoconfigure.weld;

import org.jboss.weld.environment.servlet.EnhancedListener;
import org.joinfaces.servlet.ServletContainerInitializerRegistrationBean;
import org.joinfaces.weld.WeldServletContainerInitializerRegistrationBean;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * This class initialize weld in order to have a full cdi environment.
 *
 * @author classicPintus
 */
@AutoConfiguration
@ConditionalOnClass(EnhancedListener.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WeldSpringBootAutoConfiguration {

	@Bean
	public ServletContainerInitializerRegistrationBean<EnhancedListener> weldServletContainerInitializer() {
		return new WeldServletContainerInitializerRegistrationBean();
	}

}
