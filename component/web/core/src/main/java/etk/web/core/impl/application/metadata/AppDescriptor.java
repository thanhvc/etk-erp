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
package etk.web.core.impl.application.metadata;

import java.util.ArrayList;
import java.util.List;

import etk.web.core.impl.controller.desciptor.ControllerClass;
import etk.web.core.impl.controller.desciptor.ControllerMethod;
import etk.web.core.plugin.Plugin;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 23, 2012  
 */
public class AppDescriptor {
  private final Boolean escapeXML;
  
  private final List<Class<? extends Plugin>> plugins;
  
  private final List<ControllerClass> controllerClasses;
  
  private final List<ControllerMethod> controllerMethods;
  
  public AppDescriptor(List<ControllerClass> controllerClasses,
                       Boolean escapeXML,
                       List<Class<? extends Plugin>> plugins) {
    
    this.escapeXML = escapeXML;
    this.plugins = plugins;
    this.controllerClasses = controllerClasses;

    //
    controllerMethods = new ArrayList<ControllerMethod>();
    for (ControllerClass controllerClass : controllerClasses) {
      controllerMethods.addAll(controllerClass.getMethods());
    }

  }

  public List<Class<? extends Plugin>> getPlugins() {
    return plugins;
  }
  
  
  
}
