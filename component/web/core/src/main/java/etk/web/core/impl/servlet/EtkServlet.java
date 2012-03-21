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
package etk.web.core.impl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 20, 2012  
 */
@SuppressWarnings("serial")
public final class EtkServlet extends HttpServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    doInit();
  }
  
  private void doInit() throws ServletException {
    
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
                                                                        IOException {
    processAction();
    serveResource();
    render();
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
    out.println("<html><head><title>Hello world sample</title></head>");
    out.println("<body><h2>" + getServletName() + "</h2>");
    out.println("This is a basic servlet.<br>");
    out.println("<hr></body></html>");
    out.close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    processAction();
    serveResource();
    render();
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
    out.println("<html><head><title>Hello world sample</title></head>");
    out.println("<body><h2>" + getServletName() + "</h2>");
    out.println("This is a basic servlet.<br>");
    out.println("<hr></body></html>");
    out.close();
  }
  
  private void processAction() throws ServletException, IOException {
    
  }
  
  private void render() {
    try {
      
      serveResource();
    } catch (Exception ex) {
      renderException(ex);
    }
  }
  
  private void renderException(Exception ex) {
    
  }
 
  private void serveResource() throws ServletException, IOException {
    
  }
  
  

  @Override
  public void destroy() {
    super.destroy();
    doDestroy();
  }
  
  
  private void doDestroy() {
    
  }
}
