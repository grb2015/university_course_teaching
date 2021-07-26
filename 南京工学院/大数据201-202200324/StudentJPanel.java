package 课设1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class StudentJPanel extends JPanel implements ActionListener
{
    private JTextField text_name, text_date,text_phonenumber,text_relationship;               
    private JRadioButton[] radios;
    public JComboBox<String> combox_province, combox_city; 
    private static String[] provinces={"江苏", "浙江","湖南","湖北","广西","广东"};   
    private static String[][] cities={{"南京","苏州","无锡"},{"杭州","宁波","温州"},
                                      {"长沙"},{"武汉"},{"南宁"},{"广州"}};
    
  
    public StudentJPanel()                                  
    {
        this.setBorder(new TitledBorder("Student"));                   
        this.setLayout(new GridLayout(9,1));               
        
        this.add(this.text_name = new JTextField("姓名"));
        this.add(this.text_date = new JTextField("2000年1月1日"));
        this.add(this.text_phonenumber = new JTextField("电话号码"));
        this.add(this.text_relationship = new JTextField("关系"));
        		
        String[] str={"男","女"};
        JPanel rbpanel=new JPanel(new GridLayout(1,2));   

        this.add(rbpanel);
        ButtonGroup bgroup = new ButtonGroup();         
        this.radios = new JRadioButton[str.length];
        for(int i=0; i<this.radios.length; i++)
        {
            rbpanel.add(this.radios[i]=new JRadioButton(str[i])); 
            bgroup.add(this.radios[i]);                  
        }        
        this.radios[0].setSelected(true);                
        this.add(this.combox_province = new JComboBox<String>(StudentJPanel.provinces));
        this.add(this.combox_city = new JComboBox<String>(StudentJPanel.cities[0]));
        this.combox_province.addActionListener(this); 
       
    
  
       
    }

    public void set(Friend stu)                 
    {

        if(stu==null)
            return;
        this.text_name.setText(stu.name);
        this.text_date.setText(stu.birthdate.toString());
        this.text_phonenumber.setText(stu.phonenumber);
        this.text_relationship.setText(stu.relationship);
        if(stu.gender.equals("男"))
            this.radios[0].setSelected(true);
        else
            this.radios[1].setSelected(true);
        this.combox_province.setSelectedItem(stu.province);  
        this.combox_city.setSelectedItem(stu.city);
        
    }
        public Friend get()              
    {
       
        String gender = radios[0].isSelected() ? radios[0].getText() : radios[1].getText();
        try
        {
            
            MyDate birthdate = new MyDate(this.text_date.getText());
            return new Friend(text_name.getText(), birthdate, gender,
                       (String)combox_province.getSelectedItem(), (String)combox_city.getSelectedItem(),text_phonenumber.getText(),text_relationship.getText());
        }
        catch(NumberFormatException ex)        
        {
            JOptionPane.showMessageDialog(this, ex.getMessage()+" 字符串不能转换成整数。");
        }
        catch(DateFormatException ex)           
        {
             JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        return null;
    }    
    public void actionPerformed(ActionEvent event)  {
        
        int i=this.combox_province.getSelectedIndex();    
        if(cities!=null && i!=-1)
        {
            this.combox_city.removeAllItems();            
            for(int j=0; j<StudentJPanel.cities[i].length; j++)
                this.combox_city.addItem(StudentJPanel.cities[i][j]);
            

        }
    }
}