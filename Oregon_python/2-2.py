#!/usr/bin/python
# -*- coding: utf-8 -*-
#================================================================
#  Copyright (C) 2017 free for learn
#   
#     file:	2-2.py
#    brief£º
#  history:	2017-08-12  renbin.guo created
#     note:	
#    usage:	
#
#================================================================
def reserve_1st_last(str1):
    #if(str != NULL)
    str=list(str1)
    t=str[0]
    str[0]=str[-1]
    str[-1]=t
    return str
str=reserve_1st_last('guorenbin')
print(str)



