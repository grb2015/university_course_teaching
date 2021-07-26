package 实验题;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class StarJFrame extends JFrame implements ActionListener,ChangeListener
{
private JButton button;
private JCheckBox[] checkbox;
private Color color;
private JSpinner spin;
private Canvas canvas;
public StarJFrame()
{
	super("星形线");
	Dimension dim=this.getToolkit().getScreenSize();
	this.setBounds(dim.width/4,dim.height/4,dim.width/2,dim.height/2);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	JPanel cmdpanel=new JPanel();
	this.getContentPane().add(cmdpanel,"North");
	cmdpanel.add(this.button=new JButton("选择颜色"));
	this.button.addActionListener(this);
	String[] str= {"加深","渐变色","演示动画过程"};
	this.checkbox=new JCheckBox[str.length];
	for(int i=0;i<str.length;i++)
	{
		cmdpanel.add(this.checkbox[i]=new JCheckBox(str[i]));
		this.checkbox[i].addActionListener(this);
	}
	cmdpanel.add(new JLabel("左移"),3);
	this.spin=new JSpinner(new SpinnerNumberModel(4,1,8,1));
	this.spin.addChangeListener(this);
	cmdpanel.add(this.spin,4);
	cmdpanel.add(new JLabel("位"),5);
	this.color=Color.red;
	this.canvas=new StarCanvas();
	this.getContentPane().add(this.canvas,"Center");
	this.setVisible(true);
}
public void actionPerformed(ActionEvent event) {
	if(event.getSource()==this.button)
	{
		Color color=JColorChooser.showDialog(this,"选择颜色",this.color);
		if(color!=null)
		{
			this.color=color;
			this.canvas.repaint();
		}
	}
	else if(event.getSource() instanceof JCheckBox)
		this.canvas.repaint();
	}
public void stateChanged(ChangeEvent event)
{
	this.checkbox[1].setSelected(true);
	this.canvas.repaint();
}
private class StarCanvas extends Canvas
{
	public void paint(Graphics g)
	{
		if(StarJFrame.this.checkbox[0].isSelected())
			g.setColor(StarJFrame.this.color.darker());
		else
			g.setColor(StarJFrame.this.color);
		int x0=this.getWidth()/2;
		int y0=this.getHeight()/2;
		g.drawLine(0,y0,x0*2,y0);
		g.drawLine(x0,0,x0,y0*2);
		g.drawString("y", 405, 10);
		g.drawString("x", 760, 210);
		int n=(Integer)StarJFrame.this.spin.getValue();
		for(int length=40;length<200;length+=20)
		{
			for(int i=1;i<1024;i++)
			{
				double angle=i*Math.PI/512;
				int x=(int)(length*Math.pow(Math.cos(angle),3));
				int y=(int)(length*Math.pow(Math.sin(angle),3));
				g.fillOval(x0+x,y0+y,2,2);
			}
			if(StarJFrame.this.checkbox[1].isSelected())
				g.setColor(new Color(g.getColor().getRGB()<<n));
		}
	}
}
public static void main(String arg[])
{
	new StarJFrame();
}
}