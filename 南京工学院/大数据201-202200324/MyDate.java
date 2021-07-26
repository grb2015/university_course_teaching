package 课设1;

public class MyDate implements java.io.Serializable
{ 
    private int year,month,day;                  //年月日，私有成员变量
    private static int thisYear;            //当前年份，私有静态成员变量
    static                                       //静态成员变量初始化
    {
        thisYear=2018;
//        thisYear = java.util.Calendar.getInstance().get(Calendar.YEAR); //获得当前日期对象中的年份值，4.3.2节
//        thisYear = new java.util.GregorianCalendar().get(Calendar.YEAR); //获得当前日期对象中的年份值，4.3.2节
    } 
    
    public MyDate(int year, int month, int day) throws DateFormatException
    {
                                        
    	this.set(year, month, day);                        //例3.2，调用本类的成员方法。//例5.2，该方法声明抛出日期格式异常
    } 
   public MyDate() {
	   this(1970,1,1);
   }
	
	public MyDate(String datestr)throws NumberFormatException,DateFormatException                                        //无参数构造方法，指定缺省日期，重载
    {
       if(datestr.isEmpty())
    	   throw new DateFormatException("空串，日期错误");
     int i=datestr.indexOf('年'),j=datestr.indexOf('月',i),k=datestr.indexOf('日',j);
     int year=Integer.parseInt(datestr.substring(0,i));
     int month=Integer.parseInt(datestr.substring(i+1,j));
     int day=Integer.parseInt(datestr.substring(j+1,k));
     this.set(year,month,day);
    } 
  
    

    //拷贝构造方法，日期同参数，重载。
    public MyDate(MyDate date) 
    {
        this.set(date);
    }
  
  

public void set(int year, int month, int day) throws DateFormatException       //设置日期值，算法不全，改进见5.2.3节
    {
	   if(year<=-2000||year>2500)
		   throw new DateFormatException(year+",年份不合适，有效年份为-2000~2500.");
	   if(month<1||month>12)
		   throw new DateFormatException(month+"月,日份错误");
	   if(day<1||day>MyDate.daysOfMonth(year, month))
			throw new DateFormatException(year+"年"+month+"月"+day+"日，日期错误");
	   
	  
        this.year = year;                                  //this.year指当前对象的成员变量，year指参数
        this.month= month; //this引用不能省略
        this.day = day;
    } 
  
    public void set(MyDate date)                           //设置日期值，重载
    {
        this.set(date.year, date.month, date.day);         //调用同名成员方法，不能使用this()
    }
  
	public class DateFormatException extends IllegalArgumentException{
		public DateFormatException(String message)
		{super(message);
		}
		
		
	}



	//5.2.3节，例5.2 此方法不需要抛出日期格式异常//throws DateFormatException
    //因为，date参数是日期，没有错误。

    public int getYear()                                   //获得年份
    {
        return this.year;
    }
    public int getMonth()                                  //获得月份
    {
        return this.month;
    }
    public int getDay()                                    //获得当月日期
    {
        return this.day;
    }
  
    public String toString()                               //中文日期格式字符串，2位月日
    {
        return this.year+"年"+String.format("%02d", this.month)+"月"+
               String.format("%02d", this.day)+"日";//+this.toWeekString();
    }

    public static int getThisYear()                        //获得今年年份，静态方法
    {
        return thisYear;                                   //访问静态成员变量
    }

    public static boolean isLeapYear(int year)             //判断指定年份是否闰年，静态方法
    {
        return year%400==0 || year%100!=0 && year%4==0;
    }
    public boolean isLeapYear()                            //判断当前日期的年份是否闰年，重载
    {
        return isLeapYear(this.year);                      //调用静态方法
    }        

    //比较由this和date参数引用的实例值是否相等。
    //若this与date引用同一个实例，则相等；若它们分别引用两个
    //实例，则分别比较它们的各成员变量值是否对应相等
    public boolean equals(MyDate date)
    {
//        System.out.println(b+"，执行equals(MyDate date)方法");
        //this指代调用当前方法的对象，this.year等访问当前对象的成员变量（此3处this可省略）
        return this==date ||           //若this与date引用同一个实例，则相等
               date!=null && this.year==date.year && this.month==date.month && this.day==date.day;
               //若this与date分别引用两个实例，则分别比较它们的各成员变量值是否对应相等
    } 
    
    public static int daysOfMonth(int year, int month)     //返回指定年月的天数，静态方法
    {
        switch(month)                           //计算每月的天数
        {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:  return 31; 
            case 4: case 6: case 9: case 11:  return 30;
            case 2:  return MyDate.isLeapYear(year) ? 29 : 28;
            default: return 0;
        }
    }
    public int daysOfMonth()                     //返回当月天数
    {
        return daysOfMonth(this.year, this.month);
    } 

  
    public void tomorrow()                       //将this引用实例的日期改为之后一天日期，没有返回值
    {
        this.day = this.day%this.daysOfMonth()+1;
        if(this.day==1)
        {
            this.month = this.month%12+1;        //下月
            if(this.month==1)                   //12月的下月是明年1月
                this.year++;
        }
    }    
    public MyDate yestoday()                     //返回当前日期的前一天日期
    {
        MyDate date = new MyDate(this);          //执行拷贝构造方法，创建实例，没有改变this
        date.day--;
        if(date.day==0)
        {
        	date.month = (date.month-2+12)%12+1; //上月
            if(date.month==12)                   //1月的上月是去年12月
            	date.year--;
            date.day = daysOfMonth(date.year, date.month);
        }
        return date;                             //返回对象date引用的实例
    }  

   
    public static void main(String[] args)throws NumberFormatException,DateFormatException             //main方法也是静态成员方法
    {
        int year=MyDate.getThisYear();                     //调用静态方法，没有创建实例时
        System.out.println("今年是"+year+"，闰年？"+MyDate.isLeapYear(year));
        MyDate d1 = new MyDate(year-1,12,31);              //调用构造方法
        System.out.println(d1.getYear()+"年，闰年？"+d1.isLeapYear());//调用实例成员方法
        MyDate d2 = new MyDate(d1);                    //调用拷贝构造方法复制实例
        System.out.println("d1："+d1+"，d2："+d2+"，d1==d2？"+(d1==d2)+
                "，d1.equals(d2)？"+d1.equals(d2));        //区别关系运算==与比较相等方法
        System.out.print(d1+"的明天是 ");
        d1.tomorrow();
        System.out.println(d1+"\n"+d1+"的昨天是 "+(d2=d1.yestoday()));
    }
	public int compareTo(MyDate birthdate) {
	
		return 0;
	}
}
    