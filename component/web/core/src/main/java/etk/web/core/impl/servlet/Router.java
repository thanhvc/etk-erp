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

import jregex.Matcher;
import jregex.Pattern;
import jregex.REFlags;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Apr
 * 23, 2012
 */
public class Router {

  static Pattern routePattern = new Pattern("^({method}GET|POST|PUT|DELETE|OPTIONS|HEAD|WS|\\*)[(]?({headers}[^)]*)(\\))?\\s+({path}.*/[^\\s]*)\\s+({action}[^\\s(]+)({params}.+)?(\\s*)$");

  /**
   * Pattern used to locate a method override instruction in request.querystring
   */
  static Pattern methodOverride = new Pattern("^.*x-http-method-override=({method}GET|PUT|POST|DELETE).*$");

  /**
   * Timestamp the routes file was last loaded at.
   */
  public static long lastLoading = -1;

  /**
   * Parse the routes file. This is called at startup.
   * 
   * @param prefix The prefix that the path of all routes in this route file
   *          start with. This prefix should not end with a '/' character.
   */
  public static void load(String prefix) {
    routes.clear();
    //{selectedNode}/ForumService
    Router.prependRoute("GET", "/{pageID}/ForumService", "forum.home");
    //{selectedNode}/SearchForum
    Router.addRoute("GET", "/{pageID}/SearchForum", "forum.search");
    //{selectedNode}Tag
    Router.addRoute("GET", "/{pageID}/Tag", "forum.tag");
    //{selectedNode}/topic/{topicId}
    Router.addRoute("GET", "/{pageID}/topic/{topicID}", "forum.topic.show");
    //{selectedNode}/topic/topic{topicId}/true
    Router.addRoute("GET", "/{pageID}/topic/{topicID}/reply", "forum.topic.reply");
    //{selectedNode}/topic/{topicId}/false
    Router.addRoute("GET", "/{pageID}/topic/{topicID}/quote", "forum.topic.quote");
    //{selectedNode}/topic/{topicID}/{postID}
    Router.addRoute("GET", "/{pageID}/topic/{topicID}/post/{postID}", "forum.topic.post.show");
    //{selectedNode}/topic/topic{topicID}/{[0-9]}
    Router.addRoute("GET", "/{pageID}/topic/{topicID}/page/{pageNo}", "forum.topic.page");
    //{selectedNode}/forum/forum{forumID}
    Router.addRoute("GET", "/{pageID}/forum/{forumID}", "forum.show");
    //{selectNode}/forum/{forumID}/{number}
    Router.addRoute("GET", "/{pageID}/forum/{forumID}/page/{pageNo}", "forum.show.page");
  }

  /**
   * This one can be called to add new route. Last added is first in the route
   * list.
   */
  public static void prependRoute(String method, String path, String action, String headers) {
    prependRoute(method, path, action, null, headers);
  }

  /**
   * This one can be called to add new route. Last added is first in the route
   * list.
   */
  public static void prependRoute(String method, String path, String action) {
    prependRoute(method, path, action, null, null);
  }

  /**
   * Add a route at the given position
   */
  public static void addRoute(int position, String method, String path, String action, String params, String headers) {
    if (position > routes.size()) {
      position = routes.size();
    }
    routes.add(position, getRoute(method, path, action, params, headers));
  }

  /**
   * Add a route at the given position
   */
  public static void addRoute(int position, String method, String path, String headers) {
    addRoute(position, method, path, null, null, headers);
  }

  /**
   * Add a route at the given position
   */
  public static void addRoute(int position, String method, String path, String action, String headers) {
    addRoute(position, method, path, action, null, headers);
  }

  /**
   * Add a new route. Will be first in the route list
   */
  public static void addRoute(String method, String path, String action) {
    prependRoute(method, path, action);
  }

  /**
   * Add a route at the given position
   */
  public static void addRoute(String method, String path, String action, String headers) {
    addRoute(method, path, action, null, headers);
  }

