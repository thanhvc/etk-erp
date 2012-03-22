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

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 22, 2012  
 */
public abstract class Response {

  public static class Update extends Response {
    
  }
  
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
    
  }
  
  public static abstract class Render extends Content {
    
  }
  
  public static abstract class Resource extends Content {
    
  }
  
  public static Response.Redirect redirect(String location) {
    return new Response.Redirect(location);
  }
  
  
  
}
