1.关于CPU burst的解释

CPU burst就是cpu区间。IOburst就是IO区间。
一个进程是CPU burst和IO burst的交替
-----IIIIII------IIIIII-------
8       5      8    5           8              有3个CPU burst  总的为24  ，有2个IOburst总的为10

--------------------------
24 

2. 运行说明：
forked from  : https://github.com/Neo3333/Scheduling

CSCI-202 Operating System Lab 2

How to run this program?
1, Please have all the input files in the same directory as the program Scheduler.java and Process.java that will be tested.

2, Please change to the directory where Scheduler.java is currently at.

3, Please compile the program by typing "javac Scheduler.java".

4, Run the program by typing "java Scheduler input-filename".

5, If you need the more detailed output, type "java Scheduler verbose input-filename".

6, Select the Sceduling Algorithm you want to simulate, follow the instructions of the program.

Author: Jiabei Han

Email: jh4873@nyu.edu

