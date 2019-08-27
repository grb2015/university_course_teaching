"""
###  学习使用多块画布进行布局
turtledemo.two_canvases

Use TurtleScreen and RawTurtle to draw on two
distinct canvases in a separate windows. The
new window must be separately closed in
addition to pressing the STOP button.
"""

from turtle import TurtleScreen, RawTurtle, TK

def main():
    root = TK.Tk()
    cv1 = TK.Canvas(root, width=300, height=200, bg="#ddffff")
    cv2 = TK.Canvas(root, width=300, height=200, bg="#ffeeee")
    cv1.pack()
    cv2.pack()

    s1 = TurtleScreen(cv1)
    s1.bgcolor(0.85, 0.85, 1)
    #s1.bgcolor(0, 0, 0)  ## 黑色
    s2 = TurtleScreen(cv2)
    s2.bgcolor(1, 0.5,0.5) ## 粉红

    ## 得到两个画笔的图标
    p = RawTurtle(s1)  ##  以sreen对象作参数
    q = RawTurtle(s2)

    ## 改变画笔的颜色和大小,设置填充颜色
    #p.color("red", (1, 0.85, 0.85))
    p.color("red", (1, 1, 1))   ###  red是pencolor,  (1, 1, 1)是filling color
    p.width(0.1)  ## 设置画笔大小
    q.color("blue", (0.85, 0.85, 1))
    #q.color("blue", (0, 0, 0))

    q.width(6)

    for t in p,q:
        t.shape("turtle")  ### 将p,q的画笔图标都设置为乌龟图案
        t.lt(36)  ## 乌龟图标都左转36度

    q.lt(180)   ##  q乌龟转180度

    for t in p, q:
        t.begin_fill()
    ### 画五边形
    for i in range(5):
        for t in p, q:
            t.fd(50)
            t.lt(72)
    for t in p,q:
        t.end_fill()
        # t.lt(54)
        # t.bk(50)  ##  backward()

    return "EVENTLOOP"


if __name__ == '__main__':
    main()
    TK.mainloop()  # keep window open until user closes it

