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
package etk.web.core.impl.controller.desciptor;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 23, 2012  
 */
public class ControllerMethod {

  private final String id;
  
  private final Class<?> type;
  
  private final Method method;
  
  private final List<ControllerParameter> argumentList;
  
  private final Map<String, ControllerParameter> argumentMap;
  
  public ControllerMethod(String id, Class<?> type, Method method, List<ControllerParameter> argumentList) {
    if (id == null) {
      StringBuilder sb = new StringBuilder();
      sb.append(method.getDeclaringClass().getSimpleName());
      sb.append(".");
      sb.append(method.getName());
      id = sb.toString();
    }
    
    LinkedHashMap<String, ControllerParameter> argumentMap = new LinkedHashMap<String, ControllerParameter>();
    
    for (ControllerParameter argument : argumentList) {
      argumentMap.put(argument.getName(), argument);
    }
    
    //
    this.id = id;
    this.type = type;
    this.method = method;
    this.argumentList = argumentList;
    this.argumentMap = Collections.unmodifiableMap(argumentMap);
  }

  public String getId() {
    return id;
  }

  public Class<?> getType() {
    return type;
  }

  public Method getMethod() {
    return method;
  }

  public ControllerParameter getArgument(String name) {
    return argumentMap.get(name);
  }

  public List<ControllerParameter> getArguments() {
    return argumentList;
  }
  
  public Set<String> getArgumentNames() {
    return argumentMap.keySet();
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(getClass().getSimpleName());
    sb.append("type[").append(type.getName()).append(", method=");
    sb.append(method.getName()).append("(");
    Class<?>[] types = method.getParameterTypes();
    
    for (int i = 0; i < types.length; i++) {
      if (i > 0) {
        sb.append(",");
      }
      sb.append(argumentList.get(i).getName()).append("=").append(types[i].getName());
    }
    sb.append(")]");
    return sb.toString();
  }
  
  
}
