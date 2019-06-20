package com.lchen.walleapiclient.model;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

/**
 * @author : lchen
 * @date : 2019/6/20
 */
public class Tag {
  private final String name;
  private final String description;

  public Tag(String name, String description) {
    this.name = checkNotNull(emptyToNull(name));
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Tag tag = (Tag) o;
    return Objects.equal(name, tag.name) &&
        Objects.equal(description, tag.description);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name, description);
  }
}
