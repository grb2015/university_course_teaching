package 课设1;



public class Friend extends Person implements java.io.Serializable
{
    public String phonenumber,relationship;  //系、专业、学号
                

    private static int count=0;                  //Student类对象计数，私有、静态，隐藏
    
    public Friend(String name, MyDate birthdate, String gender, String province, String city, String phonenumber, String relationship) //构造方法
    {
    	//"Li李小明",new MyDate(1994,3,15),"男","江苏","南京","计算机","计算机科学","001",true
        super(name, birthdate, gender, province, city);
            
        this.set(phonenumber, relationship);
        count++;                                 
    } 
    public Friend()
    {
        super();                                
        this.set("","");               
        Friend.count++;
    }       
   
    public Friend(Person person, String phonenumber, String relationship)
    {
        super(person);                           
        this.set(phonenumber,relationship);
        Friend.count++;
    }
    public Friend(Friend stu)               
    {
        this(stu, stu.phonenumber, stu.relationship); 
    }

   
 
    
	public void finalize()                       //析构方法，覆盖父类的析构方法，不能抛出异常
    {
        super.finalize();                        //调用父类析构方法，Person.count--；此时无Person.count访问权限
//        Person.count--;                        //编译错，The field Person.count is not visible
        Friend.count--;
    }

    public static void howMany()                 //显示父类和子类的对象数，覆盖父类静态方法
    {
        Person.howMany();                        //调用父类的静态成员方法
//        super.howMany();                       //编译错，静态方法中不能使用super
        System.out.println(Friend.count+"个Student对象");
    }

   
    public void set(String phonenumber, String relationship)
    {
        this.phonenumber = phonenumber==null?"":phonenumber;
        this.relationship = relationship==null?"":relationship;
    }    

    public String toString()                     //描述对象字符串，覆盖父类方法
    {
        return super.toString()+","+this.phonenumber+","+this.relationship;
             
    }
    public static void main(String[] args)
    {
    //    Friend.howMany();                                 //执行子类静态成员方法
    //    Person per = new Person("李小明",new MyDate(1994,3,15),"男","湖南省","长沙市");//per对象引用本类实例
   //     Friend stu1 = new Friend(per,"计算机系","计算机科学与技术专业","211994001",true); //由Person实例提供初值
   //     Friend stu2 = new Friend(stu1);                      //拷贝构造方法
    //    stu2.set("张莉",new MyDate(1998,4,5),"女","湖北省","武汉市");//调用父类的成员方法
 //       stu2.set("经济管理系","信息管理与信息系统专业","321998003",true); //调用子类重载的成员方法
//        Friend.howMany();                                 //调用子类静态成员方法
 //       System.out.println("per："+per.toString()+"\nstu1："+stu1.toString()+"\nstu2："+stu2);//编译时多态性        
  //      stu2.finalize();                                     //调用子类的析构方法
  //      Friend.howMany();                                 //执行子类静态成员方法
    }
	public static Object[] toArray(Friend stu) {
		Object[] obj = new Object[Friend.class.getFields().length];
		obj[0] = stu.name;
		obj[1] = stu.birthdate;
		obj[2] = stu.gender;
		obj[3] = stu.province;
		obj[4] = stu.city;
		obj[5]=stu.phonenumber;
		obj[6]=stu.relationship;
		
		
		
		return obj;
	}

    //3.4.4   多态的方法实现
    //比较this与obj对象是否相等，覆盖，先调用父类方法，再比较本类各成员变量是否相等
    //与父类实例不可比，s1.equals(p1)不比成员变量，引用==时，不继续比
    

}


