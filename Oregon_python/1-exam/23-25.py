# -*- coding: UTF-8 -*-
import random
def q23():
    n = random.randint(1, 290)
    return n   ## 返回1-290的随机数
def q24():
    n = random.randint(-178, 1)   ## 返回-178  -- 1的随机数
    return n

def q25():
    wc_data = q23()     ## wc_data为(1,200)的随机数
    temp_data = q24()   ## temp_data为-178---1的随机数

    print(wc_data, '\t',temp_data, '\t')
    return None

q25()
