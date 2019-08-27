"""       turtle-example-suite:

            tdemo_yinyang.py

Another drawing suitable as a beginner's
programming example.

The small circles are drawn by the circle
command.

"""

from turtle import *

def yin(radius, color1, color2):
    ### step 1 画左边的一半
    width(3)
    color("black", color1)   ## pencolor为black  filling(填充)color为color1 black,即用黑色填充
    #begin_fill()
    circle(radius/2., 180)  ## 以radius/2为半径画一个半圆
    circle(radius, 180)     ## 以radius为半径画一个半圆 (逆时针)
    left(180)
    circle(-radius/2., 180)  ## 以radius为半径,顺时针画一个半圆
    #end_fill()
    ## 画左边中间的圆圈 用color2填充 color(color1, color2)
    left(90)
    forward(radius*0.35)
    right(90)
    color(color1, color2)  ## color1为黑色,color2为白色，即以黑色为pencolor,白色为filling color
    begin_fill()
    circle(radius*0.15)
    end_fill()


    ### step 2 画右边的一半
    pencolor("red")
    left(90)
    # up()
    backward(radius*0.35)
    # down()
    left(90)

def main():
    speed(0)
    reset()
    yin(200, "black", "white")
    yin(200, "white", "black")
    return "Done!"

if __name__ == '__main__':
    main()
    mainloop()

