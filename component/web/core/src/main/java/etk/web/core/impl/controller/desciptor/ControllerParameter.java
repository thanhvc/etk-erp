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
package etk.web.core.impl.controller.desciptor;

import etk.web.core.impl.utils.Cardinality;
import etk.web.core.impl.utils.Tools;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 23, 2012  
 */
public class ControllerParameter {

  /** . */
  private final String name;
  
  private final Cardinality cardinality;
  
  private final String value;
  
  public ControllerParameter(String name, Cardinality cardinality) throws NullPointerException {
    this(name, cardinality, null);
  }
  
  public ControllerParameter(String name, Cardinality cardinality, String value) throws NullPointerException {
    if (name == null) {
      throw new NullPointerException("No null parameter name accepted.");
    }
    
    if (cardinality == null) {
      throw new NullPointerException("No null parameter cardinalty accepted");
    }
    
    this.name = name;
    this.cardinality = cardinality;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public Cardinality getCardinality() {
    return cardinality;
  }

  public String getValue() {
    return value;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (obj instanceof ControllerParameter) {
      ControllerParameter that = (ControllerParameter) obj;
      return name.equals(that.name) && Tools.safeEquals(value, that.value);
    }
    return super.equals(obj);
  }
  
  @Override
  public String toString() {
    return "ControllerParameter[name=" + name + ", value " + value + "]";
  }
  
  
}
