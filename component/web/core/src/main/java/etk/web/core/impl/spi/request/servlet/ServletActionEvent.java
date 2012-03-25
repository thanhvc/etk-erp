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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etk.web.core.Response;
import etk.web.core.impl.spi.request.ActionEvent;
import etk.web.core.request.Phase;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 22, 2012  
 */
public class ServletActionEvent extends ServletRequestEvent implements ActionEvent {
  
  public ServletActionEvent(HttpServletRequest req, HttpServletResponse resp) {
    super(req, resp);
  }

  public void setResponse(Response response) throws IllegalStateException, IOException {
    if (response instanceof Response.Update) {
      Response.Update update = (Response.Update) response;
      Map<String, String[]> parameters = new HashMap<String, String[]>();
      
      for (Map.Entry<String, String> entry : update.getParameters().entrySet()) {
        parameters.put(entry.getKey(), new String[]{entry.getValue()});
      }
      
      String url = renderURL(Phase.RENDER, null, parameters);
      resp.sendRedirect(url);
      
    } else if (response instanceof Response.Redirect) {
      Response.Redirect redirect = (Response.Redirect) response;
      String url = redirect.getLocation();
      resp.sendRedirect(url);
    }
  }
  
  

}
