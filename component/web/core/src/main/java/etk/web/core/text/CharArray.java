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
package etk.web.core.text;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com Mar
 * 23, 2012
 */
public interface CharArray {

  /**
   * Returns the number of characters contained in this array.
   * 
   * @return the length
   */
  int getLength();

  void write(OutputStream out) throws IOException, NullPointerException;

  void write(Appendable appendable) throws IOException, NullPointerException;

  public static class Simple implements CharArray {

    /** . */
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    /** . */
    private final CharSequence   chars;

    /** . */
    private final byte[]         bytes;

    public Simple(CharSequence sequence) {
      try {
        this.chars = sequence;
        this.bytes = UTF_8.newEncoder().encode(CharBuffer.wrap(chars)).array();
      } catch (CharacterCodingException e) {
        throw new UndeclaredThrowableException(e);
      }
    }

    public int getLength() {
      return chars.length();
    }

    public void write(OutputStream out) throws IOException, NullPointerException {
      if (out == null) {
        throw new NullPointerException("No null dst argument accepted");
      }
      out.write(bytes);
    }

    public void write(Appendable appendable) throws IOException {
      if (appendable == null) {
        throw new NullPointerException("No null dst argument accepted");
      }
      appendable.append(chars);
    }
  }
}
