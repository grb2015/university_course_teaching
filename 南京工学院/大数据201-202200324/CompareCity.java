package ¿ÎÉè1;
import java.util.Comparator;
public class CompareCity implements Comparator<Friend> {
public int compare(Friend stu1, Friend stu2) {
		
		return stu1.city.compareTo(stu2.city);
	}
}