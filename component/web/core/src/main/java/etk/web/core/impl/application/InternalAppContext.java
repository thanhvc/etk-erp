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
package etk.web.core.impl.application;

import java.util.List;

import etk.web.core.impl.application.metadata.AppDescriptor;
import etk.web.core.impl.spi.request.RequestEvent;
import etk.web.core.plugin.Plugin;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 23, 2012  
 */
public class InternalAppContext extends AppContext {

  //static final ThreadLocal<Request> current = ThreadLocal<Request>();
  
  
  @Override
  public ClassLoader getClassLoader() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Plugin> getPlugins() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void invoke(RequestEvent event) throws AppException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public AppDescriptor getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

}
