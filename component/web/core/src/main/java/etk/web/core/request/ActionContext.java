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
package etk.web.core.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etk.web.core.Response;
import etk.web.core.impl.application.AppContext;
import etk.web.core.impl.controller.desciptor.ControllerMethod;
import etk.web.core.impl.controller.desciptor.ControllerParameter;
import etk.web.core.impl.request.Request;
import etk.web.core.impl.spi.request.ActionEvent;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 21, 2012  
 */
public class ActionContext extends RequestContext {
  
  private ActionEvent event;

  public ActionContext(Request request, AppContext appContext, ControllerMethod method, ActionEvent event) {
    super(request, appContext, method);
  }

  @Override
  public Phase getPhase() {
    return Phase.ACTION;
  }

  @Override
  protected ActionEvent getRequestEvent() {
    return event;
  }
  
  /**
   * Create the response and also put the operation method id like as parameter request.
   * 
   * @param method ControllerMethod which will take care the process request
   * 
   * @return 
   * @throws IllegalStateException
   */
  public Response.Update createResponse(ControllerMethod method) throws IllegalStateException {
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("etk.op", method.getId());
    return new Response.Update(parameters);
  }
  
  public Response.Update createResponse(ControllerMethod method, Object arg) throws IllegalStateException {
    Response.Update response = createResponse(method);
    
    List<ControllerParameter> argumentList = method.getArguments();
    
    if (arg != null) {
      response.setParameter(argumentList.get(0).getName(), arg.toString());
    }
    
    return response;
  }
  
  public Response.Update createResponse(ControllerMethod method, Object[] args) throws IllegalStateException {
    
    Response.Update response = createResponse(method);
    
    List<ControllerParameter> argumentList = method.getArguments();
    
    for (int i =0; i < argumentList.size(); i++) {
      Object value = args[i];
      if (value != null) {
        ControllerParameter argParam = argumentList.get(i);
        response.setParameter(argParam.getName(), value.toString());
      }
      
    }
    
    return response;
  }

}
