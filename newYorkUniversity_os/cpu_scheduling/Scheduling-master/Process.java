
public class Process implements Comparable <Process>{
	private int A , B , C , IO ,initialC;
	private int state = 0;
	// 0 for "unstarted", 1 for "ready", 2 for "running", 3 for "blocked", 4 for "finished"
	private int ft, iot, wt;
	
	public Process(int a, int b, int c, int io){
		this.A = a;
		this.B = b;		// B我觉得可以理解为最大的时间片,因为是分时系统。即CPU每次能分配的最大时间
		this.C = c;
		this.IO = io;
		this.initialC = c;
 	}
	
	public int getA(){
		return this.A;
	}
	
	public int getB(){
		return this.B;
	}
	
	public int getC(){
		return this.C;
	}
	
	public int getIO(){
		return this.IO;
	}
	/*
		state   1: 运行时间
				2：阻塞时间
	// 0 for "unstarted", 1 for "ready", 2 for "running", 3 for "blocked", 4 for "finished"
				
	 */
	public void setState(int i){
		this.state = i;
	}
	
	public int getState(){
		return this.state;
	}
	
	public void decreaseC(){
		this.C -= 1;
	}
	// finish time 
	public void setft(int t){
		this.ft = t;
	}
	
	public int getft(){
		return this.ft;
	}
	// IO time 
	public void incIOT(){
		this.iot++;
	}
	
	public int getIOT(){
		return this.iot;
	}
	// wait time 
	public void incWT(){
		this.wt++;
	}
	
	public int getWT(){
		return this.wt;
	}
	
	public int getintialC(){
		return this.initialC;
	}
	
	public int compareTo(Process p) {
		if (p.getA() > this.A){
			return -1;
		}else if(this.A > p.getA()){
			return 1;
		}else if(p.getB() > this.B){
			return -1;
		}else if(this.B > p.getB()){
			return 1;
		}else if(p.getC() > this.C){
			return -1;
		}else if(this.C > p.getC()){
			return 1;
		}else if(p.getIO() > this.IO){
			return -1;
		}else if(this.IO > p.getIO()){
			return 1;
		}
		return 0;
	}
	
}
