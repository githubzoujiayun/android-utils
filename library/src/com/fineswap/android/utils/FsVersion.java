/**
 * Versioning Utility - Part of Fineswap Android Utilities.
 * Copyright (C) 2014 Fineswap Blog & App. All rights reserved.
 * http://fineswap.com/
 *
 * Released under the MIT license
 * http://en.wikipedia.org/wiki/MIT_License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.fineswap.android.utils;

/**
 * A helpful versioning class to define arbitrary objects and assign a version
 * to them.
 *
 * @author Noor Dawod
 * @since 1.0
 */
public class FsVersion
  implements
    com.fineswap.android.aux.FsSystem,
    com.fineswap.android.aux.FsException {

  /**
   * A unique identifier for this object.
   *
   * @since 1.0
   */
  public final String classId;

  /**
   * Major part of the version.
   *
   * @since 1.0
   */
  public final int major;

  /**
   * Minor part of the version.
   *
   * @since 1.0
   */
  public final int minor;

  /**
   * Patch level of the version.
   *
   * @since 1.0
   */
  public final int patch;

  /**
   * Instantiates a new versioning object with the specified class id and
   * major; use zero for both minor and patch level.
   *
   * @param classId {@link #classId}
   * @param major {@link #major}
   * @since 1.0
   */
  public FsVersion(String classId, int major) {
    this(classId, major, 0);
  }

  /**
   * Instantiates a new version for an object with the specified class id, major
   * and manor; use zero for patch level.
   *
   * @param classId {@link #classId}
   * @param major {@link #major}
   * @param minor {@link #minor}
   * @since 1.0
   */
  public FsVersion(String classId, int major, int minor) {
    this(classId, major, minor, 0);
  }

  /**
   * Instantiates a new version for an object with the specified class id,
   * major, manor and patch level.
   *
   * @param classId {@link #classId}
   * @param major {@link #major}
   * @param minor {@link #minor}
   * @param patch {@link #patch}
   * @since 1.0
   */
  public FsVersion(String classId, int major, int minor, int patch) {
    if(null == classId) {
      throw EXCEPTION_NULL_VALUE;
    }
    this.classId = classId;
    this.major = major;
    this.minor = minor;
    this.patch = patch;
  }

  /**
   * Instantiates a new version for an object with the specified class id and
   * version representation (as a string).
   *
   * @param classId {@link #classId}
   * @param versionRep {@link #getFullVersion()}
   * @since 1.0
   */
  public FsVersion(String classId, String versionRep) throws NumberFormatException {
    if(null == classId || null == versionRep) {
      throw EXCEPTION_NULL_VALUE;
    }
    String[] parsed = PATTERN_DOT.split(versionRep, 3);
    if(null == parsed || 1 > parsed.length) {
      throw new NumberFormatException();
    }
    this.classId = classId;
    this.major = Integer.parseInt(parsed[0]);
    this.minor = 1 < parsed.length ? Integer.parseInt(parsed[1]) : 0;
    this.patch = 2 < parsed.length ? Integer.parseInt(parsed[2]) : 0;
  }

  /**
   * @since 1.0
   */
  @Override
  public String toString() {
    return classId + "-" + getFullVersion();
  }

  /**
   * @since 1.0
   */
  @Override
  public boolean equals(Object o) {
    return
      null != o &&
      o instanceof FsVersion &&
      classId.equals(((FsVersion)o).classId) &&
      major == ((FsVersion)o).major &&
      minor == ((FsVersion)o).minor &&
      patch == ((FsVersion)o).patch;
  }

  /**
   * @since 1.0
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Get a short representation of this version (without the patch level).
   *
   * @return Short version representation
   * @since 1.0
   */
  public String getVersion() {
    return major + "." + minor;
  }

  /**
   * Get a full representation of this version.
   *
   * @return Full version representation
   * @since 1.0
   */
  public String getFullVersion() {
    return major + "." + minor + "." + patch;
  }

  /**
   * Tests whether the version identified by specified parameters is newer than
   * this version.
   *
   * @param versionMajor {@link #major}
   * @param versionMinor {@link #minor}
   * @return True if and only if the passed version is newer, false otherwise
   * @since 1.0
   */
  public boolean isNewerThan(int versionMajor, int versionMinor) {
    return
      major > versionMajor ||
      (major == versionMajor && minor > versionMinor);
  }

  /**
   * Tests whether the version identified by specified parameters is newer than
   * this version.
   *
   * @param versionMajor {@link #major}
   * @param versionMinor {@link #minor}
   * @param versionPatch {@link #patch}
   * @return True if and only if the passed version is newer, false otherwise
   * @since 1.0
   */
  public boolean isNewerThan(int versionMajor, int versionMinor, int versionPatch) {
    return
      isNewerThan(versionMajor, versionMinor) ||
      (major == versionMajor && minor == versionMinor && patch > versionPatch);
  }

  /**
   * Tests whether the passed version is newer than this version.
   *
   * @see FsVersion
   * @param version The version to test against
   * @return True if and only if the passed version is newer, false otherwise
   * @since 1.0
   */
  public boolean isNewerThan(FsVersion version) {
    return isNewerThan(version.major, version.minor, version.patch);
  }

  /**
   * Tests whether the version identified by specified parameters is older than
   * this version.
   *
   * @param versionMajor {@link #major}
   * @param versionMinor {@link #minor}
   * @return True if and only if the passed version is older, false otherwise
   * @since 1.0
   */
  public boolean isOlderThan(int versionMajor, int versionMinor) {
    return
      major < versionMajor ||
      (major == versionMajor && minor < versionMinor);
  }

  /**
   * Tests whether the version identified by specified parameters is older than
   * this version.
   *
   * @param versionMajor {@link #major}
   * @param versionMinor {@link #minor}
   * @param versionPatch {@link #patch}
   * @return True if and only if the passed version is older, false otherwise
   * @since 1.0
   */
  public boolean isOlderThan(int versionMajor, int versionMinor, int versionPatch) {
    return
      isOlderThan(versionMajor, versionMinor) ||
      (major == versionMajor && minor == versionMinor && patch < versionPatch);
  }

  /**
   * Tests whether the passed version is older than this version.
   *
   * @see FsVersion
   * @param version The version to test against
   * @return True if and only if the passed version is older, false otherwise
   * @since 1.0
   */
  public boolean isOlderThan(FsVersion version) {
    return isOlderThan(version.major, version.minor, version.patch);
  }

}