  /**
   * Add a route
   */
  public static void addRoute(String method, String path, String action, String params, String headers) {
    appendRoute(method, path, action, params, headers, null, 0);
  }

  /**
   * This is used internally when reading the route file. The order the routes
   * are added matters and we want the method to append the routes to the list.
   */
  public static void appendRoute(String method,
                                 String path,
                                 String action,
                                 String params,
                                 String headers,
                                 String sourceFile,
                                 int line) {
    routes.add(getRoute(method, path, action, params, headers, sourceFile, line));
  }

  public static Route getRoute(String method, String path, String action, String params, String headers) {
    return getRoute(method, path, action, params, headers, null, 0);
  }

  public static Route getRoute(String method,
                               String path,
                               String action,
                               String params,
                               String headers,
                               String sourceFile,
                               int line) {
    Route route = new Route();
    route.method = method;
    route.path = path.replace("//", "/");
    route.action = action;
    route.routesFile = sourceFile;
    route.routesFileLine = line;
    route.addFormat(headers);
    route.addParams(params);
    route.compute();
    return route;
  }

  /**
   * Add a new route at the beginning of the route list
   */
  public static void prependRoute(String method, String path, String action, String params, String headers) {
    routes.add(0, getRoute(method, path, action, params, headers));
  }

  /**
   * All the loaded routes.
   */
  public static List<Route> routes = new CopyOnWriteArrayList<Route>();

  public static Route route(Http.Request request) {
    // request method may be overriden if a x-http-method-override parameter is
    // given
    /*
     * if (request.querystring != null &&
     * methodOverride.matches(request.querystring)) { Matcher matcher =
     * methodOverride.matcher(request.querystring); if (matcher.matches()) {
     * request.method = matcher.group("method"); } }
     */
    for (Route route : routes) {
      Map<String, String> args = route.matches(request.method, request.path, request.format, request.domain);
      if (args != null) {
        request.routeArgs = args;
        request.action = route.action;
        if (args.containsKey("format")) {
          request.format = args.get("format");
        }
        if (request.action.indexOf("{") > -1) { // more optimization ?
          for (String arg : request.routeArgs.keySet()) {
            request.action = request.action.replace("{" + arg + "}", request.routeArgs.get(arg));
          }
        }
        if (request.action.equals("404")) {
          return null;
        }
        return route;
      }
    }
    // Not found - if the request was a HEAD, let's see if we can find a
    // corresponding GET
    if (request.method.equalsIgnoreCase("head")) {
      request.method = "GET";
      Route route = route(request);
      request.method = "HEAD";
      if (route != null) {
        return route;
      }
    }
    return null;

  }

  public static Map<String, String> route(String method, String path) {
    return route(method, path, null, null);
  }

  public static Map<String, String> route(String method, String path, String headers) {
    return route(method, path, headers, null);
  }

  public static Map<String, String> route(String method, String path, String headers, String host) {
    for (Route route : routes) {
      Map<String, String> args = route.matches(method, path, headers, host);
      if (args != null) {
        args.put("action", route.action);
        return args;
      }
    }
    return new HashMap<String, String>(16);
  }

  public static ActionDefinition reverse(String action) {
    // Note the map is not <code>Collections.EMPTY_MAP</code> because it will be
    // copied and changed.
    return reverse(action, new HashMap<String, Object>(16));
  }

  public static String getFullUrl(String action, Map<String, Object> args) {
    ActionDefinition actionDefinition = reverse(action, args);
    String base = getBaseUrl();
    if (actionDefinition.method.equals("WS")) {
      return base.replaceFirst("https?", "ws") + actionDefinition;
    }
    return base + actionDefinition;
  }

  // Gets baseUrl from current request or application.baseUrl in
  // application.conf
  protected static String getBaseUrl() {
    if (Http.Request.current() == null) {
      // No current request is present - must get baseUrl from config
      String appBaseUrl = "application.baseUrl";
      if (appBaseUrl.endsWith("/")) {
        // remove the trailing slash
        appBaseUrl = appBaseUrl.substring(0, appBaseUrl.length() - 1);
      }
      return appBaseUrl;

    } else {
      return Http.Request.current().getBase();
    }
  }

