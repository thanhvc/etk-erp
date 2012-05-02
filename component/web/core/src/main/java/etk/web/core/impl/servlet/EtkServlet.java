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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etk.web.core.impl.servlet.Http.Request;
import etk.web.core.impl.servlet.Http.Response;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Mar
 * 20, 2012
 */
@SuppressWarnings("serial")
public final class EtkServlet extends HttpServlet {

  private static boolean routerInitializedWithContext = false;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    doInit(config);
  }

  private void doInit(ServletConfig config) throws ServletException {

    //initialization the Router
    loadRouter(config.getServletContext().getContextPath());
  }

  @Override
  protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException,
                                                                                                        IOException {

    if (!routerInitializedWithContext) {
      loadRouter(httpServletRequest.getContextPath());
    }

    Request request = null;
    try {
      Response response = new Response();
      response.out = new ByteArrayOutputStream();
      Response.current.set(response);
      request = parseRequest(httpServletRequest);

    } catch (Throwable e) {
      throw new ServletException(e);
    } finally {
      Request.current.remove();
      Response.current.remove();
    }

    /**
     * processAction(httpServletRequest, httpServletResponse); render();
     * httpServletResponse.setContentType("text/html"); PrintWriter out =
     * httpServletResponse.getWriter();
     * out.println("<html><head><title>Hello world sample</title></head>");
     * out.println("<body><h2>" + getServletName() + "</h2>"); out.println("This
     * is a basic servlet.<br>
     * "); out.println("
     * <hr>
     * </body></html>"); out.close();
     */
  }

  private void processAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

  }

  private void render() {

  }

  private static synchronized void loadRouter(String contextPath) {
    if (routerInitializedWithContext)
      return;
    EtkContext.ctxPath = contextPath;
    Router.load(contextPath);
    routerInitializedWithContext = true;
  }

  public static Request parseRequest(HttpServletRequest httpServletRequest) throws Exception {

    URI uri = new URI(httpServletRequest.getRequestURI());
    String method = httpServletRequest.getMethod().intern();
    String path = uri.getPath();
    String querystring = httpServletRequest.getQueryString() == null ? "" : httpServletRequest.getQueryString();

    String contentType = null;
    if (httpServletRequest.getHeader("Content-Type") != null) {
      contentType = httpServletRequest.getHeader("Content-Type").split(";")[0].trim().toLowerCase().intern();
    } else {
      contentType = "text/html".intern();
    }

    if (httpServletRequest.getHeader("X-HTTP-Method-Override") != null) {
      method = httpServletRequest.getHeader("X-HTTP-Method-Override").intern();
    }

    InputStream body = httpServletRequest.getInputStream();
    boolean secure = httpServletRequest.isSecure();

    String url = uri.toString() + (httpServletRequest.getQueryString() == null ? "" : "?" + httpServletRequest.getQueryString());
    String host = httpServletRequest.getHeader("host");
    int port = 0;
    String domain = null;
    if (host.contains(":")) {
      port = Integer.parseInt(host.split(":")[1]);
      domain = host.split(":")[0];
    } else {
      port = 80;
      domain = host;
    }

    String remoteAddress = httpServletRequest.getRemoteAddr();

    boolean isLoopback = host.matches("^127\\.0\\.0\\.1:?[0-9]*$");

    final Request request = Request.createRequest(remoteAddress,
                                                  method,
                                                  path,
                                                  querystring,
                                                  contentType,
                                                  body,
                                                  url,
                                                  host,
                                                  isLoopback,
                                                  port,
                                                  domain,
                                                  secure,
                                                  getHeaders(httpServletRequest),
                                                  getCookies(httpServletRequest));

    Request.current.set(request);
    return request;
  }

  protected static Map<String, Http.Header> getHeaders(HttpServletRequest httpServletRequest) {
    Map<String, Http.Header> headers = new HashMap<String, Http.Header>(16);

    Enumeration headersNames = httpServletRequest.getHeaderNames();
    while (headersNames.hasMoreElements()) {
      Http.Header hd = new Http.Header();
      hd.name = (String) headersNames.nextElement();
      hd.values = new ArrayList<String>();
      Enumeration enumValues = httpServletRequest.getHeaders(hd.name);
      while (enumValues.hasMoreElements()) {
        String value = (String) enumValues.nextElement();
        hd.values.add(value);
      }
      headers.put(hd.name.toLowerCase(), hd);
    }

    return headers;
  }

  protected static Map<String, Http.Cookie> getCookies(HttpServletRequest httpServletRequest) {
    Map<String, Http.Cookie> cookies = new HashMap<String, Http.Cookie>(16);
    javax.servlet.http.Cookie[] cookiesViaServlet = httpServletRequest.getCookies();
    if (cookiesViaServlet != null) {
      for (javax.servlet.http.Cookie cookie : cookiesViaServlet) {
        Http.Cookie playCookie = new Http.Cookie();
        playCookie.name = cookie.getName();
        playCookie.path = cookie.getPath();
        playCookie.domain = cookie.getDomain();
        playCookie.secure = cookie.getSecure();
        playCookie.value = cookie.getValue();
        playCookie.maxAge = cookie.getMaxAge();
        cookies.put(playCookie.name, playCookie);
      }
    }

    return cookies;
  }

  @Override
  public void destroy() {
    super.destroy();
    doDestroy();
  }

  private void doDestroy() {

  }
}
