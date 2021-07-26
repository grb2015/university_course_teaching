package ����1;



public class Friend extends Person implements java.io.Serializable
{
    public String phonenumber,relationship;  //ϵ��רҵ��ѧ��
                

    private static int count=0;                  //Student����������˽�С���̬������
    
    public Friend(String name, MyDate birthdate, String gender, String province, String city, String phonenumber, String relationship) //���췽��
    {
    	//"Li��С��",new MyDate(1994,3,15),"��","����","�Ͼ�","�����","�������ѧ","001",true
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

   
 
    
	public void finalize()                       //�������������Ǹ�������������������׳��쳣
    {
        super.finalize();                        //���ø�������������Person.count--����ʱ��Person.count����Ȩ��
//        Person.count--;                        //�����The field Person.count is not visible
        Friend.count--;
    }

    public static void howMany()                 //��ʾ���������Ķ����������Ǹ��ྲ̬����
    {
        Person.howMany();                        //���ø���ľ�̬��Ա����
//        super.howMany();                       //�������̬�����в���ʹ��super
        System.out.println(Friend.count+"��Student����");
    }

   
    public void set(String phonenumber, String relationship)
    {
        this.phonenumber = phonenumber==null?"":phonenumber;
        this.relationship = relationship==null?"":relationship;
    }    

    public String toString()                     //���������ַ��������Ǹ��෽��
    {
        return super.toString()+","+this.phonenumber+","+this.relationship;
             
    }
    public static void main(String[] args)
    {
    //    Friend.howMany();                                 //ִ�����ྲ̬��Ա����
    //    Person per = new Person("��С��",new MyDate(1994,3,15),"��","����ʡ","��ɳ��");//per�������ñ���ʵ��
   //     Friend stu1 = new Friend(per,"�����ϵ","�������ѧ�뼼��רҵ","211994001",true); //��Personʵ���ṩ��ֵ
   //     Friend stu2 = new Friend(stu1);                      //�������췽��
    //    stu2.set("����",new MyDate(1998,4,5),"Ů","����ʡ","�人��");//���ø���ĳ�Ա����
 //       stu2.set("���ù���ϵ","��Ϣ��������Ϣϵͳרҵ","321998003",true); //�����������صĳ�Ա����
//        Friend.howMany();                                 //�������ྲ̬��Ա����
 //       System.out.println("per��"+per.toString()+"\nstu1��"+stu1.toString()+"\nstu2��"+stu2);//����ʱ��̬��        
  //      stu2.finalize();                                     //�����������������
  //      Friend.howMany();                                 //ִ�����ྲ̬��Ա����
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

    //3.4.4   ��̬�ķ���ʵ��
    //�Ƚ�this��obj�����Ƿ���ȣ����ǣ��ȵ��ø��෽�����ٱȽϱ������Ա�����Ƿ����
    //�븸��ʵ�����ɱȣ�s1.equals(p1)���ȳ�Ա����������==ʱ����������
    

}


