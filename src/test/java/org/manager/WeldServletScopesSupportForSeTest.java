/**
 * Copyright 2012 Lars Michaelis
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
package org.manager;

import de.larmic.cdi.context.ConversationScopeBean;
import de.larmic.cdi.context.RequestScopeBean;
import de.larmic.cdi.context.SessionScopeBean;
import de.larmic.cdi.context.ViewScopeBean;
import de.larmic.cdi.mock.FacesContextMock;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class WeldServletScopesSupportForSeTest extends Arquillian {

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

    @Test(dependsOnMethods = {"testInjection"})
    public void testSayHello() {
        Assert.assertEquals("de.larmic.cdi.context.RequestScopeBean says hello", this.requestScopeBean.sayHello());
        Assert.assertEquals("de.larmic.cdi.context.SessionScopeBean says hello", this.sessionScopeBean.sayHello());
        Assert.assertEquals("de.larmic.cdi.context.ViewScopeBean says hello", this.viewScopeBean.sayHello());
        Assert.assertEquals("de.larmic.cdi.context.ConversationScopeBean says hello", this.conversationScopeBean.sayHello());
    }

    @Test(dependsOnMethods = {"testInjection", "testSayHello"})
    public void testViewScopeDoesNotChange() {
        FacesContextMock.mockFacesContext();

        Assert.assertEquals(this.viewScopeBean.toString(), this.viewScopeBean.toString());
    }
}
