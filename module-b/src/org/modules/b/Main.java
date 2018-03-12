package org.modules.b;

import org.modules.a.Thing;
import org.modules.c.Ting;

public class Main {

  public static void main(String[] args) {
    System.out.println(Thing.class.getModule().getName());
    System.out.println(Ting.class.getModule().getName());
  }
}

