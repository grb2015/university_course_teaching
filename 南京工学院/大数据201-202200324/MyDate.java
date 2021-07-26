package ����1;

public class MyDate implements java.io.Serializable
{ 
    private int year,month,day;                  //�����գ�˽�г�Ա����
    private static int thisYear;            //��ǰ��ݣ�˽�о�̬��Ա����
    static                                       //��̬��Ա������ʼ��
    {
        thisYear=2018;
//        thisYear = java.util.Calendar.getInstance().get(Calendar.YEAR); //��õ�ǰ���ڶ����е����ֵ��4.3.2��
//        thisYear = new java.util.GregorianCalendar().get(Calendar.YEAR); //��õ�ǰ���ڶ����е����ֵ��4.3.2��
    } 
    
    public MyDate(int year, int month, int day) throws DateFormatException
    {
                                        
    	this.set(year, month, day);                        //��3.2�����ñ���ĳ�Ա������//��5.2���÷��������׳����ڸ�ʽ�쳣
    } 
   public MyDate() {
	   this(1970,1,1);
   }
	
	public MyDate(String datestr)throws NumberFormatException,DateFormatException                                        //�޲������췽����ָ��ȱʡ���ڣ�����
    {
       if(datestr.isEmpty())
    	   throw new DateFormatException("�մ������ڴ���");
     int i=datestr.indexOf('��'),j=datestr.indexOf('��',i),k=datestr.indexOf('��',j);
     int year=Integer.parseInt(datestr.substring(0,i));
     int month=Integer.parseInt(datestr.substring(i+1,j));
     int day=Integer.parseInt(datestr.substring(j+1,k));
     this.set(year,month,day);
    } 
  
    

    //�������췽��������ͬ���������ء�
    public MyDate(MyDate date) 
    {
        this.set(date);
    }
  
  

public void set(int year, int month, int day) throws DateFormatException       //��������ֵ���㷨��ȫ���Ľ���5.2.3��
    {
	   if(year<=-2000||year>2500)
		   throw new DateFormatException(year+",��ݲ����ʣ���Ч���Ϊ-2000~2500.");
	   if(month<1||month>12)
		   throw new DateFormatException(month+"��,�շݴ���");
	   if(day<1||day>MyDate.daysOfMonth(year, month))
			throw new DateFormatException(year+"��"+month+"��"+day+"�գ����ڴ���");
	   
	  
        this.year = year;                                  //this.yearָ��ǰ����ĳ�Ա������yearָ����
        this.month= month; //this���ò���ʡ��
        this.day = day;
    } 
  
    public void set(MyDate date)                           //��������ֵ������
    {
        this.set(date.year, date.month, date.day);         //����ͬ����Ա����������ʹ��this()
    }
  
	public class DateFormatException extends IllegalArgumentException{
		public DateFormatException(String message)
		{super(message);
		}
		
		
	}



	//5.2.3�ڣ���5.2 �˷�������Ҫ�׳����ڸ�ʽ�쳣//throws DateFormatException
    //��Ϊ��date���������ڣ�û�д���

    public int getYear()                                   //������
    {
        return this.year;
    }
    public int getMonth()                                  //����·�
    {
        return this.month;
    }
    public int getDay()                                    //��õ�������
    {
        return this.day;
    }
  
    public String toString()                               //�������ڸ�ʽ�ַ�����2λ����
    {
        return this.year+"��"+String.format("%02d", this.month)+"��"+
               String.format("%02d", this.day)+"��";//+this.toWeekString();
    }

    public static int getThisYear()                        //��ý�����ݣ���̬����
    {
        return thisYear;                                   //���ʾ�̬��Ա����
    }

    public static boolean isLeapYear(int year)             //�ж�ָ������Ƿ����꣬��̬����
    {
        return year%400==0 || year%100!=0 && year%4==0;
    }
    public boolean isLeapYear()                            //�жϵ�ǰ���ڵ�����Ƿ����꣬����
    {
        return isLeapYear(this.year);                      //���þ�̬����
    }        

    //�Ƚ���this��date�������õ�ʵ��ֵ�Ƿ���ȡ�
    //��this��date����ͬһ��ʵ��������ȣ������Ƿֱ���������
    //ʵ������ֱ�Ƚ����ǵĸ���Ա����ֵ�Ƿ��Ӧ���
    public boolean equals(MyDate date)
    {
//        System.out.println(b+"��ִ��equals(MyDate date)����");
        //thisָ�����õ�ǰ�����Ķ���this.year�ȷ��ʵ�ǰ����ĳ�Ա��������3��this��ʡ�ԣ�
        return this==date ||           //��this��date����ͬһ��ʵ���������
               date!=null && this.year==date.year && this.month==date.month && this.day==date.day;
               //��this��date�ֱ���������ʵ������ֱ�Ƚ����ǵĸ���Ա����ֵ�Ƿ��Ӧ���
    } 
    
    public static int daysOfMonth(int year, int month)     //����ָ�����µ���������̬����
    {
        switch(month)                           //����ÿ�µ�����
        {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:  return 31; 
            case 4: case 6: case 9: case 11:  return 30;
            case 2:  return MyDate.isLeapYear(year) ? 29 : 28;
            default: return 0;
        }
    }
    public int daysOfMonth()                     //���ص�������
    {
        return daysOfMonth(this.year, this.month);
    } 

  
    public void tomorrow()                       //��this����ʵ�������ڸ�Ϊ֮��һ�����ڣ�û�з���ֵ
    {
        this.day = this.day%this.daysOfMonth()+1;
        if(this.day==1)
        {
            this.month = this.month%12+1;        //����
            if(this.month==1)                   //12�µ�����������1��
                this.year++;
        }
    }    
    public MyDate yestoday()                     //���ص�ǰ���ڵ�ǰһ������
    {
        MyDate date = new MyDate(this);          //ִ�п������췽��������ʵ����û�иı�this
        date.day--;
        if(date.day==0)
        {
        	date.month = (date.month-2+12)%12+1; //����
            if(date.month==12)                   //1�µ�������ȥ��12��
            	date.year--;
            date.day = daysOfMonth(date.year, date.month);
        }
        return date;                             //���ض���date���õ�ʵ��
    }  

   
    public static void main(String[] args)throws NumberFormatException,DateFormatException             //main����Ҳ�Ǿ�̬��Ա����
    {
        int year=MyDate.getThisYear();                     //���þ�̬������û�д���ʵ��ʱ
        System.out.println("������"+year+"�����ꣿ"+MyDate.isLeapYear(year));
        MyDate d1 = new MyDate(year-1,12,31);              //���ù��췽��
        System.out.println(d1.getYear()+"�꣬���ꣿ"+d1.isLeapYear());//����ʵ����Ա����
        MyDate d2 = new MyDate(d1);                    //���ÿ������췽������ʵ��
        System.out.println("d1��"+d1+"��d2��"+d2+"��d1==d2��"+(d1==d2)+
                "��d1.equals(d2)��"+d1.equals(d2));        //�����ϵ����==��Ƚ���ȷ���
        System.out.print(d1+"�������� ");
        d1.tomorrow();
        System.out.println(d1+"\n"+d1+"�������� "+(d2=d1.yestoday()));
    }
	public int compareTo(MyDate birthdate) {
	
		return 0;
	}
}
    