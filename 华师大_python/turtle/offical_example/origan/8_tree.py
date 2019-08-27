"""      turtle-example-suite:

             tdemo_tree.py

Displays a 'breadth-first-tree' - in contrast
to the classical Logo tree drawing programs,
which use a depth-first-algorithm.

Uses:
(1) a tree-generator, where the drawing is
quasi the side-effect, whereas the generator
always yields None.
(2) Turtle-cloning: At each branching point
the current pen is cloned. So in the end
there are 1024 turtles.
"""
from turtle import Turtle, mainloop
from time import clock

def tree(plist, l, a, f):
    """ plist is list of pens
    l is length of branch     ### L代表第一个分支(即树的主干的长度)  这里我们设置为200
    a is half of the angle between 2 branches  ## a代表两个分支直接的角度 45度 则之间为90度
    f is factor by which branch is shortened  ## f 代表下一个树枝的长度是当前的几分之几  0.5 每次减半
    from level to level."""
    #if l > 3:
    if l > 25: ## 停止画树的条件,当树分支上次短于50时停止画树
        lst = []
        for p in plist:
            print("######## for ")
            p.forward(l)
            q = p.clone()  ### clone()一个光标出来
            p.left(a)  ## 初始的光标左转45度
            q.right(a) ## 克隆出来的关闭右转
            lst.append(p)
            lst.append(q)
        # for x in tree(lst, l*f, a, f):   ### 递归下去
        #     yield None
            tree(lst, l*f, a, f)  ### 递归下去

def maketree():
    p = Turtle()
    p.setundobuffer(None) ## 可不用管这个
    #p.hideturtle()
    p.speed(1)
    #p.getscreen().tracer(30,0)  ## 获取screen ,然后设置tracer   ## 注释掉它以便观察绘制过程
    p.left(90)  ## 左转90 ,因为初始时是向右的,转了之后就朝上
    p.forward(-210)
    t = tree([p], 200, 45, 0.5)
    # for x in t:
    #     pass
    # print(len(p.getscreen().turtles()))

def main():
    # a=clock()
    maketree()
    # b=clock()
    # return "done: %.2f sec." % (b-a)

if __name__ == "__main__":
    msg = main()
    print(msg)
    mainloop()

