# -*- coding: UTF-8 -*-
from turtle import *
def q14(n):
    le = 100
    angle = 360 / n         # angle = 90
    for i in range(n):
        fd(le)
        lt(angle)
    return None
q14(4)
