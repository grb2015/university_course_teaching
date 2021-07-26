package ����1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.util.*;                                        //��12�¼��Ͽ��


import java.util.ArrayList;

import java.util.List;
//Person������Ϣ�������࣬�̳п���࣬��Ӧ�����¼����б��ѡ���¼�
public class PhoneJFrame extends JFrame 
    implements ActionListener, ListSelectionListener,Runnable
{
	protected JList jlist1,jlist2; // �б��
	protected DefaultListModel listmodel,listmodel1; // �б��ģ��
	protected JTable jtable;
	protected String filename;
	protected Friend[] stu;
; // ���ģ��
	protected StudentJPanel student; 
	protected JPanel uppanel, downpanel, leftpanel, rightpanel;
	protected JPanel cmdpanel; // �������
	protected JComboBox<String>[] comboxs;            

   public static Comparator[] comparators={new CompareName(), new CompareBirthdate(),new CompareGender(),new CompareProvince(),new CompareCity()};
    public Font font = new Font("����",1,28);    
 
    public PhoneJFrame(Friend[] stu, StudentJPanel student,String filename)//��˼����6-5��person��������ʵ��
    {
        super("Phone������Ϣ����");
//        this.setSize(700,240);                             //��������ߴ磬��ͼ��û���ֺ�
        this.setSize(800,668);                             //��������ߴ�
        this.setLocationRelativeTo(null);                  //������������Ļ����
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);      //�������ڹرհ�ť��������������
        
        this.stu=stu;
        this.filename=filename;//�ֲ��������ڳ�Ա����
        
   
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//ˮƽ�ָ��
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
					this.listmodel.addElement(stu[j].name.charAt(0)); // �б��ģ�����������

			}
			this.listmodel.insertElementAt("ȫ��", 0); // ����ȫ���������һ��
		}
		// �����б��ָ���б��ģ��
		this.jlist1 = new JList(this.listmodel);
		this.jlist1.setFont(font); // �����б�����������
		this.jlist1.setBorder(new TitledBorder("Phone���������б��"));
		this.jlist1.addListSelectionListener(this); // �б�����ѡ���¼�

		this.leftpanel = new JPanel();
		this.rightpanel = new JPanel();

		JSplitPane split_jlist = new JSplitPane(1, leftpanel, rightpanel);
		JSplitPane split_jtable = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // ��ֱ�ָ��
		this.getContentPane().add(split_jlist);
		split_jlist.setDividerLocation(180);// ����ˮƽ�ָ�����λ��
		split_jlist.setOneTouchExpandable(true);// �ṩһ��չ����ť

		this.leftpanel.add(jlist1);
		this.rightpanel.add(split_jtable);

		split_jtable.add(this.uppanel = new JPanel(new GridLayout(1, 1)));
		split_jtable.add(this.downpanel = new JPanel(new BorderLayout()));

		split_jtable.setDividerLocation(200); // ����ˮƽ�ָ�����λ��
		split_jtable.setOneTouchExpandable(true); // �ṩһ��չ����ť

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
	//	MyJPanel.setFont(this.person, font); // ����������������������

		downpanel.add(this.cmdpanel = new JPanel(), BorderLayout.SOUTH); // ����������

		// �����������������Ӱ�ť�Ͳ��ҡ�������Ͽ�
		String[][] str = { { "���", "ɾ��ѡ����",  "��", "����" }, {  "����ؼ���" },
				{ "����", "��������", "�Ա�", "�绰����", "��ϵ" } };
		for (int i = 0; i < str[0].length; i++)
		{
			JButton button = new JButton(str[0][i]);
			button.addActionListener(this);
			cmdpanel.add(button);
		}
		this.comboxs = new JComboBox[str[1].length]; // <String>����
		for (int i = 0; i < str[1].length; i++) 
		{
			cmdpanel.add(new JLabel(str[1][i]));
			cmdpanel.add(this.comboxs[i] = new JComboBox<String>(str[2]));
			this.comboxs[i].addActionListener(this); // ��Ͽ���������¼�
		}
		this.setVisible(true);
																

	}

  
    public void valueChanged(ListSelectionEvent event)
    {
		String str = "ȫ��";
		char name = ' '; // ��¼����
		if (str.equals(this.jlist1.getSelectedValue())) {
			this.listmodel1.removeAllElements(); // �����ȫ��,��ձ��
			for (int i = 0; i < stu.length; i++) { 
				this.listmodel1.addElement(this.stu[i]);
			}
			return;
		}
		for (int i = 0; i < this.listmodel.getSize(); i++) { // �����б��,�ҵ����������
			if ((char) this.jlist1.getSelectedValue() ==this.listmodel1.getElementAt(i).toString().charAt(0)) {
				name = (char) this.jlist1.getSelectedValue();
				break;
			}
			
		}
		this.listmodel1.removeAllElements();// ��ձ��
		for (int i = 0; i < stu.length; i++) { 
			if (name == stu[i].name.charAt(0))
				this.listmodel1.addElement(stu[i]);
		}
    }
    
    public void actionPerformed(ActionEvent event)            //�����¼���������������ť��ѡ����Ͽ�
    {    
    	boolean n=true;

        if(event.getSource() instanceof JButton)           //�����˰�ť
        {
            Friend stu=null;
            switch(event.getActionCommand())     //JDK 8��switch�������ʽ������String
            {
                case "���":   
                	if((stu=this.student.get())!=null)//��������ӡ���ť
                	{
                		this.listmodel1.addElement(stu); 
                		this.stu = Arrays.copyOf(this.stu, this.stu.length + 1); 
    					this.stu[this.stu.length - 1] = stu; // ���¼ӵ����ݼ�������

                	for (int x = 1; x < this.listmodel.getSize(); x++) { // �����б�����¼�������ϲ����ڣ�������µ�����
						if (stu.name.charAt(0) == (char) listmodel.getElementAt(x)) {
							n = false;
							break;
						}
					}
					if (n)
						this.listmodel.addElement(stu.name.charAt(0)); // �б��ģ�����������

                	}
					break;
                	
                    
              
                case "ɾ��ѡ����":
                    this.removeSelected(this.jlist2, this.listmodel1);
                    break;
                    
            
                case "��":readFrom(filename,listmodel1);break;
                case "����":writeTo(filename,listmodel1);break;
            }
        }
		
             if(event.getSource()==this.comboxs[0])    
            {
                int i=this.comboxs[0].getSelectedIndex(); 

            }
        
    }
    
    //��listmodel�б��ģ����ɾ����obj��ȵ������ͨ�÷���
      
    //��jlist�б��ѡ������������listmodel�б��ģ����ɾ����ͨ�÷���
    public <T> void removeSelected(JList jlist, DefaultListModel<T> listmodel)
    {
        if(this.listmodel1.getSize()==0)               //�����б����������
            JOptionPane.showMessageDialog(this, "�б��Ϊ�գ�����ɾ��");
        else
        {   
   	        int i=jlist.getSelectedIndex();       //�б��ѡ�����������
            if(i==-1)
                JOptionPane.showMessageDialog(this, "��ѡ���б��������");
            else
            {
            	boolean x=false;
                String str=jlist.getSelectedValue().toString(); //�б��ѡ���������ַ���
                if(JOptionPane.showConfirmDialog(this, "ɾ�� ��"+i+"��("+str+")��","ȷ��", 
                    JOptionPane.YES_NO_OPTION)==0)     //ȷ�϶Ի��򣬵������ǡ���ť���򷵻�0

                    for(int p=0;p<this.listmodel1.getSize();p++) {
                    	if(listmodel.getElementAt(i)!=this.listmodel1.getElementAt(p))
                    		x=true;
                    }
                	listmodel.removeElementAt(i); //ɾ���б���i������

                    if(x)
                        this.listmodel.removeElement(stu[i].name.charAt(0));
         
            }


        }
    }
  
  
    
    
    public <T> void sort(DefaultListModel<? super T> listmodel, Comparator<? super T> c)
    {
         for(int i=0; i<listmodel1.getSize()-1; i++)    //ֱ��ѡ�������㷨
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
    		listmodel1.removeAllElements();//ɾ������������
    		while(true) {
    			try{
    				listmodel1.addElement(objin.readObject());//�б��ģ����Ӷ�ȡ�Ķ���
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
    	
    	
        Friend[] stu={new Friend("��С��",new MyDate(1994,3,15),"��","����","�Ͼ�","123456789","ͬѧ"),
                      new Friend("��Φ", new MyDate(2002,11,12),"��","����","��ɳ","183468465","ͬѧ"),
                      new Friend("��ͮ", new MyDate(1994,3,15),"Ů","�㽭","����","1576456466","ͬѧ"),
                      new Friend("����",new MyDate(2002,06,18),"��","����","��ɳ","13787142069","ͬѧ")};
        new PhoneJFrame(stu,new StudentJPanel(),"stu.obj");
    }
}