package 课设1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.util.*;                                        //第12章集合框架


import java.util.ArrayList;

import java.util.List;
//Person对象信息管理框架类，继承框架类，响应动作事件、列表框选择事件
public class PhoneJFrame extends JFrame 
    implements ActionListener, ListSelectionListener,Runnable
{
	protected JList jlist1,jlist2; // 列表框
	protected DefaultListModel listmodel,listmodel1; // 列表框模型
	protected JTable jtable;
	protected String filename;
	protected Friend[] stu;
; // 表格模型
	protected StudentJPanel student; 
	protected JPanel uppanel, downpanel, leftpanel, rightpanel;
	protected JPanel cmdpanel; // 命令面板
	protected JComboBox<String>[] comboxs;            

   public static Comparator[] comparators={new CompareName(), new CompareBirthdate(),new CompareGender(),new CompareProvince(),new CompareCity()};
    public Font font = new Font("宋体",1,28);    
 
    public PhoneJFrame(Friend[] stu, StudentJPanel student,String filename)//【思考题6-5】person引用子类实例
    {
        super("Phone对象信息管理");
//        this.setSize(700,240);                             //设置组件尺寸，截图，没改字号
        this.setSize(800,668);                             //设置组件尺寸
        this.setLocationRelativeTo(null);                  //将窗口置于屏幕中央
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);      //单击窗口关闭按钮，结束程序运行
        
        this.stu=stu;
        this.filename=filename;//局部变量等于成员变量
        
   
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//水平分割窗格
        this.listmodel = new DefaultListModel();
		if (stu != null) {
			this.listmodel.addElement(stu[0].name.charAt(0));
			for (int j = 1, k = 0; j < stu.length; j++) {
				boolean n = true;
				for (int x = 0; x < j - k; x++) {
					if (stu[j].name.charAt(0) == (char) listmodel.getElementAt(x)) {
						n = false;
						k++;
						break;
					}
				}
				if (n)
					this.listmodel.addElement(stu[j].name.charAt(0)); // 列表框模型添加数据项

			}
			this.listmodel.insertElementAt("全部", 0); // 将“全部”插入第一项
		}
		// 创建列表框，指定列表框模型
		this.jlist1 = new JList(this.listmodel);
		this.jlist1.setFont(font); // 设置列表框组件的字体
		this.jlist1.setBorder(new TitledBorder("Phone对象姓氏列表框"));
		this.jlist1.addListSelectionListener(this); // 列表框监听选择事件

		this.leftpanel = new JPanel();
		this.rightpanel = new JPanel();

		JSplitPane split_jlist = new JSplitPane(1, leftpanel, rightpanel);
		JSplitPane split_jtable = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 垂直分割窗格
		this.getContentPane().add(split_jlist);
		split_jlist.setDividerLocation(180);// 设置水平分隔条的位置
		split_jlist.setOneTouchExpandable(true);// 提供一键展开按钮

		this.leftpanel.add(jlist1);
		this.rightpanel.add(split_jtable);

		split_jtable.add(this.uppanel = new JPanel(new GridLayout(1, 1)));
		split_jtable.add(this.downpanel = new JPanel(new BorderLayout()));

		split_jtable.setDividerLocation(200); // 设置水平分隔条的位置
		split_jtable.setOneTouchExpandable(true); // 提供一键展开按钮

		this.listmodel1=new DefaultListModel();
//		this.listmodel2.setSize(8);
		for(int i=0;i<stu.length;i++) {
			this.listmodel1.addElement(i);
			this.listmodel1.addElement(stu[i]);

		}
		
		this.jlist2=new JList(listmodel1);
		this.uppanel.add(new JScrollPane(jlist2));
		
		this.student = student;
		this.downpanel.add(this.student);
	//	MyJPanel.setFont(this.person, font); // 设置面板中所有组件的字体

		downpanel.add(this.cmdpanel = new JPanel(), BorderLayout.SOUTH); // 添加命令面板

		// 以下在命令面板上添加按钮和查找、排序组合框
		String[][] str = { { "添加", "删除选中项",  "打开", "保存" }, {  "排序关键字" },
				{ "姓名", "出生日期", "性别", "电话号码", "关系" } };
		for (int i = 0; i < str[0].length; i++)
		{
			JButton button = new JButton(str[0][i]);
			button.addActionListener(this);
			cmdpanel.add(button);
		}
		this.comboxs = new JComboBox[str[1].length]; // <String>不行
		for (int i = 0; i < str[1].length; i++) 
		{
			cmdpanel.add(new JLabel(str[1][i]));
			cmdpanel.add(this.comboxs[i] = new JComboBox<String>(str[2]));
			this.comboxs[i].addActionListener(this); // 组合框监听动作事件
		}
		this.setVisible(true);
																

	}

  
    public void valueChanged(ListSelectionEvent event)
    {
		String str = "全部";
		char name = ' '; // 记录姓氏
		if (str.equals(this.jlist1.getSelectedValue())) {
			this.listmodel1.removeAllElements(); // 若点击全部,清空表格
			for (int i = 0; i < stu.length; i++) { 
				this.listmodel1.addElement(this.stu[i]);
			}
			return;
		}
		for (int i = 0; i < this.listmodel.getSize(); i++) { // 遍历列表框,找到点击的姓氏
			if ((char) this.jlist1.getSelectedValue() ==this.listmodel1.getElementAt(i).toString().charAt(0)) {
				name = (char) this.jlist1.getSelectedValue();
				break;
			}
			
		}
		this.listmodel1.removeAllElements();// 清空表格
		for (int i = 0; i < stu.length; i++) { 
			if (name == stu[i].name.charAt(0))
				this.listmodel1.addElement(stu[i]);
		}
    }
    
    public void actionPerformed(ActionEvent event)            //动作事件处理方法，单击按钮，选择组合框
    {    
    	boolean n=true;

        if(event.getSource() instanceof JButton)           //单击了按钮
        {
            Friend stu=null;
            switch(event.getActionCommand())     //JDK 8，switch条件表达式可以是String
            {
                case "添加":   
                	if((stu=this.student.get())!=null)//单击“添加”按钮
                	{
                		this.listmodel1.addElement(stu); 
                		this.stu = Arrays.copyOf(this.stu, this.stu.length + 1); 
    					this.stu[this.stu.length - 1] = stu; // 将新加的数据加入数组

                	for (int x = 1; x < this.listmodel.getSize(); x++) { // 遍历列表框，若新加入的姓氏不存在，则添加新的姓氏
						if (stu.name.charAt(0) == (char) listmodel.getElementAt(x)) {
							n = false;
							break;
						}
					}
					if (n)
						this.listmodel.addElement(stu.name.charAt(0)); // 列表框模型添加数据项

                	}
					break;
                	
                    
              
                case "删除选中项":
                    this.removeSelected(this.jlist2, this.listmodel1);
                    break;
                    
            
                case "打开":readFrom(filename,listmodel1);break;
                case "保存":writeTo(filename,listmodel1);break;
            }
        }
		
             if(event.getSource()==this.comboxs[0])    
            {
                int i=this.comboxs[0].getSelectedIndex(); 

            }
        
    }
    
    //在listmodel列表框模型中删除与obj相等的数据项，通用方法
      
    //将jlist列表框选中项行数据在listmodel列表框模型中删除，通用方法
    public <T> void removeSelected(JList jlist, DefaultListModel<T> listmodel)
    {
        if(this.listmodel1.getSize()==0)               //返回列表框数据项数
            JOptionPane.showMessageDialog(this, "列表框为空，不能删除");
        else
        {   
   	        int i=jlist.getSelectedIndex();       //列表框选中数据项序号
            if(i==-1)
                JOptionPane.showMessageDialog(this, "请选中列表框数据项");
            else
            {
            	boolean x=false;
                String str=jlist.getSelectedValue().toString(); //列表框选中数据项字符串
                if(JOptionPane.showConfirmDialog(this, "删除 第"+i+"项("+str+")？","确认", 
                    JOptionPane.YES_NO_OPTION)==0)     //确认对话框，单击“是”按钮，则返回0

                    for(int p=0;p<this.listmodel1.getSize();p++) {
                    	if(listmodel.getElementAt(i)!=this.listmodel1.getElementAt(p))
                    		x=true;
                    }
                	listmodel.removeElementAt(i); //删除列表框第i数据项

                    if(x)
                        this.listmodel.removeElement(stu[i].name.charAt(0));
         
            }


        }
    }
  
  
    
    
    public <T> void sort(DefaultListModel<? super T> listmodel, Comparator<? super T> c)
    {
         for(int i=0; i<listmodel1.getSize()-1; i++)    //直接选择排序算法
        {
            int min=i; 
            for(int j=i+1; j<listmodel1.getSize(); j++)
                if(c.compare((T)listmodel1.getElementAt(j), (T)listmodel1.getElementAt(min))<0)
                    min = j; 
            if(min!=i) 
            {   
                T temp = (T)listmodel1.getElementAt(i);
                listmodel1.setElementAt((T)listmodel1.getElementAt(min), i);
                listmodel1.setElementAt(temp, min);
            }
       
       }
        
       
    }    
    
  
    public void writeTo(String filename,ListModel listmodel) {
    	try {
    		OutputStream out=new FileOutputStream(filename);
    		ObjectOutputStream objout=new ObjectOutputStream(out);
    		for(int d=0;d<this.listmodel1.getSize();d++) 
    			objout.writeObject(this.listmodel1.getElementAt(d));
    		objout.close();
    		out.close();
    	}
    	catch(FileNotFoundException ex) {}
    	catch(IOException ex) {}	
    }
   
    public void readFrom(String filename,DefaultListModel listmodel) {
    	new Thread(this).start();
    }
    
    public void run() {
    	try {
    		InputStream in=new FileInputStream(filename);
    		ObjectInputStream objin=new ObjectInputStream(in);
    		listmodel1.removeAllElements();//删除所有数据项
    		while(true) {
    			try{
    				listmodel1.addElement(objin.readObject());//列表框模型添加读取的对象
    				Thread.sleep(100);
    			}
    			catch(EOFException ex) {break;}
    			catch(InterruptedException ex) {}
    		}
    		objin.close();
    		in.close();
    	}
    	catch(FileNotFoundException ex) {System.out.println(1);}
    	catch(ClassNotFoundException ex) {System.out.println(2);}
    	catch(IOException ex) {System.out.println(3);}
    }
    
    public Object[] toArray(Friend stu) {
		Object[] obj = new Object[Friend.class.getFields().length];
		obj[0] = stu.name;
		obj[1] = stu.birthdate;
		obj[2] = stu.gender;
		obj[3] = stu.province;
		obj[4] = stu.city;
		obj[5]= stu.phonenumber;
		obj[6]= stu.relationship;
	
		return obj;
    } 
    
    public static void main(String arg[])
    {
    	
    	
        Friend[] stu={new Friend("李小明",new MyDate(1994,3,15),"男","江苏","南京","123456789","同学"),
                      new Friend("成桅", new MyDate(2002,11,12),"男","湖南","长沙","183468465","同学"),
                      new Friend("凌彤", new MyDate(1994,3,15),"女","浙江","杭州","1576456466","同学"),
                      new Friend("刘乐",new MyDate(2002,06,18),"男","湖南","长沙","13787142069","同学")};
        new PhoneJFrame(stu,new StudentJPanel(),"stu.obj");
    }
}