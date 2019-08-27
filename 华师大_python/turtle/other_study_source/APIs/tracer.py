from turtle import *
screen = Screen()
#screen.tracer(8, 25)   ###这个实际上用来提高性能的。
dist = 2
speed(1)
#for i in range(200):
for i in range(20):
    fd(dist)  ## forward
    rt(90)  ## right ?
    dist += 2
exitonclick()