  public static String getFullUrl(String action) {
    // Note the map is not <code>Collections.EMPTY_MAP</code> because it will be
    // copied and changed.
    return getFullUrl(action, new HashMap<String, Object>(16));
  }

  public static ActionDefinition reverse(String action, Map<String, Object> args) {

    String encoding = Http.Response.current().encoding;

    if (action.startsWith("controllers.")) {
      action = action.substring(12);
    }
    Map<String, Object> argsbackup = new HashMap<String, Object>(args);
    // Add routeArgs
    /*
     * if (Scope.RouteArgs.current() != null) { for (String key :
     * Scope.RouteArgs.current().data.keySet()) { if (!args.containsKey(key)) {
     * args.put(key, Scope.RouteArgs.current().data.get(key)); } } }
     */
    for (Route route : routes) {
      if (route.actionPattern != null) {
        Matcher matcher = route.actionPattern.matcher(action);
        if (matcher.matches()) {
          for (String group : route.actionArgs) {
            String v = matcher.group(group);
            if (v == null) {
              continue;
            }
            args.put(group, v.toLowerCase());
          }
          List<String> inPathArgs = new ArrayList<String>(16);
          boolean allRequiredArgsAreHere = true;
          // les noms de parametres matchent ils ?
          for (Route.Arg arg : route.args) {
            inPathArgs.add(arg.name);
            Object value = args.get(arg.name);
            if (value == null) {
              // This is a hack for reverting on hostname that are a regex
              // expression.
              // See [#344] for more into. This is not optimal and should
              // retough. However,
              // it allows us to do things like {(.*}}.domain.com
              String host = route.host.replaceAll("\\{", "").replaceAll("\\}", "");
              if (host.equals(arg.name) || host.matches(arg.name)) {
                args.remove(arg.name);
                route.host = Http.Request.current() == null ? "" : Http.Request.current().domain;
                break;
              } else {
                allRequiredArgsAreHere = false;
                break;
              }
            } else {
              if (value instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<Object> l = (List<Object>) value;
                value = l.get(0);
              }
              if (!value.toString().startsWith(":") && !arg.constraint.matches(value.toString())) {
                allRequiredArgsAreHere = false;
                break;
              }
            }
          }
          // les parametres codes en dur dans la route matchent-ils ?
          for (String staticKey : route.staticArgs.keySet()) {
            if (staticKey.equals("format")) {
              if (!(Http.Request.current() == null ? "" : Http.Request.current().format).equals(route.staticArgs.get("format"))) {
                allRequiredArgsAreHere = false;
                break;
              }
              continue; // format is a special key
            }
            if (!args.containsKey(staticKey) || (args.get(staticKey) == null)
                || !args.get(staticKey).toString().equals(route.staticArgs.get(staticKey))) {
              allRequiredArgsAreHere = false;
              break;
            }
          }
          if (allRequiredArgsAreHere) {
            StringBuilder queryString = new StringBuilder();
            String path = route.path;
            String host = route.host;
            if (path.endsWith("/?")) {
              path = path.substring(0, path.length() - 2);
            }
            for (Map.Entry<String, Object> entry : args.entrySet()) {
              String key = entry.getKey();
              Object value = entry.getValue();
              if (inPathArgs.contains(key) && value != null) {
                if (List.class.isAssignableFrom(value.getClass())) {
                  @SuppressWarnings("unchecked")
                  List<Object> vals = (List<Object>) value;
                  path = path.replaceAll("\\{(<[^>]+>)?" + key + "\\}", vals.get(0).toString()).replace("$", "\\$");
                } else {
                  path = path.replaceAll("\\{(<[^>]+>)?" + key + "\\}", value.toString()
                                                                             .replace("$", "\\$")
                                                                             .replace("%3A", ":")
                                                                             .replace("%40", "@"));
                  host = host.replaceAll("\\{(<[^>]+>)?" + key + "\\}", value.toString()
                                                                             .replace("$", "\\$")
                                                                             .replace("%3A", ":")
                                                                             .replace("%40", "@"));
                }
              } else if (route.staticArgs.containsKey(key)) {
                // Do nothing -> The key is static
              } else if (!argsbackup.containsKey(key)) {
                // Do nothing -> The key is provided in RouteArgs and not used
                // (see #447)
              } else if (value != null) {
                if (List.class.isAssignableFrom(value.getClass())) {
                  @SuppressWarnings("unchecked")
                  List<Object> vals = (List<Object>) value;
                  for (Object object : vals) {
                    try {
                      queryString.append(URLEncoder.encode(key, encoding));
                      queryString.append("=");
                      if (object.toString().startsWith(":")) {
                        queryString.append(object.toString());
                      } else {
                        queryString.append(URLEncoder.encode(object.toString() + "", encoding));
                      }
                      queryString.append("&");
                    } catch (UnsupportedEncodingException ex) {
                    }
                  }
                } else {
                  try {
                    queryString.append(URLEncoder.encode(key, encoding));
                    queryString.append("=");
                    if (value.toString().startsWith(":")) {
                      queryString.append(value.toString());
                    } else {
                      queryString.append(URLEncoder.encode(value.toString() + "", encoding));
                    }
                    queryString.append("&");
                  } catch (UnsupportedEncodingException ex) {
                  }
                }
              }
            }
            String qs = queryString.toString();
            if (qs.endsWith("&")) {
              qs = qs.substring(0, qs.length() - 1);
            }
            ActionDefinition actionDefinition = new ActionDefinition();
            actionDefinition.url = qs.length() == 0 ? path : path + "?" + qs;
            actionDefinition.method = route.method == null || route.method.equals("*") ? "GET" : route.method.toUpperCase();
            actionDefinition.star = "*".equals(route.method);
            actionDefinition.action = action;
            actionDefinition.args = argsbackup;
            actionDefinition.host = host;
            return actionDefinition;
          }
        }
      }
    }
    return null;
  }

