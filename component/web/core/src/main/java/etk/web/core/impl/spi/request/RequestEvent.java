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
package etk.web.core.impl.spi.request;

import java.io.IOException;
import java.util.Map;

import etk.web.core.impl.inject.Scoped;
import etk.web.core.request.HttpContext;
import etk.web.core.request.Response;

/**
 * We need to determine these values which keeps with 
 * Request and Session scope. The object value is hold by 
 * <code>Scoped</code> with the it own <code>ScopeContext</code>
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 22, 2012  
 */
public interface RequestEvent {

  Map<String, String[]> getParameters();
  
  Scoped getRequestValue(Object key);
  
  void setRequestValue(Object key, Scoped value);
  
  Scoped getSessionValue(Object key);
  
  void setSessionValue(Object key, Scoped value);
  
  Scoped getIdentityValue(Object key);
  
  void setIdentityValue(Object key, Scoped value);
  
  HttpContext getHttpContext();
  
  void setResponse(Response response) throws IllegalStateException, IOException;
  
  
}
