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
package org.etk.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Feb 11, 2012  
 */
public class ControllerContext {

  /** . */
  private final HttpServletRequest request;
  
  /** . */
  private final HttpServletResponse response;
  
  /** . */
  private final WebAppController controller;
  
  /** . */
  private final String contextName;
  
  public ControllerContext(WebAppController controller, HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
    this.controller = controller;
    this.contextName = request.getContextPath().substring(1);
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public WebAppController getController() {
    return controller;
  }

  public String getContextName() {
    return contextName;
  }
    
}
