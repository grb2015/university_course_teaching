package 课设1;


public class Person implements java.io.Serializable
{
    public String name;                                   
    public MyDate birthdate;                              
    public String gender, province, city;                 
    private static int count=0;                         
    
    public Person(String name, MyDate birthdate, String gender, String province, String city) 
    {
        super();                                           
        this.set(name, birthdate, gender, province, city); 
        count++;                                           
    } 
    public Person(String name, MyDate birthdate)        
    {
        this(name, birthdate, "", "", "");               
    } 
    public Person()                                       
    {
        this("", new MyDate());

    }
    public Person(Person per)                              
    {
        
        this(per.name, new MyDate(per.birthdate), per.gender, per.province, per.city);
    }

    public void finalize()                                
    {
        System.out.println("释放对象 ("+this.toString()+")");
        Person.count--;
    }

  
    public static void howMany()
    {
        System.out.print(Person.count+"个Person对象，");
    }
    

    public void set(String name, MyDate birthdate, String gender, String province, String city)
    {
        this.name = name==null?"":name;          
        this.birthdate = birthdate;             

        this.gender = gender==null?"":gender;
        this.province = province==null?"":province;
        this.city = city==null?"":city;
    }
    public void set(String name, MyDate birthdate)
    {
        this.set(name, birthdate, "", "", "");
    }
    
    public String toString()                   
    {
        return this.name+","+(this.birthdate==null?"":birthdate.toString())+","+
               this.gender+","+this.province+","+this.city;  
    }
    public static void main(String[] args)
    {
        Person p1 = new Person("李小明", new MyDate(1994,3,15));

                
        Person p2 = new Person(p1);                     

        Person.howMany();                                
        System.out.println("p1："+p1+"；p2："+p2+"\np1==p2？"+(p1==p2)+
            "；p1.name==p2.name？"+(p1.name==p2.name)+"；p1.birthdate==p2.birthdate？"+
            (p1.birthdate==p2.birthdate));               

      
        p2.name = "张"+p2.name.substring(1);                
        MyDate date = p2.birthdate;                         
       
        date.set(date.getYear()+2, date.getMonth(), date.getDay());
                                     
        System.out.println("p1："+p1+"；p2："+p2);
        p1.finalize();                                    
        Person.howMany();
    }
}