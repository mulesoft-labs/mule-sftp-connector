/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.sftp.internal.sampledata;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mule.extension.sftp.api.SftpFileAttributes;
import org.mule.extension.sftp.internal.connection.SftpFileSystem;
import org.mule.sdk.api.data.sample.SampleDataException;

import com.jcraft.jsch.SftpATTRS;
import org.mule.sdk.api.runtime.operation.Result;

public class FileInformationSampleDataProviderTest {

  public static final String FILE_PATH = "/";
  @Mock
  SftpFileSystem connection;

  @InjectMocks
  FileInformationSampleDataProvider provider = new FileInformationSampleDataProvider();

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getSampleData_success() throws SampleDataException, URISyntaxException {
    SftpATTRS attrs = Mockito.mock(SftpATTRS.class);
    SftpFileAttributes attributes = new SftpFileAttributes(new URI(FILE_PATH), attrs);
    Mockito.when(connection.readFileAttributes(FILE_PATH)).thenReturn(attributes);

    Result<SftpFileAttributes, Void> sample = provider.getSample();

    assertNotNull(sample);
    assertNotNull(sample.getOutput());
    assertFalse(sample.getAttributes().isPresent());
  }

  @Test
  public void getSampleData_noDataAvailable() {
    try {
      provider.getSample();
      assertFalse(true);
    } catch (SampleDataException ex) {
      assertTrue("Should have thrown exception", true);
      assertEquals("No data available", ex.getMessage());
    }

  }
}
