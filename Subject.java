package rovuSystem;

public abstract class Subject {
	
	Observer[] observers;
	int numberOfObservers;
	
	
	Subject() {
		numberOfObservers = 0;
		observers = new Observer[numberOfObservers];
	}
	
	void attach(Observer ob) {
		observers[numberOfObservers] = ob;
		numberOfObservers++;
	}
	
	void detach(Observer ob) {
		for (int i = 0; i < numberOfObservers; i++) {
			if (observers[i] == ob) {
				for (int j = i+1; j < numberOfObservers; j++) {
					observers[i] = observers[j];
					i++;
				} 
				
				numberOfObservers--;
			}
		}
	}
	
	void notifyAllObservers() {
		//send signal to observers
	}

}
