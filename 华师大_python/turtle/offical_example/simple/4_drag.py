## 演示红绿蓝三源色组成的色彩(0,0,0)-->黑色  （255,255,255)--->白色
## 学习拖拽事件 
from turtle import Screen, Turtle, mainloop
class ColorTurtle(Turtle):
    ###  x 代表(0,1,2)中的某个,代表(红R,绿G,蓝B) . 
    ###  y 代表R,G,B的颜色值
    ### 如ColorTurtle(0, 0.5) 代表红色的值为0.5
    ### green = ColorTurtle(1, 0.5)代表绿色的值为0.5
    def __init__(self, x, y):
        Turtle.__init__(self)
        #self.shape("turtle")  ## 乌龟图形
        self.shape("triangle")
        self.resizemode("user")
        self.shapesize(3,3,5)
        self.pensize(10)
        self._color = [0,0,0]   ###  _color即是(R,G,B)的值
        self.x = x
        self._color[x] = y     ###
        self.color(self._color)  ### 调用color函数,设置背景颜色
        self.speed(1)
        self.left(90)
        #self.pu()  ## penup   ## 这里是调用了pu()函数 
        self.goto(x,0)
        self.pd()  ## pendown
        self.sety(1)  ##  先把y值设置为1,即画出整根长柱
        #self.pu()   ## penup  调用
        self.sety(y)   ### 再把y值设置为传入的值
        #self.pencolor("gray25")   
        self.ondrag(self.shift)  ## 调用了Ondrag方法  拖拽时,调用shift方法

    def shift(self, x, y):
        print("########### call shift ");
        self.sety(max(0,min(y,1)))  ## 改变乌龟的Y坐标值，相当于移动了乌龟
        print("###########self.ycor()  = ")
        print(self.ycor())
        self._color[self.x] = self.ycor()  ## 调用ycor() 获取y坐标  Return the turtle’s y coordinate.
        #self.fillcolor(self._color)
        setbgcolor()

def setbgcolor():
    screen.bgcolor(red.ycor(), green.ycor(), blue.ycor())  ## 根据red ,green,和blue三个对象的y坐标设置背景颜色

def main():
    global screen, red, green, blue
    screen = Screen()
    screen.delay(0)
    screen.setworldcoordinates(-1, -0.3, 3, 1.3)

    ###  乌龟的纵坐标y代表颜色的程度
    #red = ColorTurtle(0, .5)
    red = ColorTurtle(0, 0.1)
    green = ColorTurtle(1, .5)
    blue = ColorTurtle(2, .5)
    setbgcolor()   ###  设置背景颜色

    # writer = Turtle()  ### 这里要弄一个文本框
    # writer.ht()
    # writer.pu()
    # writer.goto(1,1.15)
    # writer.write("DRAG!",align="center",font=("Arial",30,("bold","italic")))  ## 文本框
    return "EVENTLOOP"

if __name__ == "__main__":
    msg = main()
    print(msg)
    mainloop()

