/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.sftp.internal.sampledata;

import org.mule.extension.sftp.api.SftpFileAttributes;
import org.mule.extension.sftp.internal.connection.SftpFileSystem;
import org.mule.sdk.api.annotation.param.Connection;
import org.mule.sdk.api.data.sample.SampleDataException;
import org.mule.sdk.api.data.sample.SampleDataProvider;
import org.mule.sdk.api.runtime.operation.Result;

public class FileInformationSampleDataProvider implements SampleDataProvider {

  public static final String DEFAULT_PATH = "/";

  @Connection
  SftpFileSystem connection;

  @Override
  public String getId() {
    return getClass().getSimpleName();
  }

  @Override
  public Result<SftpFileAttributes, Void> getSample() throws SampleDataException {
    SftpFileAttributes attributes = connection.readFileAttributes(DEFAULT_PATH);

    if (attributes == null) {
      throw new SampleDataException("No data available", SampleDataException.NO_DATA_AVAILABLE);
    }

    return Result.<SftpFileAttributes, Void>builder().output(attributes).build();
  }
}

