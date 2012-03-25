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
package etk.web.core.request;

import java.util.Map;

import etk.web.core.impl.application.AppContext;
import etk.web.core.impl.controller.desciptor.ControllerMethod;
import etk.web.core.impl.request.Request;
import etk.web.core.impl.spi.request.RequestEvent;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 21, 2012  
 */
public abstract class RequestContext {

  protected final AppContext appContext;
  
  protected final ControllerMethod method;
  
  protected final Request request;
  
  public RequestContext(Request request, AppContext appContext, ControllerMethod method) {
    this.request = request;
    this.appContext = appContext;
    this.method = method;
  }

  public AppContext getAppContext() {
    return appContext;
  }

  public ControllerMethod getMethod() {
    return method;
  }
  
  public Map<String, String[]> getParameters() {
    return request.getParameters();
  }
  
  public HttpContext getHttpContext() {
    return getRequestEvent().getHttpContext();
  }
  
  public abstract Phase getPhase();
  
  protected abstract RequestEvent getRequestEvent();
}
