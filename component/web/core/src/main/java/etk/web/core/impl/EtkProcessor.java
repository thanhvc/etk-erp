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
package etk.web.core.impl;

import java.util.Set;

import javax.ws.rs.core.Application;

import etk.web.core.ProviderBuilder;
import etk.web.core.ResourceBuilder;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * May 1, 2012  
 */
public class EtkProcessor {
  
  //private final RequestHandler requestHandler;
  private final ResourceBuilder resources;
  private final ProviderBuilder providers;
  

  public EtkProcessor(ResourceBuilder resources, ProviderBuilder providers, Application application) {
    this.resources = resources;
    this.providers = providers;
    if (application != null) {
      addApplication(application);
    }
    
  }
  
  public void addApplication(Application application) {
    if (application == null) {
      throw new NullPointerException("application");
    }
  }
  
  private void resolver(Application application) {
    Set<Class<?>> perRequests = application.getClasses();
    
    
  }
}
