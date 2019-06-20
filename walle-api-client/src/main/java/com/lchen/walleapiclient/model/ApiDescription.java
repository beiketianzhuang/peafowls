package com.lchen.walleapiclient.model;

import io.swagger.models.Operation;

import java.util.List;

/**
 * @author : lchen
 * @date : 2019/6/20
 */
public class ApiDescription {
  private final String path;
  private final String description;
  private final List<Operation> operations;
  private final Boolean hidden;

  public ApiDescription(String path, String description, List<Operation> operations, Boolean hidden) {
    this.path = path;
    this.description = description;
    this.operations = operations;
    this.hidden = hidden;
  }

  public String getPath() {
    return path;
  }

  public String getDescription() {
    return description;
  }

  public List<Operation> getOperations() {
    return operations;
  }

  public Boolean isHidden() {
    return hidden;
  }
}
