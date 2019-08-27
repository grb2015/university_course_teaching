""" 
    学习 update事件
     turtle-example-suite:

         tdemo_round_dance.py

(Needs version 1.1 of the turtle module that
comes with Python 3.1)

Dancing turtles have a compound shape
consisting of a series of triangles of
decreasing size.

Turtles march along a circle while rotating
pairwise in opposite direction, with one
exception. Does that breaking of symmetry
enhance the attractiveness of the example?

Press any key to stop the animation.

Technically: demonstrates use of compound
shapes, transformation of shapes as well as
cloning turtles. The animation is
controlled through update().
"""

from turtle import *

def stop():
    global running
    running = False

def main():
    #pencolor("red")
    speed(1)
    global running
    clearscreen()
    bgcolor("gray10")
    tracer(False)  ## 闭关trace 即实时刷新
    shape("triangle")
    f =   0.793402
    phi = 9.064678
    s = 5   ##  size 
    c = 1   ### color
    # create compound shape
    ###  step1 创建基本图形，即一个三角形里面包含若干个小三角形的复杂图案
    sh = Shape("compound")  ## 声明是一个复合形状
    for i in range(10):
        shapesize(s)
        p = get_shapepoly()  ## 返回三角形的各个顶点的坐标
        # print("p = ")
        # print(p)
        s *= f  ##  size 
        c *= f  ### color
        tilt(-phi)
        sh.addcomponent(p, (1, 1, 1), "green")  ## 新加入一个三角形, green为三角形外框的颜色
        #sh.addcomponent(p, (c, 0.25, 1-c), "green")    ## 新加入一个三角形, green为三角形外框的颜色  
                                                        ## (c, 0.25, 1-c)这个为填充的颜色

    register_shape("multitri", sh)  ### 自定义一个图形，叫multitri，后面可以用
    # # create dancers
    shapesize(1)  
    shape("multitri")   ### 创建我们自定义的图形multitri
    # pu()
    setpos(0, -200)
    dancers = []
    #update()  ### 调用update()只会创建一个。
    #step2 创建一圈9个上面的复杂三角形图案
    for i in range(180):
        fd(7)  ### forward
        tilt(-4) ## 对图形旋转4度
        lt(2) ## left  注意这里是对乌龟旋转(上面的tilt是对图形而已，即我们的三角形) 每次转2度，180次即360度
        update()
        #clone()
        if i % 12 == 0:
            dancers.append(clone())  ## clone()是真正复制三角形的动作 clone()出来的图案Position应在光标处
                                    ## 由于光标在变化，所以clone()出来的图形也在变
    home()
    # dance
    print("######### dances = %s",dancers)
    running = True
    onkeypress(stop)  ## 按键后就执行stop ,里面设置running为false
    listen()  ## 监听按键
    cs = 1
    #update() 
    # fd(7)
    # update() 
    

    while running:
        ta = -4
        ### 四周的三角形图案转动
        for dancer in dancers:
            #print("######### dancer i ")
            dancer.fd(7)  # 
            dancer.lt(2)
            dancer.tilt(ta)
            #ta = -4 if ta > 0 else 2
        if cs < 180:
            right(4)
            shapesize(cs)    ### 中间的三角形慢慢变大
            #cs *= 1.005
        update()   ### 只要调用了一次update，就会创建一个.但是update只能创建一个,多个要clone()
    return "DONE!"

if __name__=='__main__':
    print(main())
    mainloop()

