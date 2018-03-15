package org.modules.b;

import org.modules.a.Thing;
import org.modules.c.Ting;

public class Main {

  public static void main(String[] args) {
    printInfo(Thing.class);
    printInfo(Ting.class);
    printInfo(Main.class);
    System.out.println("Mem:     " + Runtime.getRuntime().maxMemory());
  }

  private static void printInfo(Class clazz) {
    System.out.println("Class:   " + clazz.getCanonicalName());
    System.out.println("Module:  " + clazz.getModule().getName());
    System.out.println("Packages:");    
    Package[] packages = clazz.getClassLoader().getDefinedPackages();
    for (int i = 0; i < packages.length; i++) {
      Package p = packages[i];
      System.out.println("- " + p.getName());
    }
    System.out.println();
  }
}
