public class QuadraticCalculator{
  public static float getDiscriminant(float coefA, float coefB, float coefC){
    return (float)Math.pow(coefB, 2) - 4 * coefA * coefC;
  }    
  
  public static float getX1(float coefA, float coefB, float disc){
    return (- coefB + (float)Math.sqrt(disc)) / 2 * coefA;
  }
  
  public static float getX2(float coefA, float coefB, float disc){
    return (- coefB - (float)Math.sqrt(disc)) / 2 * coefA;
  }
}
