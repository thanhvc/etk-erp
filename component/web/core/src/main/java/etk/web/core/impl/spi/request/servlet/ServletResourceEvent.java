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
package etk.web.core.impl.spi.request.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etk.web.core.impl.spi.request.ResourceEvent;
import etk.web.core.request.Response;
import etk.web.core.text.WriterPrinter;

/**
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 22, 2012  
 */
public class ServletResourceEvent extends ServletMimeEvent implements ResourceEvent {
  
  ServletResourceEvent(HttpServletRequest req, HttpServletResponse resp) {
    super(req, resp);
  }
  
  public void setResponse(Response response) throws IllegalStateException, IOException {
    
    //
    if (response instanceof Response.Content.Resource) {
      Response.Content.Resource resource = (Response.Content.Resource)response;
      
      //check the status which return current interaction.
      //if 200 status return, it's OK and resource is available for render.
      int status = resource.getStatus();
      
      if(status != 200) {
        resp.setStatus(status);
      }
      
    }
    
    //
    resp.setContentType("text/html");
    
    //
    PrintWriter writer = resp.getWriter();
    
    //Send response
    ((Response.Resource) response).send(new WriterPrinter(writer));
  }
}
