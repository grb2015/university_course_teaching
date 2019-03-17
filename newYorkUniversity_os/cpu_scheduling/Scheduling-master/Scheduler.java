import java.io.*;
import java.util.*;
// must use utf-8 i windows cmd . 
// javac -encoding UTF-8 Scheduler.java 
public class Scheduler {
	private static BufferedReader br;
	private ArrayList<Process> sList= new ArrayList<Process>();
	private static int[] blocktime;
	private static int[] cputime;
	private static boolean verbose = false;
	
	public static int getNextNum(BufferedReader br) throws IOException{
		int a = 0;
		char temp = 0;
		boolean check = true;
		while (check){
			temp = (char) br.read();
			if (Character.isDigit(temp)) check = false;
		}
		while (Character.isDigit(temp)){
			a = a * 10;
			a += temp - '0';
			temp = (char)br.read();
		}
		return a;
	}
	
	public int randomOS(int R){
		Random random = new Random();
		int num = random.nextInt(R) + 1;
		return num;
	}
	
	public void read(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		int size = br.read() - '0';
		int a,b,c,io;
		for (int i = 0; i < size; i++){
			a = getNextNum(br);
			b = getNextNum(br);
			c = getNextNum(br);
			io = getNextNum(br);
			sList.add(new Process(a,b,c,io));
		}
		System.out.print("the original input is: size = " + size + "\n");
		System.out.print("the original input is: sList = " + sList + "\n");
		for (Process k : sList){
			System.out.print("the original input is: k = " + k + "\n");
			System.out.print(k.getA() +" " + k.getB() + " " + k.getC() + " " + k.getIO() + "  \n");
		}
		System.out.print("\n");
		Collections.sort(sList);
		System.out.print("------the sorted input is: size = " +  size + " \n");
		for (Process k : sList){
			System.out.print(k.getA() +" " + k.getB() + " " + k.getC() + " " + k.getIO() + "  \n");
		}
		System.out.print("\n");
		System.out.println();	
	}
	