  public static class ActionDefinition {

    /**
     * The domain/host name.
     */
    public String host;

    /**
     * The HTTP method, e.g. "GET".
     */
    public String method;

    /**
     * @todo - what is this? does it include the domain?
     */
    public String url;

    /**
     * Whether the route contains an astericks *.
     */
    public boolean star;

    /**
     * @todo - what is this? does it include the class and package?
     */
    public String action;

    /**
     * @todo - are these the required args in the routing file, or the query
     *       string in a request?
     */
    public Map<String, Object> args;

    public ActionDefinition add(String key, Object value) {
      args.put(key, value);
      return reverse(action, args);
    }

    public ActionDefinition remove(String key) {
      args.remove(key);
      return reverse(action, args);
    }

    public ActionDefinition addRef(String fragment) {
      url += "#" + fragment;
      return this;
    }

    @Override
    public String toString() {
      return url;
    }

    public void absolute() {
      boolean isSecure = Http.Request.current() == null ? false : Http.Request.current().secure;
      String base = getBaseUrl();
      String hostPart = host;
      String domain = Http.Request.current() == null ? "" : Http.Request.current().get().domain;
      int port = Http.Request.current() == null ? 80 : Http.Request.current().get().port;
      if (port != 80 && port != 443) {
        hostPart += ":" + port;
      }
      // ~
      if (!url.startsWith("http")) {
        if (host != null && host.length() == 0) {
          url = base + url;
        } else if (host.contains("{_}")) {
          java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("([-_a-z0-9A-Z]+([.][-_a-z0-9A-Z]+)?)$")
                                                                   .matcher(domain);
          if (matcher.find()) {
            url = (isSecure ? "https://" : "http://") + hostPart.replace("{_}", matcher.group(1)) + url;
          } else {
            url = (isSecure ? "https://" : "http://") + hostPart + url;
          }
        } else {
          url = (isSecure ? "https://" : "http://") + hostPart + url;
        }
        if (method.equals("WS")) {
          url = url.replaceFirst("https?", "ws");
        }
      }
    }

