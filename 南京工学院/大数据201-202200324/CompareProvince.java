package ¿ÎÉè1;
import java.util.Comparator;
public class CompareProvince implements Comparator<Friend> {
public int compare(Friend stu1, Friend stu2) {
		
		return stu1.province.compareTo(stu2.province);
	}
}