	public void FCFS() throws Exception{
		int cpuBT = 0;	// CPU before terminate  保存当前运行进程的剩余的cpu时间? 应该是该进程能使用cpu的时间
						// 即分配给它的时间片还剩多少
		int time = 0;	 // 存放当前时间
		int count = 0;	// count记录当前有几个进程完成了。
		double cpuutil = 0;  // CPU 使用时间
		double ioutil = 0;	// io使用时间
		ArrayList<Process> runTable = new ArrayList<Process>();  // runTable存放就绪队列?
		boolean check = true;
		ArrayList<Process> runList = new ArrayList<Process>();
		for(Process k : sList){
			runList.add(k);
		}
		System.out.print("---------------FCFS-----------------");

		System.out.print("runList"+runList+"\n");
		blocktime = new int[sList.size()];		// 阻塞时间
		for (int i = 0; i < runList.size(); i++){
			blocktime[i] = -1;
		}
		if (verbose){
			System.out.print("Before Cycle    time = " + time + ":");
			for (int i = 0; i < runList.size(); i++){
				System.out.print("unstarted 0" + "\t");	
			}
		}
		System.out.print("\n");
		while (check){
			System.out.print("------ while check ---- ");	
			for (Process k : runList){	// 遍历队列,查看是否有新进程创建了。(新创建的进程会直接加入到就绪队列)
				if (k.getA() == time){		// 如果进程的到达时间 = 当前时间  ,即有进程来了,则将进程放到就绪队列中
					runTable.add(k);
					k.setState(1);   
				}
			}
			System.out.print("time =  " + time + "\t" + "  runTable =  "+ runTable+"\n");	

			time ++;
			// 遍历阻塞队列,看是否有队列等待时间到了,到了则加入到就绪队列。没到什么事情都不做。
			// 下面也有遍历阻塞队列的地方。放在一起不是更好? Q3
			for (int i = 0; i < blocktime.length; i++){		
				if (blocktime[i] == 0){		// 如果阻塞时间为0了,那么也应该加入到就绪队列中
					runTable.add(runList.get(i));
					System.out.print(" add  =  " + i+"\t"+runList.get(i) + "  into runTable\n");	
					blocktime[i] = -1;		// 设置当前进程阻塞时间为-1 (非阻塞)
					runList.get(i).setState(1);	// 设置当前进程状态为就绪
				}
			}
			System.out.print("  runTable =  "+ runTable+"\n");	

			// 如果就绪队列不为空,则取第1个出来运行
			if (!runTable.isEmpty()){		// 如果就绪队列非空
				System.out.print(" ########### runTable in not Empty ! ");
				Process temp = runTable.get(0);		// 取的第一个元素
				// System.out.print(" temp   =  " + temp + "\n");
				if (temp.getState() == 1){		// 如果取的进程转台为1(就绪)
					// 为cpuBT赋值
					
					int temp1 = randomOS(temp.getB());		// 该次分配的cpu时间
					if (temp1 < temp.getC()) 
						cpuBT = temp1;
					else 
						cpuBT = temp.getC();
					temp.setState(2);		// 设置进程为运行中
					System.out.print(" temp.getB()   =  " + temp.getB() + "\n");
					System.out.print(" temp.getC()   =  " + temp.getC() + "\n");
					System.out.print(" temp1 ,randomOS(temp.getB()   =  " + temp1+ "\n");
					System.out.print(" cpuBT =  " + cpuBT + "\n");
				}
				temp.decreaseC();	// temp进程的C减1 
				cpuutil ++;			// cpuutil加1 ,cpuutil这个是总的cpu运行时间之和,而不是某个进程的时间

			}
			else{
					System.out.print(" *************** runTable in Empty ! ");
			}
			if (verbose){
				System.out.print("Before Cycle    " + time + ":");
				for (Process k: runList){
					if (k.getState() == 0){
						System.out.print("unstarted 0" + "\t");
					}else if(k.getState() == 1){
						System.out.print("ready     0" + "\t");
						k.incWT();
					}else if(k.getState() == 2){
						System.out.print("running   " + cpuBT + "\t");
					}else if(k.getState() == 3){
						System.out.print("blocked   " + blocktime[runList.indexOf(k)] + "\t");
					}else{
						System.out.print("finished  = " + count);
					}
					
				}
				System.out.print("\n");
			}
			// 遍历阻塞队列,blocktime减1,IO 时间加1 ioutil
			for (int i = 0; i < blocktime.length; i++){	
				if (blocktime[i] > 0){ 
					blocktime[i] --;
					runList.get(i).incIOT();
					ioutil ++;
				}
			}
			if (cpuBT > 0){		// 如果CPU剩余时间大于1
				cpuBT --;		// 则减1 
				System.out.print(" [in cpuBT>0] cpuBT =  " + cpuBT + "\n");
				if (cpuBT == 0){		// 如果剩余时间为0了
					Process temp = runTable.get(0);		// 则从就绪队列中取新的进来
					if (temp.getC() > 0){		// 如果取得这个新的剩余时间是大于0的
						temp.setState(3);		// 则阻塞这个进程.应该进入CPU burst了。所以需要阻塞
						int temp1 = randomOS(temp.getIO());
						// System.out.print("frunList.indexOf(temp) = " + runList.indexOf(temp)+"\n");
						blocktime[runList.indexOf(temp)] = temp1;  // 设置该进程的阻塞时间为temp1
						runTable.remove(0); // 从就绪队列中删除这个
						// 为什么IO这里不判断temp1
						System.out.print(" [in cpuBT>0] remove " + temp + "  from runTable \n");
						System.out.print(" [in cpuBT>0] temp.getIO() =  " + temp.getIO() + "\n");
						System.out.print(" [in cpuBT>0] temp1 =  " + temp1 + "\n");
						System.out.print(" blocktime[runList.indexOf(temp)]=  " + blocktime[runList.indexOf(temp)] + "\n");

					}else{
						temp.setft(time);	// 记录当前进程的完成时刻。time表示当前时刻
						temp.setState(4);	// 当前进程完成了
						runTable.remove(0);	// 从运行队列中删除这个结束的进程
						count++;
					}
				}
			}
			if (count == runList.size()){
				check = false;
			}
		}
		System.out.println();
		System.out.println("The algorithm used is FIRST COME FIRST SERVED");
		System.out.println();
		double avgWait = 0, avgTurn = 0, max = -1;
		for(Process p:runList){
			if (p.getft() > max) max = p.getft();	
			avgTurn += p.getft() - p.getA();		 // 每个进程的
			avgWait += p.getWT();
			System.out.println("Process " + runList.indexOf(p) + ":");
			System.out.println("\t" +"(A,B,C,IO) = (" + p.getA() + "," + p.getB() + "," + p.getintialC() +"," + p.getIO() +")");
			System.out.println("\t" + "Finishing time: " + p.getft());
			System.out.println("\t" + "Turnaround time: " + (p.getft() - p.getA()));
			System.out.println("\t" + "I/O time: " + p.getIOT());
			System.out.println("\t" + "Waiting time: " + p.getWT());
		}
		System.out.println();
		avgWait = avgWait / runList.size();
		avgTurn = avgTurn / runList.size();
		System.out.println("Summary Data: ");
		System.out.println("\t" + "Finishing time: " + max);
		System.out.println("\t" + "CPU Utilization : " + cpuutil);
		System.out.println("\t" + "IO Utilization : " + ioutil);
		System.out.println("\t" + "CPU Utilization rate: " + cpuutil/max);
		System.out.println("\t" + "IO Utilization rate: " + ioutil/max);
		System.out.println("\t" + "Troughput " + (runList.size() / max) * 100 +" processes per hundread cycles");
		System.out.println("\t" + "Average Turnaround time: " + avgTurn);
		System.out.println("\t" + "Average Wait time: " + avgWait);
		System.out.println();

		// 表明该进程结束的时间,Q1它与avgTurn有什么区别?基本山是等同的。
		System.out.println("\t" + "Finishing time: " + max);
		System.out.println("\t" + "CPU Utilization : " + cpuutil);
		System.out.println("\t" + "IO Utilization : " + ioutil);
		// CPU使用率 : 比如100ms中,CPU使用了70ms,CPU等待IO花了30ms 则CPU使用率为 70%
		System.out.println("\t" + "CPU Utilization rate : " + cpuutil/max*100+"%");
		// IO使用率
		System.out.println("\t" + "IO Utilization rate : " + ioutil/max*100+"%");



		// 吞吐量 = 单位时间内可完成的进程数量(这里是100ms)
		// System.out.println("\t" + "Troughput " + (runList.size() / max) * 100 +" processes per hundread cycles");
		System.out.println("\t" + "Troughput " + 100/( max/ runList.size() ) +" processes per hundread cycles");
		
		// 平均周转时间 = 该进程运行了多长时间 (即从进程提交到进程完成的时间段称为周转时间)
		System.out.println("\t" + "Average Turnaround time: " + avgTurn);
		// 平均等待时间  ： 在就绪队列中等待所花费的时间
		System.out.println("\t" + "Average Wait time: " + avgWait);
		System.out.println();
	}
	

	public static void main(String args[]) throws Exception{
		Scheduler s = new Scheduler();
		if (args.length == 2){
			verbose = true;
			s.read(args[1]);
		}else{
			s.read(args[0]);
		}
		System.out.println("Please select scheduling algorithm");
		System.out.println("1.FCFS 2.RR 3.UNI 4.PSJN ");
		Scanner in = new Scanner(System.in);
		System.out.println("invalid input");
		String test = in.next();
		System.out.println("test ="+test);
		if (test.equals("1")) s.FCFS();
		else if (test.equals("2")) s.RR();
		else if (test.equals("3")) s.UNI();
		else if (test.equals("4")) s.PSJF();
		else System.out.println("invalid input");
	}
	
}
