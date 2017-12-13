public class Inheritance2{

	public void objectCast(){
		Parent2 parent=new Parent2();
      Child2 child=new Child2();
        
      Parent2 parent2=child;
      Child2 child2=(Child2)parent2;
	}

	public void callPrintName(){
		Parent2 parent1=new Parent2();
		Parent2 parent2=new Child2();
		Parent2 parent3=new ChildOther();

		parent1.printName();
		parent2.printName();
		parent3.printName();
	}
	
	public static void main(String[] args){
		Inheritance2 sample = new Inheritance2();
		sample.callPrintName();
	}
}