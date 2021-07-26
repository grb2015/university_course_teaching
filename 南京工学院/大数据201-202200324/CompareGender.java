package ¿ÎÉè1;
import java.util.Comparator;
public class CompareGender implements Comparator<Friend>{
public int compare(Friend stu1, Friend stu2) {
		
		return stu1.gender.compareTo(stu2.gender);
	}

}
