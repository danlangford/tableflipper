/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// in an attempt to jlink i ran into problems with many dependencies including commons-lang3
// i realized the functionality needed from commons-lang3 was minimal so i copied it over
//
// https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/StringUtils.java
//
// i ran into other dependency issues using jlink and postponed the effort around jlink

package dan.langford.tableflipper.cli;

public class Lang3Utils {
  public static boolean isBlank(CharSequence cs) {
    int strLen;
    if (cs != null && (strLen = cs.length()) != 0) {
      for (int i = 0; i < strLen; ++i) {
        if (!Character.isWhitespace(cs.charAt(i))) {
          return false;
        }
      }

      return true;
    } else {
      return true;
    }
  }

  public static boolean isEmpty(CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  public static boolean isNumeric(CharSequence cs) {
    if (isEmpty(cs)) {
      return false;
    } else {
      int sz = cs.length();

      for (int i = 0; i < sz; ++i) {
        if (!Character.isDigit(cs.charAt(i))) {
          return false;
        }
      }

      return true;
    }
  }

  public static int toInt(String str) {
    return toInt(str, 0);
  }

  public static int toInt(String str, int defaultValue) {
    if (str == null) {
      return defaultValue;
    } else {
      try {
        return Integer.parseInt(str);
      } catch (NumberFormatException var3) {
        return defaultValue;
      }
    }
  }

  public static boolean isAlphaSpace(CharSequence cs) {
    if (cs == null) {
      return false;
    } else {
      int sz = cs.length();

      for (int i = 0; i < sz; ++i) {
        if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ') {
          return false;
        }
      }

      return true;
    }
  }

  public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
    if (str != null && searchStr != null) {
      int len = searchStr.length();
      int max = str.length() - len;

      for (int i = 0; i <= max; ++i) {
        if (regionMatches(str, true, i, searchStr, 0, len)) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  static boolean regionMatches(
      CharSequence cs,
      boolean ignoreCase,
      int thisStart,
      CharSequence substring,
      int start,
      int length) {
    if (cs instanceof String && substring instanceof String) {
      return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
    } else {
      int index1 = thisStart;
      int index2 = start;
      int tmpLen = length;
      int srcLen = cs.length() - thisStart;
      int otherLen = substring.length() - start;
      if (thisStart >= 0 && start >= 0 && length >= 0) {
        if (srcLen >= length && otherLen >= length) {
          while (tmpLen-- > 0) {
            char c1 = cs.charAt(index1++);
            char c2 = substring.charAt(index2++);
            if (c1 != c2) {
              if (!ignoreCase) {
                return false;
              }

              if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                  && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
              }
            }
          }

          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }
}
