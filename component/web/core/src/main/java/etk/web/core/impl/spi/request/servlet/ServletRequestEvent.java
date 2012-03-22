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
package etk.web.core.impl.spi.request.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etk.web.core.impl.spi.request.RequestEvent;
import etk.web.core.request.HttpContext;
import etk.web.core.request.Phase;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 22, 2012  
 */
public abstract class ServletRequestEvent implements RequestEvent, HttpContext {

  /**
   * @FIXME Need to create the ServletEventFactory to provide the kind of Servlet Event.
   * 
   * Create the RequestEvent which base on HttpServletRequest and HttpServletResponse
   * @param req
   * @param resp
   * @return
   */
  public static ServletRequestEvent create(HttpServletRequest req, HttpServletResponse resp) {
    //default is always RENDER phase.
    Phase phase = Phase.RENDER;
    Map<String, String[]> parameters = new HashMap<String, String[]>();
    
    for(Map.Entry<String, String[]> entry : ((Map<String, String[]>)req.getParameterMap()).entrySet()) {
      
      String name = entry.getKey();
      String[] value = entry.getValue();
      if (name.equals("juzu.phase")) {
        phase = Phase.valueOf(value[0]);
      } else {
        parameters.put(name, value);
      }
    }
    
    switch (phase) {

      case RENDER:
        return new ServletRenderEvent(req, resp, parameters);
      case ACTION:
        return new ServletActionEvent(req, resp, parameters);
      case RESOURCE:
        return new ServletResourceEvent(req, resp, parameters);
      default:
        throw new UnsupportedOperationException("todo");

    }
  }
  
  /** . */
  final HttpServletRequest req;
  /** . */
  final HttpServletResponse resp;
  
  final Map<String, String[]> parameters;
  
  ServletRequestEvent(HttpServletRequest req,
                      HttpServletResponse resp,
                      Map<String, String[]> parameters) {
    this.req = req;
    this.resp = resp;
    this.parameters = parameters;
  }
  
  public Cookie[] getCookies() {
    return req.getCookies();
  }
  
  public String getScheme() {
    return req.getScheme();
  }
  
  public int getServerPort() {
    return req.getServerPort();
  }
  
  public String getServerName() {
    return req.getServerName();
  }
  
  public final String getNamespace() {
    return "window_ns";
  }
  
  public final String getId() {
    return "window_id";
  }

  public final Map<String, String[]> getParameters() {
    return parameters;
  }

  public final HttpContext getHttpContext() {
    return this;
  }

}
