/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package etk.web.core.impl.request;

import java.util.Map;

import etk.web.core.Response;
import etk.web.core.impl.application.AppContext;
import etk.web.core.impl.application.AppException;
import etk.web.core.impl.controller.desciptor.ControllerMethod;
import etk.web.core.impl.inject.Scoped;
import etk.web.core.impl.inject.ScopingContext;
import etk.web.core.impl.spi.request.ActionEvent;
import etk.web.core.impl.spi.request.RenderEvent;
import etk.web.core.impl.spi.request.RequestEvent;
import etk.web.core.impl.spi.request.ResourceEvent;
import etk.web.core.request.ActionContext;
import etk.web.core.request.RenderContext;
import etk.web.core.request.RequestContext;
import etk.web.core.request.ResourceContext;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 25, 2012  
 */
public class Request implements ScopingContext {

  private final AppContext appContext;
  
  private final RequestEvent requestEvent;
  
  private final RequestContext context;
  
  private final Map<String, String[]> parameters;
  
  private final Object[] args;
  
  private Response response;
  
  public Request(AppContext appContext, ControllerMethod method, Map<String, String[]> parameters, Object[] args, RequestEvent event) {
    RequestContext context = null;
    
    if (event instanceof RenderEvent) {
      context = new RenderContext(this, appContext, method, (RenderEvent) event);
    } else if (event instanceof ActionEvent) {
      context = new ActionContext(this, appContext, method, (ActionEvent) event);
    } else {
      context = new ResourceContext(this, appContext, method, (ResourceEvent) event);
    }
    
    this.context = context;
    this.requestEvent = event;
    this.args = args;
    this.parameters = parameters;
    this.appContext = appContext;
    
  }
  
  
  
  public Response getResponse() {
    return response;
  }



  public void setResponse(Response response) {
    this.response = response;
  }



  public Map<String, String[]> getParameters() {
    return parameters;
  }



  @Override
  public Scoped getContextualValue(Scoped scope, Object key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setContextualValue(Scoped scoped, Object key, Scoped value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isActive(Scoped scope) {
    // TODO Auto-generated method stub
    return false;
  }
  
  private int index = 0;
  
  public void invoke() throws AppException {
    
  }

}
