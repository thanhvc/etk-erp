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
package etk.web.core.method;

import etk.web.core.resource.MethodDescriptor;

/**
 * Invoke resource methods.
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * May 1, 2012  
 */
public interface MethodInvoker {

  /**
   * Invoke supplied method and return result of method invoking
   * 
   * @param resource object that contains method
   * @param method 
   * @return result of methof invoking
   */
  Object invokeMethod(Object resource, MethodDescriptor method);
}
