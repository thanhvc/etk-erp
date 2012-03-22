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

import java.lang.annotation.Annotation;

import etk.web.core.Action;
import etk.web.core.Resource;
import etk.web.core.View;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Mar 21, 2012  
 */
public enum Phase {

  /**
   * Action phase.
   */
  ACTION(Action.class)
  {
     @Override
     public String id(Annotation annotation) throws ClassCastException
     {
        return ((Action)annotation).id();
     }
  },

  /**
   * Render phase.
   */
  RENDER(View.class)
  {
     @Override
     public String id(Annotation annotation) throws ClassCastException
     {
        return ((View)annotation).id();
     }
  },

  /**
   * Resource phase.
   */
  RESOURCE(Resource.class)
  {
     @Override
     public String id(Annotation annotation) throws ClassCastException
     {
        return ((Resource)annotation).id();
     }
  };

  /** . */
  public final Class<? extends Annotation> annotation;

  Phase(Class<? extends Annotation> annotation)
  {
     this.annotation = annotation;
  }

  public abstract String id(Annotation annotation) throws ClassCastException;
}
