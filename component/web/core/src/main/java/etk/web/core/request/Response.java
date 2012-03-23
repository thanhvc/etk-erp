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

import java.io.IOException;
import java.util.Map;

import etk.web.core.text.Printer;


/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 22, 2012  
 */
public abstract class Response {

  /**
   * A response instructing to execute a render phase of a controller 
   * method after the current interaction.
   * 
   * @author thanh_vucong
   *
   */
  public static class Update extends Response {
    final Map<String, String> parameters;
    
    public Update(Map<String, String> parameters) {
      this.parameters = parameters;
    }
    
    /**
     * Setter the parameter name and value for Response.Update
     * these parameter will bind with the controller method.
     * 
     * @param parameterName parameter name
     * @param parameterValue parameter value
     * @return this object
     */
    public Update setParameter(String parameterName, String parameterValue) {
      if (parameterName == null) {
        throw new NullPointerException();
      }
      
      //
      if (parameterValue == null) {
        throw new NullPointerException();
      }
      
      //
      if (parameterName.startsWith("erp.")) {
        throw new IllegalArgumentException("Parameter can not start with <erp.> prefix.");
      }
      
      //
      parameters.put(parameterName, parameterValue);
      return this;
    }
    
    /**
     * Getter the parameter list
     * @return
     */
    public Map<String, String> getParameters() {
      return parameters;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      
      if (obj instanceof Update) {
        Update that = (Update) obj;
        return parameters.equals(that.parameters);
      }
      
      return false;
    }
    
    @Override
    public String toString() {
      return "Response.Update[parameters " + parameters + "]";
    }
  }
  
  /**
   * A response instructing to execute an HTTP redirect after the current interaction
   * @author thanh_vucong
   *
   */
  public static class Redirect extends Response {
    
    private final String location;
    
    public Redirect(String location) {
      this.location = location;
    }
    
    public String getLocation() {
      return location;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (obj instanceof Redirect) {
        Redirect that = (Redirect) obj;
        return location.equals(that.location);
      }
      return false;
    }
    
    @Override
    public String toString() {
       return "Response.Redirect[location" + location + "]";
    }
  }
  
  public static abstract class Content extends Response {
    public abstract void send(Printer printer) throws IOException;
  }
  
  public static abstract class Render extends Content {
    
  }
  
  /**
   * Resource response wrapper
   * @author thanh_vucong
   *
   */
  public static abstract class Resource extends Content {
    public abstract int getStatus();
    
    @Override
    public String toString() {
      return "Response.Resource[]";
    }
  }
  
  public static Response.Redirect redirect(String location) {
    return new Response.Redirect(location);
  }
  
  
  
}
