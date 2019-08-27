"""  学习 Undo  
   turtle-example-suite:

          tdemo_wikipedia3.py

This example is
inspired by the Wikipedia article on turtle
graphics. (See example wikipedia1 for URLs)

First we create (ne-1) (i.e. 35 in this
example) copies of our first turtle p.
Then we let them perform their steps in
parallel.

Followed by a complete undo().
"""
from turtle import Screen, Turtle, mainloop
from time import clock, sleep

def mn_eck(p, ne,sz):  ##  ne = 36  ,sz = 19
    turtlelist = [p]
    #create ne-1 additional turtles
    ## step1 clone 了6个光标(飞镖)图标
    for i in range(1,ne):
        q = p.clone()
        q.rt(360.0/ne)  ### 旋转某个角度
        turtlelist.append(q)
        p = q
    for i in range(ne):
        # let those ne turtles make a step
        # in parallel:
        for t in turtlelist:   ## 每个飞镖图标每次右转60度,然后前进sz长度
            t.rt(360./ne)  
            t.fd(sz)  ##  forward  sz  

def main():
    ###  step  绘制图形

    s = Screen()
    s.bgcolor("black")
    p = Turtle()
    p.speed(0)
    # p.hideturtle()
    p.pencolor("red")
    p.pensize(3)
    #s.tracer(36,0)  ### 不需要每次刷新,36次变化后再刷新一次即可，用于提高性能。 我们注释掉它!才能更好看清绘图过程。

    #mn_eck(p, 36, 19)
    mn_eck(p, 6, 19)   ###  我们改变ne值,图形就会变


    sleep(1)
    ##  step2  实现undo 

    ## s是Screen对象,s.turtles() 为返回屏幕上所有的turtles对象
     ## python any :只要里面一个不为空,则为true

    #for test
    for t in s.turtles():
        print(t);
        print(t.undobufferentries())
        print("------------------------");
    #any_list = [t.undobufferentries() for t in s.turtles()]
    #print("any_list = ",any_list) 
    #any_list =  [15, 16, 17, 18, 19, 20]
    
    while any([t.undobufferentries() for t in s.turtles()]):  
        print([t.undobufferentries() for t in s.turtles()])  ### 打印出来一目了然
        for t in s.turtles():
            t.undo()
    return "END"


if __name__ == '__main__':
    msg = main()
    print(msg)
    mainloop()

