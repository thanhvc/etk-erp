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

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etk.web.core.impl.inject.Scoped;
import etk.web.core.impl.spi.request.RenderEvent;
import etk.web.core.request.Phase;
import etk.web.core.request.Response;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 22, 2012  
 */
public class ServletRenderEvent extends ServletMimeEvent implements RenderEvent {
  public ServletRenderEvent(HttpServletRequest req, HttpServletResponse resp, Map<String, String[]> parameters) {
    
    super(req, resp, parameters);
  }
  
  @Override
  public void setTitle(String title) {
    
  }

  @Override
  public String renderURL(Phase phase, Boolean escapeXML, Map<String, String[]> parameters) {
    
    return null;
  }

  @Override
  public Scoped getRequestValue(Object key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setRequestValue(Object key, Scoped value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Scoped getSessionValue(Object key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setSessionValue(Object key, Scoped value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Scoped getIdentityValue(Object key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setIdentityValue(Object key, Scoped value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setResponse(Response response) throws IllegalStateException, IOException {
    
    
  }

  @Override
  public String getContextPath() {
    // TODO Auto-generated method stub
    return null;
  }

    
  

}