    public ActionDefinition secure() {
      if (!url.contains("http://") && !url.contains("https://")) {
        absolute();
      }
      url = url.replace("http:", "https:");
      return this;
    }
  }

  public static class Route {

    /**
     * HTTP method, e.g. "GET".
     */
    public String method;

    public String path;

    /**
     * @todo - what is this?
     */
    public String action;

    Pattern actionPattern;

    List<String> actionArgs = new ArrayList<String>(3);

    String staticDir;

    boolean staticFile;

    Pattern pattern;

    Pattern hostPattern;

    List<Arg> args = new ArrayList<Arg>(3);

    Map<String, String> staticArgs = new HashMap<String, String>(3);

    List<String> formats = new ArrayList<String>(1);

    String host;

    Arg hostArg = null;

    public int routesFileLine;

    public String routesFile;

    static Pattern customRegexPattern = new Pattern("\\{([a-zA-Z_][a-zA-Z_0-9]*)\\}");

    static Pattern argsPattern = new Pattern("\\{<([^>]+)>([a-zA-Z_0-9]+)\\}");

    static Pattern paramPattern = new Pattern("([a-zA-Z_0-9]+):'(.*)'");

    public void compute() {
      this.host = "";
      this.hostPattern = new Pattern(".*");
      if (action.startsWith("staticDir:") || action.startsWith("staticFile:")) {
        // Is there is a host argument, append it.
        if (!path.startsWith("/")) {
          String p = this.path;
          this.path = p.substring(p.indexOf("/"));
          this.host = p.substring(0, p.indexOf("/"));
          if (this.host.contains("{")) {
            // Logger.warn("Static route cannot have a dynamic host name");
            return;
          }
        }
        if (!method.equalsIgnoreCase("*") && !method.equalsIgnoreCase("GET")) {
          // Logger.warn("Static route only support GET method");
          return;
        }
      }
      // staticDir
      if (action.startsWith("staticDir:")) {
        if (!this.path.endsWith("/") && !this.path.equals("/")) {
          // Logger.warn("The path for a staticDir route must end with / (%s)",
          // this);
          this.path += "/";
        }
        this.pattern = new Pattern("^" + path + "({resource}.*)$");
        this.staticDir = action.substring("staticDir:".length());
      } else if (action.startsWith("staticFile:")) {
        this.pattern = new Pattern("^" + path + "$");
        this.staticFile = true;
        this.staticDir = action.substring("staticFile:".length());
      } else {
        // URL pattern
        // Is there is a host argument, append it.
        if (!path.startsWith("/")) {
          String p = this.path;
          this.path = p.substring(p.indexOf("/"));
          this.host = p.substring(0, p.indexOf("/"));
          String pattern = host.replaceAll("\\.", "\\\\.").replaceAll("\\{.*\\}", "(.*)");

          // if (Logger.isTraceEnabled()) {
          // Logger.trace("pattern [" + pattern + "]");
          // Logger.trace("host [" + host + "]");
          // }

          Matcher m = new Pattern(pattern).matcher(host);
          this.hostPattern = new Pattern(pattern);

          if (m.matches()) {
            if (this.host.contains("{")) {
              String name = m.group(1).replace("{", "").replace("}", "");
              if (!name.equals("_")) {
                hostArg = new Arg();
                hostArg.name = name;
                // if (Logger.isTraceEnabled()) {
                // Logger.trace("hostArg name [" + name + "]");
                // }
                // The default value contains the route version of the host ie
                // {client}.bla.com
                // It is temporary and it indicates it is an url route.
                // TODO Check that default value is actually used for other
                // cases.
                hostArg.defaultValue = host;
                hostArg.constraint = new Pattern(".*");

                // if (Logger.isTraceEnabled()) {
                // Logger.trace("adding hostArg [" + hostArg + "]");
                // }

                args.add(hostArg);
              }
            }
          }

        }
        String patternString = path;
        patternString = customRegexPattern.replacer("\\{<[^/]+>$1\\}").replace(patternString);
        Matcher matcher = argsPattern.matcher(patternString);
        while (matcher.find()) {
          Arg arg = new Arg();
          arg.name = matcher.group(2);
          arg.constraint = new Pattern(matcher.group(1));
          args.add(arg);
        }

        patternString = argsPattern.replacer("({$2}$1)").replace(patternString);
        this.pattern = new Pattern(patternString);
        // Action pattern
        patternString = action;
        patternString = patternString.replace(".", "[.]");
        for (Arg arg : args) {
          if (patternString.contains("{" + arg.name + "}")) {
            patternString = patternString.replace("{" + arg.name + "}", "({" + arg.name + "}" + arg.constraint.toString() + ")");
            actionArgs.add(arg.name);
          }
        }
        actionPattern = new Pattern(patternString, REFlags.IGNORE_CASE);
      }
    }

