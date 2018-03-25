package com.practice.example;

import java.util.Vector;

//outer class can only be public , abstract or final only but it can never be private or protected
//class can be public abstract or public final but not public abstract final
// An abstract class can have abstract , non- abstract classes , methods  
//if a class have abstract method then that class must be abstract
//if a class have abstract class inside , it is not necessary that class should also be abstract
public  class MyNewVector {

     private final static class MyNewVector2{
		// inner class can be private , protected , public , static , final , abstract 
    	 // abstract and final can never be come together 
    	 //class or method can be either be abstract or final
	 }
     
     protected final  class MyNewVector3{
 		
 	 }
     
     public final static class MyNewVector4{
  		
  	 }
     
     public abstract  class MyNewVector5{
   		
   	}
}

// outer class can never be protected
/*protected class MyNewVector6{
	
}*/
//outer class can never be private
/*private class MyNewVector7{
	
}*/