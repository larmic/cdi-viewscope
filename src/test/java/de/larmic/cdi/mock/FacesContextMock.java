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
package de.larmic.cdi.mock;

import org.mockito.Mockito;

import javax.enterprise.inject.Alternative;
import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Use {@link de.larmic.cdi.mock.FacesContextMock#mockFacesContext()} to create a new instance of {@link javax.faces.context.FacesContext}.
 */
@Alternative
public class FacesContextMock extends FacesContextWrapper {

    private FacesContext facesContextMock;

    private FacesContextMock() {
        final Map<String, Object> sessionMap = new HashMap<>();
        final Map<String, Object> viewMaps = new HashMap<>();

        sessionMap.put("com.sun.faces.activeViewMaps", viewMaps);

        final UIViewRoot uiViewRoot = new UIViewRoot();
        final Application application = Mockito.mock(Application.class);
        final ExternalContext externalContext = Mockito.mock(ExternalContext.class);

        this.facesContextMock = Mockito.mock(FacesContext.class);

        Mockito.when(facesContextMock.getViewRoot()).thenReturn(uiViewRoot);
        Mockito.when(facesContextMock.getApplication()).thenReturn(application);
        Mockito.when(facesContextMock.getExternalContext()).thenReturn(externalContext);

        Mockito.when(externalContext.getSessionMap()).thenReturn(sessionMap);
    }

    @Override
    public FacesContext getWrapped() {
        return facesContextMock;
    }

    public static FacesContext mockFacesContext() {
        setCurrentInstance(new FacesContextMock());
        return getCurrentInstance();
    }
}
