/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.sftp.api;

import static org.mule.extension.sftp.internal.SftpUtils.normalizePath;

import java.io.Serializable;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.mule.extension.file.common.api.FileAttributes;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import com.jcraft.jsch.SftpATTRS;

/**
 * Metadata about a file in a SFTP server
 *
 * @since 1.0
 */
public class SftpFileAttributes implements FileAttributes, Serializable {

  @Parameter
  private LocalDateTime timestamp;

  @Parameter
  private long size;

  @Parameter
  private boolean regularSize;

  @Parameter
  private boolean directory;

  @Parameter
  private boolean symbolicLink;

  @Parameter
  private String name;

  @Parameter
  private String path;

  /**
   * Creates a new instance
   *
   * @param uri the file's {@link URI}
   * @param attrs the {@link SftpATTRS} which represents the file on the SFTP server
   */
  public SftpFileAttributes(URI uri, SftpATTRS attrs) {
    Date timestamp = new Date(((long) attrs.getMTime()) * 1000L);
    this.timestamp = asDateTime(timestamp.toInstant());
    this.size = attrs.getSize();
    this.regularSize = attrs.isReg();
    this.directory = attrs.isDir();
    this.symbolicLink = attrs.isLink();
    this.path = uri.getPath();
    String name = FilenameUtils.getName(uri.getPath());
    this.name = name != null ? name : "";
  }

  /**
   * @return The last time the file was modified
   */
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getSize() {
    return size;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRegularFile() {
    return regularSize;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDirectory() {
    return directory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSymbolicLink() {
    return symbolicLink;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPath() {
    return normalizePath(path);
  }

  @Override
  public String getName() {
    return name;
  }

  private LocalDateTime asDateTime(Instant instant) {
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
  }
}
