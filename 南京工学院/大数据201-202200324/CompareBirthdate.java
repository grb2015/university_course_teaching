package ¿ÎÉè1;
import java.util.Comparator;
public class CompareBirthdate implements Comparator<Friend> {
public int compare(Friend stu1, Friend stu2) {
		
		return stu1.birthdate.compareTo(stu2.birthdate);
	}
}