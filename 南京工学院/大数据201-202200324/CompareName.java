package ¿ÎÉè1;
import java.util.Comparator;

public class CompareName implements Comparator<Friend> {


	public int compare(Friend stu1,Friend stu2) {
		
		return stu1.name.compareTo(stu2.name);
	}

}
