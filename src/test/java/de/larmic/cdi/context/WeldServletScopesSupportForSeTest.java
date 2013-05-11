/**
 * Copyright 2013 Lars Michaelis
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package de.larmic.cdi.context;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.larmic.cdi.mock.FacesContextMock;

public class WeldServletScopesSupportForSeTest extends Arquillian {

	private static final String BEAN_PKG_NAME = "de.larmic.cdi.context.";
	
	@Inject
	private RequestScopeBean requestScopeBean;

	@Inject
	private SessionScopeBean sessionScopeBean;

	@Inject
	private ConversationScopeBean conversationScopeBean;

	@Inject
	private ViewScopeBean viewScopeBean;

	@Deployment
	public static JavaArchive createdeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(RequestScopeBean.class)
				.addClass(SessionScopeBean.class)
				.addClass(ConversationScopeBean.class)
				.addClass(ViewScopeBean.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
	}

	@Test
	public void testInjection() {
		Assert.assertNotNull(this.requestScopeBean);
		Assert.assertNotNull(this.sessionScopeBean);
		Assert.assertNotNull(this.conversationScopeBean);
		Assert.assertNotNull(this.viewScopeBean);
	}

	@Test(dependsOnMethods = { "testInjection" })
	public void testSayHello() {
		Assert.assertEquals(this.requestScopeBean.sayHello(), BEAN_PKG_NAME + "RequestScopeBean says hello");
		Assert.assertEquals(this.sessionScopeBean.sayHello(), BEAN_PKG_NAME + "SessionScopeBean says hello");
		Assert.assertEquals(this.viewScopeBean.sayHello(), BEAN_PKG_NAME + "ViewScopeBean says hello");
		Assert.assertEquals(this.conversationScopeBean.sayHello(), BEAN_PKG_NAME + "ConversationScopeBean says hello");
	}

	@Test(dependsOnMethods = { "testInjection", "testSayHello" })
	public void testViewScope() {
		FacesContextMock.mockFacesContext();

		// in a previous version of cdi scope extension this was failing
		Assert.assertEquals(this.viewScopeBean.toString(), this.viewScopeBean.toString());
	}
}