    public void addParams(String params) {
      if (params == null || params.length() < 1) {
        return;
      }
      params = params.substring(1, params.length() - 1);
      for (String param : params.split(",")) {
        Matcher matcher = paramPattern.matcher(param);
        if (matcher.matches()) {
          staticArgs.put(matcher.group(1), matcher.group(2));
        } else {
          // Logger.warn("Ignoring %s (static params must be specified as key:'value',...)",
          // params);
        }
      }
    }

    // TODO: Add args names
    public void addFormat(String params) {
      if (params == null || params.length() < 1) {
        return;
      }
      params = params.trim();
      formats.addAll(Arrays.asList(params.split(",")));
    }

    private boolean contains(String accept) {
      boolean contains = (accept == null);
      if (accept != null) {
        if (this.formats.isEmpty()) {
          return true;
        }
        for (String format : this.formats) {
          contains = format.startsWith(accept);
          if (contains) {
            break;
          }
        }
      }
      return contains;
    }

    public Map<String, String> matches(String method, String path) {
      return matches(method, path, null, null);
    }

    public Map<String, String> matches(String method, String path, String accept) {
      return matches(method, path, accept, null);
    }

    /**
     * Check if the parts of a HTTP request equal this Route.
     * 
     * @param method GET/POST/etc.
     * @param path Part after domain and before query-string. Starts with a "/".
     * @param accept Format, e.g. html.
     * @param domain The domain (host without port).
     * @return ???
     */
    public Map<String, String> matches(String method, String path, String accept, String domain) {
      // Normalize
      // if (path.equals(Play.ctxPath)) {
      // path = path + "/";
      // }
      // If method is HEAD and we have a GET

      Matcher matcher = pattern.matcher(path);

      // Extract the host variable
      if (matcher.matches()) {

        // Matcher matcher = pattern.matcher(path);
        Map<String, String> localArgs = new HashMap<String, String>();
        for (Arg arg : args) {
          // FIXME: Careful with the arguments that are not matching as they
          // are part of the hostname
          // Defaultvalue indicates it is a one of these urls. This is a
          // trick and should be changed.
          if (arg.defaultValue == null) {
            localArgs.put(arg.name, matcher.group(arg.name));
          }
        }
        if (hostArg != null && domain != null) {
          // Parse the hostname and get only the part we are interested in
          String routeValue = hostArg.defaultValue.replaceAll("\\{.*}", "");
          domain = domain.replace(routeValue, "");
          localArgs.put(hostArg.name, domain);
        }
        localArgs.putAll(staticArgs);
        return localArgs;

      }
      return null;
    }

    static class Arg {

      String name;

      Pattern constraint;

      String defaultValue;

      Boolean optional = false;
    }

    @Override
    public String toString() {
      return method + " " + path + " -> " + action;
    }
  }
}
