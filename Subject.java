package rovuSystem;


import java.util.ArrayList;
import java.util.List;

public class Subject {
	
   private List<Observer> observers = new ArrayList<Observer>();
   private String state;

   public String getState() {
      return state;
   }

   public void setState(String string) {
      this.state = string;
      notifyAllObservers();
   }

   public void attach(Observer observer){
      observers.add(observer);		
   }

   public void notifyAllObservers(){
      for (Observer observer : observers) {
         observer.stopRovers();
      }
   } 	

	
	


}
