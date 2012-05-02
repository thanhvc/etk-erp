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
package etk.web.core.impl.servlet;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com May
 * 1, 2012
 */
public class EtkServletContextInitializer {

  public static final String JAXRS_APPLICATION = "javax.ws.rs.Application";

  public static final String ETK_SCAN_COMPONENTS = "";

  protected final ServletContext ctx;

  public EtkServletContextInitializer(ServletContext ctx) {
    this.ctx = ctx;
  }

  public Application getApplication() {
    Application application = null;
    String applicationFQN = getParameter(JAXRS_APPLICATION);
    if (applicationFQN != null) {
      try {
        Class<?> cl = Thread.currentThread().getContextClassLoader().loadClass(applicationFQN);
        application = (Application) cl.newInstance();
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      } catch (InstantiationException e) {
        throw new RuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
    return application;
  }
  
  /**
   * Get parameter with specified name from servlet context initial parameters.
   *
   * @param name parameter name
   * @return value of parameter with specified name
   */
  public String getParameter(String name)
  {
     String str = ctx.getInitParameter(name);
     if (str != null)
     {
        return str.trim();
     }
     return null;
  }
}
