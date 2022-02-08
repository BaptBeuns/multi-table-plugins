/*
 * Copyright © 2021 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.format;

import io.cdap.plugin.ForwardingConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A JDBC {@link Connection} that delegates all methods to another {@link Connection}, but automatically call
 * the {@link Statement#setFetchSize(int)} for all {@link Statement} created via this class.
 */
public class ConnectionWithFetchSize extends ForwardingConnection {

  private final int fetchSize;

  public ConnectionWithFetchSize(Connection delegate, int fetchSize) {
    super(delegate);
    this.fetchSize = fetchSize;
  }

  private <T extends Statement> T setFetchSize(T stmt) throws SQLException {
    stmt.setFetchSize(fetchSize);
    return stmt;
  }

  @Override
  public Statement createStatement() throws SQLException {
    return setFetchSize(super.createStatement());
  }

  @Override
  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return setFetchSize(super.prepareStatement(sql));
  }

  @Override
  public CallableStatement prepareCall(String sql) throws SQLException {
    return setFetchSize(super.prepareCall(sql));
  }

  @Override
  public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
    return setFetchSize(super.createStatement(resultSetType, resultSetConcurrency));
  }

  @Override
  public PreparedStatement prepareStatement(String sql,
                                            int resultSetType, int resultSetConcurrency) throws SQLException {
    return setFetchSize(super.prepareStatement(sql, resultSetType, resultSetConcurrency));
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
    return setFetchSize(super.prepareCall(sql, resultSetType, resultSetConcurrency));
  }

  @Override
  public Statement createStatement(int resultSetType,
                                   int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return setFetchSize(super.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int resultSetType,
                                            int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return setFetchSize(super.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public CallableStatement prepareCall(String sql, int resultSetType,
                                       int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return setFetchSize(super.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    return setFetchSize(super.prepareStatement(sql, autoGeneratedKeys));
  }

  @Override
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    return setFetchSize(super.prepareStatement(sql, columnIndexes));
  }

  @Override
  public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    return setFetchSize(super.prepareStatement(sql, columnNames));
  }
}
