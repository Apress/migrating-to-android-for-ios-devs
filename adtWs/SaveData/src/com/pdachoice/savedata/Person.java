package com.pdachoice.savedata;

import java.io.Serializable;

public class Person implements Serializable {

  private static final long serialVersionUID = 1L;
  private String name;

  public Person(String name) {
    super();
    this.setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // ...
}
