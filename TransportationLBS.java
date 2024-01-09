/*
 * STUDENT NAME: DEEMAH ADIL ALMUSBEH 
 * OPTIMIZATION AND REGRESSION PROJECT
 * LOCAL BASIC SEARCH
 *
 */
package transportationlbs;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author deema
 */

public class TransportationLBS {
    
    //distances bus that goes to close neighborhoods/locations
    private static int[] GenerateInitial1(int[]bus1){
      
        Random rand = new Random();
        //generate random near the university 
        int min=1;
        int max=10;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        
     for(int i = 0; i <bus1.length ;i++) {  
        
         int randomdistance = rand.nextInt(random_int);
        bus1[i]=randomdistance;
            
            
            }   
        return bus1;
    }
    //distances for bus that goes to further neighborhoods/locations
    private static int[] GenerateInitial2(int[]bus2){
      
        //generate random locations further from the university 
        Random rand = new Random();
        
        int min=11;
        int max=20;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
     for(int i = 0; i <bus2.length ;i++) {  
        
        int randomdistance = rand.nextInt(random_int);
        bus2[i]=randomdistance;
            
            }   
        return bus2;
    }
    
    //distances for bus that goes too far neighborhoods/locations 
    private static int[] GenerateInitial3(int[]bus3){
      
        Random rand = new Random();
        int min=21;
        int max=30;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
     for(int i = 0; i <bus3.length ;i++) {  
        
        int randomdistance = rand.nextInt(random_int);
        bus3[i]=randomdistance;
            
            
            }   
        return bus3;
    }
    
    //to get better solution
     private static int[] GenerateNeighbor1(int[]distances){
     
       Random rand = new Random();
        int min=1;
        int max=10;
     for (int i = 0; i < distances.length; i++)   
        {  
      int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
      int randomdistance = rand.nextInt(random_int);
      
        distances[i]=((distances[i]+randomdistance)/2)+1;

        }
     //sorting the distances so the bus starts with the closest location. 
      Arrays.sort(distances);
     return distances;
     }
        
     private static int[] GenerateNeighbor2(int[]distances){
      Random rand = new Random();
        int min=11;
        int max=20;
      
     for (int i = 0; i < distances.length; i++)   
        {  
    
       int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
       int randomdistance = rand.nextInt(random_int);
      
        distances[i]=((distances[i]+randomdistance)/2)+3;
   
         
        }  
            //Sorting the distances so the bus starts with the closest location. 
            Arrays.sort(distances);
           return distances;
        }
     
     
     private static int[] GenerateNeighbor3(int[]distances){
     
      Random rand = new Random();
        int min=21;
        int max=30;
     for (int i = 0; i < distances.length; i++)   
        {  
    
      int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
      int randomdistance = rand.nextInt(random_int);
      
        distances[i]=((distances[i]+randomdistance)/2)+6;
        
        }
     //Sorting the distances so the bus starts with the closest location.
     Arrays.sort(distances);
      return distances;
     }
     
    //constraints:
   //checks if total miles for all locations are less than 150 
   //and every 2 locations are less than 50 miles 
   //and the bus doesn't go to the same location twice
    private static boolean CheckConstraint1(int[]distances){
        
        if (distances[0]+distances[1]+distances[2]+distances[3]<=150){
            for(int i=0; i<distances.length;i++){
                for(int j=i+1;j<distances.length;j++){
                    //if two locations are >50 miles return false
                    if(distances[i]+distances[j]>50){
                        return false;
                    }
                    //if two locations are the same return false
                    if(distances[i]==distances[j]){
                        return false;
                    }
                }
            }
            
            
        }
       return true;
    }
    //checks if any 2 busses go to the same location
    private static boolean CheckConstraintfull(int[]bus1,int[]bus2,int[]bus3){
        for(int i=0; i<bus1.length;i++){
            for(int j=0;j<bus2.length;j++){
                for(int k=0;k<bus3.length;k++){
                    if(bus1[i]==bus2[j]||bus1[i]==bus3[k]||bus2[j]==bus3[k]){
                        return false;
                       
                    }
                }
            }
        }
        
        return true;
    }
    //compares two solutions and takes the better one
   private static int[] bettersolution(int[]distances, int[]distances2){
       
       int[]bettersolution;
       if(distances[0]+distances[1]+distances[2]+distances[3]>distances2[0]+distances2[1]+distances2[2]+distances2[3]){
           bettersolution=distances2;
       }
       else {
           bettersolution=distances;
       }
      return bettersolution; 
   }
    
    public static void main(String[] args) {

        
        int r=1;
        //Arrays for the distances of each bus
        int[] bus1= new int[4];
        int[] bus2= new int[4];
        int[] bus3= new int[4];
        int[] d1=bus1=GenerateInitial1(bus1);
        int[] d2=bus2=GenerateInitial2(bus2);
        int[] d3=bus3=GenerateInitial3(bus3);
       
        
        //check constraint for initial solution: if it's false generate another solution
            while(CheckConstraintfull(bus1,bus2,bus3)==false){
               GenerateInitial1(bus1);
               GenerateInitial2(bus2);
               GenerateInitial3(bus3);
              }
            while(CheckConstraint1(bus1)==false){
            GenerateInitial1(bus1);
       }
            while(CheckConstraint1(bus2)==false){
                GenerateInitial2(bus2);
            }
              while(CheckConstraint1(bus3)==false){
                  GenerateInitial3(bus3);
              }
        
        d1=GenerateNeighbor1(bus1);
        d2=GenerateNeighbor2(bus2);
        d3=GenerateNeighbor3(bus3);
        int[] x1=bettersolution(d1,bus1);
        int[] x2=bettersolution(d2,bus2);
        int[] x3=bettersolution(d3,bus3);
        
        bus1=x1;
        bus2=x2;
        bus3=x3;
       
       
        //generates solutions until it finds the best local solution that satisfies the constraints
   while(CheckConstraintfull(bus1,bus2,bus3)==false||CheckConstraint1(bus1)==false||CheckConstraint1(bus2)==false||CheckConstraint1(bus3)==false 
           || r<30){
       //generate neighbors
       d1=GenerateNeighbor1(bus1);
       d2=GenerateNeighbor2(bus2);
       d3=GenerateNeighbor3(bus3);
       
       //take the better solution
       x1=bettersolution(d1,bus1);
       x2=bettersolution(d2,bus2);
       x3=bettersolution(d3,bus3);
        //give bus1,bus2,bus3 values of better solution
        bus1=x1;
        bus2=x2;
        bus3=x3;
        //prints the arrays
       System.out.println(Arrays.toString(bus1)+Arrays.toString(bus2)+Arrays.toString(bus3));
                
       r++;
       }

     System.out.println("Solution is: \nBus 1:"+Arrays.toString(bus1)+"\nBus 2:"+Arrays.toString(bus2)+"\nBus 3:"+Arrays.toString(bus3));
                   
   
    }
    
}
