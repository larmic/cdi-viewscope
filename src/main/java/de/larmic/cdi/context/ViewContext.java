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
package de.larmic.cdi.context;

import org.jboss.weld.context.AbstractBoundContext;
import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.beanstore.MapBeanStore;
import org.jboss.weld.context.beanstore.SimpleNamingScheme;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class ViewContext extends AbstractBoundContext<Map<String, Object>> implements Context,
        BoundContext<Map<String, Object>> {

    private static final String IDENTIFIER = ViewContext.class.getName();

    public ViewContext(final String contextId) {
        super(contextId, false);
    }

    public Class<? extends Annotation> getScope() {
        return ViewScoped.class;
    }

    @Override
    public <T> T get(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {
        final T beanFromViewMap = this.get(contextual);

        if (beanFromViewMap == null) {
            final Bean<T> bean = (Bean<T>) contextual;
            final T t = bean.create(creationalContext);
            this.getViewMap().put(bean.getName(), t);
            return t;
        }

        return beanFromViewMap;
    }

    @Override
    public <T> T get(final Contextual<T> contextual) {
        final Bean<T> bean = (Bean<T>) contextual;
        final Map<String, Object> viewMap = this.getViewMap();
        if (viewMap.containsKey(bean.getName())) {
            @SuppressWarnings("unchecked")
            final T viewMapBean = (T) viewMap.get(bean.getName());
            return viewMapBean;
        }

        return null;
    }

    private Map<String, Object> getViewMap() {
        final FacesContext context = FacesContext.getCurrentInstance();

        if (context != null) {
            final UIViewRoot viewRoot = context.getViewRoot();
            return viewRoot.getViewMap(true);
        } else {
            // TODO [larmic] should never happen. log error
            return new HashMap<String, Object>();
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public boolean associate(final Map<String, Object> storage) {
        if (this.getBeanStore() == null) {
            storage.put(IDENTIFIER, IDENTIFIER);
            this.setBeanStore(new MapBeanStore(new SimpleNamingScheme(IDENTIFIER), storage));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean dissociate(final Map<String, Object> storage) {
        if (storage.containsKey(IDENTIFIER)) {
            try {
                storage.remove(IDENTIFIER);
                this.setBeanStore(null);
                return true;
            } finally {
                this.cleanup();
            }

        } else {
            return false;
        }
    }

}
