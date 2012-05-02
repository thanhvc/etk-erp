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
package etk.web.core.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com May
 * 1, 2012
 */
public class EtkApplication extends Application {

  private final Set<Class<?>> classes;

  private final Set<Object> singletons;

  public EtkApplication() {
    classes = new LinkedHashSet<Class<?>>(1);
    singletons = new LinkedHashSet<Object>(3);
  }

  /**
   * @see javax.ws.rs.core.Application#getClasses()
   */
  @Override
  public Set<Class<?>> getClasses() {
    return classes;
  }

  /**
   * @see javax.ws.rs.core.Application#getSingletons()
   */
  @Override
  public Set<Object> getSingletons() {
    return singletons;
  }

  /**
   * Add components defined by <code>application</code> to this instance.
   * 
   * @param application application
   * @see Application
   */
  public void addApplication(Application application) {
    if (application != null) {
      Set<Object> appSingletons = application.getSingletons();
      if (appSingletons != null && appSingletons.size() > 0) {
        Set<Object> tmp = new LinkedHashSet<Object>(getSingletons().size() + appSingletons.size());
        tmp.addAll(appSingletons);
        tmp.addAll(getSingletons());
        getSingletons().clear();
        getSingletons().addAll(tmp);
      }
      Set<Class<?>> appClasses = application.getClasses();
      if (appClasses != null && appClasses.size() > 0) {
        Set<Class<?>> tmp = new LinkedHashSet<Class<?>>(getClasses().size() + appClasses.size());
        tmp.addAll(appClasses);
        tmp.addAll(getClasses());
        getClasses().clear();
        getClasses().addAll(tmp);
      }
    }
  }
}
