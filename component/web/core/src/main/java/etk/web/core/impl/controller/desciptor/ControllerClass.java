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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import etk.web.core.Action;


/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 23, 2012  
 */
public class ControllerClass {

  private final List<ControllerMethod> methodControllers;
  
  /**
   * Constructor the resolve all of controller classes which have specified Controller annotation. 
   * @param controllerClazz
   */
  public ControllerClass(Class<?> controllerClazz) {
    Method[] methods = controllerClazz.getMethods();
    
    
    List<ControllerMethod> methodControllers = new ArrayList<ControllerMethod>();
    
    for(Method method : methods) {
      if (method.isAnnotationPresent(Action.class)) {
        //TODO
        List<ControllerParameter> argumentList = new LinkedList<ControllerParameter>();

        //we can use the parameter annotation for this
        for (int i = 0;i < method.getParameterTypes().length; i++) {
          Class<?> paramType = method.getParameterTypes()[i];
          
          
        }
        methodControllers.add(new ControllerMethod(null, controllerClazz, method, argumentList));
      }
    }
    
    this.methodControllers = methodControllers;
  }
  
  /**
   * 
   * @return
   */
  public List<ControllerMethod> getMethods() {
    return this.methodControllers;
  }
}
