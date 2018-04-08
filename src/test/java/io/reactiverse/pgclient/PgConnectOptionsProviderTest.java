/*
 * Copyright (C) 2017 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.reactiverse.pgclient;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Billy Yuan <billy112487983@gmail.com>
 */
public class PgConnectOptionsProviderTest {
  private String connectionUri;
  private PgConnectOptions expectedConfiguration;
  private PgConnectOptions actualConfiguration;

  @Test
  public void testValidUri1() {
    connectionUri = "postgresql://";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);

    expectedConfiguration = new PgConnectOptions();

    assertEquals(expectedConfiguration, actualConfiguration);
  }

  @Test
  public void testValidUri2() {
    connectionUri = "postgresql://myhost";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);

    expectedConfiguration = new PgConnectOptions()
      .setHost("myhost");

    assertEquals(expectedConfiguration, actualConfiguration);
  }

  @Test
  public void testValidUri3() {
    connectionUri = "postgresql://myhost:5433";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);

    expectedConfiguration = new PgConnectOptions()
      .setHost("myhost")
      .setPort(5433);

    assertEquals(expectedConfiguration, actualConfiguration);
  }

  @Test
  public void testValidUri4() {
    connectionUri = "postgresql://myhost/mydb";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);

    expectedConfiguration = new PgConnectOptions()
      .setHost("myhost")
      .setDatabase("mydb");

    assertEquals(expectedConfiguration, actualConfiguration);
  }

  @Test
  public void testValidUri5() {
    connectionUri = "postgresql://user@myhost";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);

    expectedConfiguration = new PgConnectOptions()
      .setUsername("user")
      .setHost("myhost");

    assertEquals(expectedConfiguration, actualConfiguration);
  }

  @Test
  public void testValidUri6() {
    connectionUri = "postgresql://user:secret@myhost";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);

    expectedConfiguration = new PgConnectOptions()
      .setUsername("user")
      .setPassword("secret")
      .setHost("myhost");

    assertEquals(expectedConfiguration, actualConfiguration);
  }

  @Test
  public void testValidUri7() {
    connectionUri = "postgresql://other@localhost/otherdb?port=5433&password=secret";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);

    expectedConfiguration = new PgConnectOptions()
      .setUsername("other")
      .setPassword("secret")
      .setHost("localhost")
      .setPort(5433)
      .setDatabase("otherdb");

    assertEquals(expectedConfiguration, actualConfiguration);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidUri1() {
    connectionUri = "postgrsql://username";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidUri2() {
    connectionUri = "postgresql://username:password@loc//dbname";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidUri3() {
    connectionUri = "postgresql://user@:passowrd@localhost/dbname/qwer";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidUri4() {
    connectionUri = "postgresql://user:password@localhost:655355/dbname";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidUri5() {
    connectionUri = "postgresql://user@localhost?port=1234&port";
    actualConfiguration = PgConnectOptionsProvider.fromUri(connectionUri);
  }
}