import java.util.ArrayList;

public class ListSample
{
	public static void main(String[] args) 
	{
		ListSample sample=new ListSample();
		//sample.checkArrayList1();
		//sample.checkArrayList2();
		//sample.checkArrayList3();
		sample.checkArrayList4();
	}

	public void checkArrayList1(){
		ArrayList<String> list1=new ArrayList<String>();
		//list1.add(new Object());
		list1.add("ArrayListSample");
		//list1.add(new Double(1));
	}

	public void checkArrayList2(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add(1,"A1");
		/*
		for (String tmp:list){
			System.out.println(tmp);
		}
		
		
		ArrayList<String> list2=new ArrayList<String>();
		list2.add("0 ");
		list2.addAll(list);
		*/
		ArrayList<String> list2=list; //치환 수행
		list.add("Ooops");
		for (String tmp:list2){
			System.out.println("List2 "+tmp);
		}
	}

	public void checkArrayList3(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		/*
		int listSize=list.size();
		for(int loop=0; loop<listSize; loop++){
			System.out.println("list.get("+loop+")="+list.get(loop));
		}
		*/
		String[] tempArray=new String[2];
		String[] strList=list.toArray(tempArray);
		for(String tmp:tempArray){
			System.out.println(tmp);
		}
	}

	public void checkArrayList4(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("A");

		//System.out.println("Removed "+list.remove(0));
		//System.out.println(list.remove("A"));

		//ArrayList<String> temp=new ArrayList<String>();
		//temp.add("A");
		//list.removeAll(temp);

		System.out.println(list.set(1,"AAAA"));

		for(int loop=0;loop<list.size();loop++){
			System.out.println("list.get("+loop+")="+list.get(loop));
		}
	}
}
 