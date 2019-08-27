from turtle import *
from datetime import datetime

def jump(distanz, winkel=0):
    penup()
    right(winkel)
    forward(distanz)
    left(winkel)
    pendown()

def hand(laenge, spitze):
    fd(laenge*1.15)   ## forward
    rt(90)  ## right
    fd(spitze/2.0)  
    lt(120)  ## left
    fd(spitze)
    lt(120)
    fd(spitze)
    lt(120)
    fd(spitze/2.0)

def make_hand_shape(name, laenge, spitze):
    reset()
    jump(-laenge*0.15)
    begin_poly()
    hand(laenge, spitze)
    end_poly()
    hand_form = get_poly()
    register_shape(name, hand_form)

def clockface(radius):
    reset()
    pensize(7)
    for i in range(60):
        jump(radius)
        if i % 5 == 0:
            fd(25)
            jump(-radius-25)
        else:
            dot(3)
            jump(-radius)
        rt(6)

def setup():
    global second_hand, minute_hand, hour_hand, writer
    mode("logo")
    # 调用make_hand_shape 三次
    make_hand_shape("second_hand", 125, 25)
    make_hand_shape("minute_hand",  130, 25)
    make_hand_shape("hour_hand", 90, 25)
    clockface(160)
    ## 为second_hand, minute_hand, hour_hand 三个变量赋值
    second_hand = Turtle()
    second_hand.shape("second_hand")
    second_hand.color("gray20", "gray80")
    minute_hand = Turtle()
    minute_hand.shape("minute_hand")
    minute_hand.color("blue1", "red1")
    hour_hand = Turtle()
    hour_hand.shape("hour_hand")
    hour_hand.color("blue3", "red3")
    ## second_hand, minute_hand, hour_hand三个变量都调用resizemode ,shapesize
    for hand in second_hand, minute_hand, hour_hand:
        hand.resizemode("user")
        hand.shapesize(1, 1, 3)
        hand.speed(1)   ## 1是最慢
        #hand.speed(0)  ## 0是最快
    #ht()  # hideturtle()  隐藏

    writer = Turtle()
    #writer.mode("logo")
    #writer.ht()
    writer.pu()  ## penup()
    writer.bk(85)  ## backcolor

def wochentag(t):
    wochentag = ["Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday", "Sunday"]
    return wochentag[t.weekday()]

def datum(z):
    monat = ["Jan.", "Feb.", "Mar.", "Apr.", "May", "June",
             "July", "Aug.", "Sep.", "Oct.", "Nov.", "Dec."]
    j = z.year
    m = monat[z.month - 1]
    t = z.day
    return "%s %d %d" % (m, t, j)

def tick():
    t = datetime.today()
    sekunde = t.second + t.microsecond*0.000001
    minute = t.minute + sekunde/60.0
    stunde = t.hour + minute/60.0
    try:
        tracer(False)  # Terminator can occur here
        writer.clear()
        writer.home()
        writer.forward(65)
        writer.write(wochentag(t),
                     align="center", font=("Courier", 14, "bold"))
        writer.back(150)
        writer.write(datum(t),
                     align="center", font=("Courier", 14, "bold"))
        writer.forward(85)
        tracer(True)
        second_hand.setheading(6*sekunde)  # or here
        minute_hand.setheading(6*minute)
        hour_hand.setheading(30*stunde)
        tracer(True)
        ontimer(tick, 100)
    except Terminator:
        pass  # turtledemo user pressed STOP

def main():
    tracer(False)  ## tracer 是用来提高性能的,它代表多少个screen设置一次，这里禁用了tracer，也就是没一个画面都要
    setup()        ## 在屏幕刷新
    tracer(True)
    tick()
    return "EVENTLOOP"

if __name__ == "__main__":
    mode("logo")
    msg = main()
    print("##### ___main___  msg = ")
    print(msg)
    mainloop()

