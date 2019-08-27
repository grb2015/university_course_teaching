# File: tdemo_chaos.py
# Author: Gregor Lingl
# Date: 2009-06-24

# A demonstration of chaos

from turtle import *

N = 80

def f(x):
    return 3.9*x*(1-x)

def g(x):
    return 3.9*(x-x**2)

def h(x):
    return 3.9*x-3.9*x*x

def jumpto(x, y):  
    #penup();    ## jump的时候不应该画线
    goto(x,y)

def line(x1, y1, x2, y2):
    jumpto(x1, y1)
    pendown()
    goto(x2, y2)

#   (-1,0.1)   (81, 1.1)
def coosys():
    line(-1, 0, N+1, 0)  #(-1,0)  (81,0)
    line(0, -0.1, 0, 1.1) # (0,-0,1) (0,1,1)

# plot(f, 0.35, "blue")
def plot(fun, start, color):
    pencolor(color)
    x = start
    jumpto(0, x)
    pendown()
    #pendown()
    dot(1)
    for i in range(N):
        x=fun(x)   ### 通过二次函数映射
        goto(i+1,x)
        dot(5)

def main():
    reset()
    setworldcoordinates(-1.0,-0.1, N+1, 1.1)
    speed(0)
    #hideturtle()
    coosys()
    plot(f, 0.35, "red")
    # plot(g, 0.35, "green")
    # plot(h, 0.35, "red")
    # Now zoom in:  ## 放大
    if(0):  ## 一次性放到最大
        setworldcoordinates(50,-0.1, N+1, 1.1)
    else:
        for s in range(100):  ## 慢慢放大
            setworldcoordinates(0.5*s,-0.1, N+1, 1.1)
    return "Done!"

if __name__ == "__main__":
    main()
    mainloop()  ## 为了绘制完后界面不退出

