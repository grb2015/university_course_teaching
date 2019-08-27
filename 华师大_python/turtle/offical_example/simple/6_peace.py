""" 
    学习基本的移动,角度。
      turtle-example-suite:

              tdemo_peace.py

A simple drawing suitable as a beginner's
programming example. Aside from the
peacecolors assignment and the for loop,
it only uses turtle commands.
"""

from turtle import *

def main():
    peacecolors = ("red3",  "orange", "yellow",
                   "seagreen4", "orchid4",
                   "royalblue1", "dodgerblue4")

    reset()
    Screen()
    #up()  ### penup() 
    goto(-320,-195)
    width(70)   ### 设置粗线！
    #goto(100,-195)  ## 可以试试这个

    ##  1.画不同颜色的粗横线
    for pcolor in peacecolors:
        color(pcolor)
        #down()  ## pendown()
        forward(640)
        #up()
        backward(640)  ## 画完之后要返回来
        left(90)
        forward(66)  ## 画完之后，要向上移动一段,以便画上面的线
        right(90)
    ##  2.画图形
    
        ## step1 将光标移动到(0,-170)
    width(25)
    color("white")
    goto(0,-170)
    #down()
        ## step2 画圆圈
    circle(170)
        ##  step3 画向上的横线
    left(90)
    forward(340)
        ## step4 ... 向下移动170 回到圆心
    left(180)
    forward(170)
        ## step5 向左下移动170
    right(45)
    forward(170)
        ## step6  backward 170回到圆心,然后向右下移动170
    backward(170)
    left(90)
    forward(170)

    #goto(0,300) # vanish if hideturtle() is not available ;-)
    return "Done!"

if __name__ == "__main__":
    main()
    mainloop()

