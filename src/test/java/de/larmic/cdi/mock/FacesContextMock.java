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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.enterprise.inject.Alternative;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

import org.mockito.Mockito;

@Alternative
public class FacesContextMock extends FacesContext {

	private final Map<String, Object> sessionMap = new HashMap<String, Object>();

	private final Map<String, Object> viewMaps = new HashMap<String, Object>();
	
	private UIViewRoot uiViewRoot;
	private final Application application;
	private final ExternalContext externalContext;

	public FacesContextMock() {
		super();

		this.sessionMap.put("com.sun.faces.activeViewMaps", this.viewMaps);
		
		this.uiViewRoot = new UIViewRoot();
		this.application = Mockito.mock(Application.class);
		this.externalContext = Mockito.mock(ExternalContext.class);
		
		Mockito.when(this.externalContext.getSessionMap()).thenReturn(this.sessionMap);
	}

	public static FacesContext mockFacesContext() {
		setCurrentInstance(new FacesContextMock());
		return getCurrentInstance();
	}

	@Override
	public Application getApplication() {
		return this.application;
	}

	@Override
	public Iterator<String> getClientIdsWithMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExternalContext getExternalContext() {
		return this.externalContext;
	}

	@Override
	public Severity getMaximumSeverity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<FacesMessage> getMessages(final String clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RenderKit getRenderKit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getRenderResponse() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getResponseComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResponseStream getResponseStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResponseStream(final ResponseStream responseStream) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResponseWriter getResponseWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResponseWriter(final ResponseWriter responseWriter) {
		// TODO Auto-generated method stub

	}

	@Override
	public UIViewRoot getViewRoot() {
		return this.uiViewRoot;
	}

	@Override
	public void setViewRoot(final UIViewRoot root) {
		this.uiViewRoot = root;
	}

	@Override
	public void addMessage(final String clientId, final FacesMessage message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderResponse() {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseComplete() {
		// TODO Auto-generated method stub

	}
}
