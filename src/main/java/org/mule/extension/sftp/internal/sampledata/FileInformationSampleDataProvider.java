/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.sftp.internal.sampledata;

import java.io.InputStream;
import java.util.List;

import org.mule.extension.file.common.api.FileAttributes;
import org.mule.extension.file.common.api.FileConnectorConfig;
import org.mule.extension.file.common.api.FileSystem;
import org.mule.extension.file.common.api.exceptions.FileError;
import org.mule.extension.sftp.api.SftpFileAttributes;
import org.mule.extension.sftp.internal.connection.SftpFileSystem;
import org.mule.runtime.core.api.util.StringUtils;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.sdk.api.annotation.param.Config;
import org.mule.sdk.api.annotation.param.Connection;
import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.data.sample.SampleDataException;
import org.mule.sdk.api.data.sample.SampleDataProvider;

public class FileInformationSampleDataProvider implements SampleDataProvider {

  @Parameter
  String path;

  @Config
  FileConnectorConfig config;

  @Connection
  SftpFileSystem connection;


  @Override
  public String getId() {
    return getClass().getSimpleName();
  }

  @Override
  public Result<SftpFileAttributes, Void> getSample() throws SampleDataException {
    connection.changeToBaseDir();

    if (StringUtils.isBlank(path)) {
      throw new SampleDataException(String.format("Illegal path specified."), FileError.ILLEGAL_PATH);
    }
    SftpFileAttributes attributes = fileSystem.readFileAttributes(path);

    if (attributes == null) {
      throw new SampleDataException("No data available", SampleDataException.NO_DATA_AVAILABLE);
    }

    return Result.<SftpFileAttributes, Void>builder().output(attributes).build();
  }
}

