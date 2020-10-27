/*
Name: Run Lin
Homework: 1
Class: CS636
*/

public class Box2 {
   // PRIVATE VAR
   private int x, y;
   // CONSTRUCTOR
   public Box2(int x, int y)
   {
     this.x = x;
     this.y = y;
   }

   // ACCESSOR
   public int getX()
   {
      return this.x;
   }
   public int getY()
   {
      return this.y;
   }

   // MUTATOR
   public void setX(int x)
   {
      this.x = x;
   }
   public void setY(int y)
   {
      this.y = y;
   }

   // Override
   @Override
   public boolean equals(Object comp)
   {  
      // source geeksforgeeks
      if (comp == this)
         return true;
      
      // source geeksforgeeks
      /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
      if (!(comp instanceof Box2)) { 
         return false; 
      }

      Box2 box = (Box2) comp;
      return (Integer.compare(x, box.x) == 0) && (Integer.compare(y, box.y) == 0);
   }

   @Override
   public int hashCode()
   {
      // return the area of the rectangle
      return (this.x * this.y);
   }

   @Override
   public String toString()
   {
      return this.x + "x" + this.y;
   }

   public static void main(String[] args)
   {
      Box2 b = new Box2(10, 12);
      Box2 c = new Box2(10, 12);

      System.out.println("b.isEquals(c)? " + b.equals(c));
      System.out.println("box b hash (ret area): " + b.hashCode());
      System.out.println("box c hash (ret area): " + c.hashCode());
      System.out.println(b.toString());
      System.out.println(c.toString());
   }
}
