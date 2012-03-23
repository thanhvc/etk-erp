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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import etk.web.core.impl.inject.Scoped;
import etk.web.core.impl.inject.ScopedContext;
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
    
    for(Map.Entry<String, String[]> entry : ((Map<String, String[]>)req.getParameterMap()).entrySet()) {
      String name = entry.getKey();
      String[] value = entry.getValue();
      if (name.equals("etk.phase")) {
        phase = Phase.valueOf(value[0]);
        break;
      } 
    }
    
    switch (phase) {
      case RENDER:
        return new ServletRenderEvent(req, resp);
      case ACTION:
        return new ServletActionEvent(req, resp);
      case RESOURCE:
        return new ServletResourceEvent(req, resp);
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
                      HttpServletResponse resp) {
    this.req = req;
    this.resp = resp;
    //need to parser the parameters from HttpServletRequest
    Map<String, String[]> parameters = req.getParameterMap();
    
    String methodId= req.getParameter("op");
    if (methodId != null)
    {
       parameters = new HashMap<String, String[]>(parameters);
       parameters.remove("op");
    }

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
  
  public String getContextPath() {
    return req.getContextPath();
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
  
  public final Scoped getIdentityValue(Object key) {
    return null;
  }

  public final void setIdentityValue(Object key, Scoped value) {
  }
  
  @Override
  public final Scoped getRequestValue(Object key) {
    ScopedContext context = getRequestContext(false);
    return context != null ? context.get(key) : null;
  }
  
  public final void setRequestValue(Object key, Scoped value) {
   //FIXME tell him to update this code. value == null 
   if (value == null) {
     ScopedContext context = getRequestContext(false);
     if (context != null) {
       context.set(key, null);
     }
   } else {
     getRequestContext(true).set(key, value);
   }
  }
  
  @Override
  public final void setSessionValue(Object key, Scoped value) {
    if (value == null) {
      ScopedContext context = getSessionContext(false);
      if (context != null) {
        context.set(key, null);
      }
    } else {
      getSessionContext(true).set(key, value);
    }
  }
  
  @Override
  public final Scoped getSessionValue(Object key) {
    ScopedContext context = getSessionContext(false);
    return context != null ? context.get(key) : null;
  }
  
  /**
   * Build the URL to provide the redirect(url) to servlet to process the next phase.
   * 
   * @param phase this phase render, action or resource
   * @param escapeXML
   * @param parameters the parameters to set for URL
   * @return String 
   */
  public final String renderURL(Phase phase, Boolean escapeXML, Map<String, String[]> parameters) {
    StringBuilder buffer = new StringBuilder();
    
    buffer.append(req.getScheme());
    buffer.append("://");
    buffer.append(req.getServerName());
    int port = req.getServerPort();
    if (port != 80) {
      buffer.append(":").append(port);
    }
    
    buffer.append(req.getContextPath());
    buffer.append(req.getServletPath());
    buffer.append("?etk.phase=").append(phase);
    
    for (Map.Entry<String, String[]> parameter : parameters.entrySet()) {
      String name = parameter.getKey();
      
      try {
        String encName = URLEncoder.encode(name, "UTF-8");
        for (String value : parameter.getValue()) {
          String encValue = URLEncoder.encode(value, "UTF-8");
          buffer.append("&").append(encName).append("=").append(encValue);
        }
      } catch(UnsupportedEncodingException e) {
        //Should not happen
        throw new AssertionError(e);
      }
    }
    
    return buffer.toString();
  }
  
  /**
   * Gets the {@link ScopedContext} which holds {@link Scoped} instances with Request Scoped
   * @param create
   * @return
   */
  protected final ScopedContext getRequestContext(boolean create) {
    ScopedContext context = (ScopedContext)req.getAttribute("org.etk.request_scoped");
    
    if (context == null && create) {
      req.setAttribute("org.etk.request_scoped", context = new ScopedContext());
    }
    
    return context;
  }

  /**
   * Gtes the {@link ScopedContext} which holds {@link Scoped} instance with Session Scoped
   * @param create TRUE/FALSE
   * @return
   */
  protected final ScopedContext getSessionContext(boolean create) {
    ScopedContext context = null;
    HttpSession session = req.getSession(create);
    
    if (session != null) {
      context = (ScopedContext) session.getAttribute("org.etk.session_scope");
      if (context == null && create) {
        session.setAttribute("org.etk.session_scope", context = new ScopedContext());
      }
    }
    return context;
  }
  
  /**
   * Clear the {@link Scoped} instances with Scoped Request
   */
  public void close() {
    ScopedContext context = getRequestContext(false);
    if (context != null) {
      context.close();
    }
    
  }

}
