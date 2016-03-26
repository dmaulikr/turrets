package com.turrets.scenes;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Example {

	   @Element
	   private String name;

	   @Attribute
	   private int id;
	   
	   public Example() {
		   
	   }
	   
	   public Example(String name, int id) {
		   this.name = name;
		   this.id = id;
	   }
}
