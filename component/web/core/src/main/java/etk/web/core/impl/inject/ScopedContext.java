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
package etk.web.core.impl.inject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * <p>
 * The helper class for managing scoped entries. It implements {@ HttpSessionBindingListerner}
 * interface which invokes the {@link #close()} method when the servlet container invokes the
 * {@link #valueUnbound(HttpSessionBindingEvent) callback. </p>
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 22, 2012  
 */
public class ScopedContext implements HttpSessionBindingListener, Iterable<Scoped> {

  /** . */
  private Map<Object, Scoped> state;
  
  /**
   * Gets the {@link Scoped} from the state
   * @param key
   * @return
   * @throws NullPointerException
   */
  public Scoped get(Object key) throws NullPointerException {
    if (key == null) {
      throw new NullPointerException("No null key accepted");
    }
    
    return state != null ? state.get(key) : null;
  }
  
  public void set(Object key, Scoped scoped) throws NullPointerException {
    if (key == null) {
      throw new NullPointerException("No null key accepted");
    }
    
    //removes the value when value argument is null
    if (scoped == null) {
      if (state != null) {
        state.remove(key);
      }
    } else {
      if (state == null) {
        state = new HashMap<Object, Scoped>();
      }
      
      //
      state.put(key, scoped);
    }
  }
  
  /**
   * Gets the size of the ScopedContext which keeps the Scoped value.
   * @return
   */
  public int size() {
    return state != null ? state.size() : 0;
  }
  
  
  
  @Override
  public Iterator<Scoped> iterator() {
   
    return state == null ? Collections.<Scoped>emptyList().iterator() : state.values().iterator();
  }

  
  @Override
  public void valueBound(HttpSessionBindingEvent event) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void valueUnbound(HttpSessionBindingEvent event) {
    close();
    
  }
  /**
   * @TODO later
   */
  public void close() {
    
  }

}
