/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 提供配置应用的核心接口.当应用在运行时 ApplicationContext 应该是只读，但是可以重加载如果实现支持的话。
 *
 * <p>
 * 一个 ApplicationContext 提供:
 * <ul>
 * <li>访问应用组件的bean工厂方法，继承自 ListableBeanFactory
 * <li>通用方式加载文件资源的能力，继承自 ResourceLoader
 * <li>发布对应的事件到对应的监听器，继承自 ApplicationEventPublisher
 * <li>解析消息，支持国际化的能力，继承自 MessageSource
 * <li>继承自一个父上下文. 祖先上下文中的定义优先级比较高，比如整个Web应用有一个 parent context，每个 servlet 有自己的 child
 * context。
 * </ul>
 *
 * <p>
 * 除了标准的 BeanFactory 的生命周期能力，ApplicationContext的实现会探测
 * ApplicationContextAware，ResourceLoaderAware，ApplicationEventPublisherAware，MessageSourceAware
 * 的bean。
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see ConfigurableApplicationContext
 * @see org.springframework.beans.factory.BeanFactory
 * @see org.springframework.core.io.ResourceLoader
 */
public interface ApplicationContext
		extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
		MessageSource, ApplicationEventPublisher, ResourcePatternResolver {

	/**
	 * 返回该应用上下文的唯一ID
	 * 
	 * @return the unique id of the context, or {@code null} if none
	 */
	String getId();

	/**
	 * 该上下文所属的部署的应用的名称
	 * 
	 * @return a name for the deployed application, or the empty String by default
	 */
	String getApplicationName();

	/**
	 * 该上下文的友好名称
	 * 
	 * @return a display name for this context (never {@code null})
	 */
	String getDisplayName();

	/**
	 * 该上下文初次加载的时间戳（ms）
	 * 
	 * @return the timestamp (ms) when this context was first loaded
	 */
	long getStartupDate();

	/**
	 * 返回父上下文，如果是根上下文则返回null
	 * 
	 * @return the parent context, or {@code null} if there is no parent
	 */
	ApplicationContext getParent();

	/**
	 * Expose AutowireCapableBeanFactory functionality for this context.
	 * <p>
	 * This is not typically used by application code, except for the purpose of
	 * initializing bean instances that live outside of the application context, applying
	 * the Spring bean lifecycle (fully or partly) to them.
	 * <p>
	 * Alternatively, the internal BeanFactory exposed by the
	 * {@link ConfigurableApplicationContext} interface offers access to the
	 * {@link AutowireCapableBeanFactory} interface too. The present method mainly serves
	 * as a convenient, specific facility on the ApplicationContext interface.
	 * <p>
	 * <b>NOTE: As of 4.2, this method will consistently throw IllegalStateException after
	 * the application context has been closed.</b> In current Spring Framework versions,
	 * only refreshable application contexts behave that way; as of 4.2, all application
	 * context implementations will be required to comply.
	 * 
	 * @return the AutowireCapableBeanFactory for this context
	 * @throws IllegalStateException if the context does not support the
	 *         {@link AutowireCapableBeanFactory} interface, or does not hold an
	 *         autowire-capable bean factory yet (e.g. if {@code refresh()} has never been
	 *         called), or if the context has been closed already
	 * @see ConfigurableApplicationContext#refresh()
	 * @see ConfigurableApplicationContext#getBeanFactory()
	 */
	AutowireCapableBeanFactory getAutowireCapableBeanFactory()
			throws IllegalStateException;

}
