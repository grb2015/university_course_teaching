#!/usr/bin/sh
#-*- coding: utf-8 -*-
#================================================================
#   Copyright (C) 2017  free for learn .
#   
#   file£º     20-22.py
#   breif£º
#   history:   2017-08-17renbin.guo created
#   usage£º
#   note£º 
#
#================================================================
def two_digits(s):
    dig = 0
    for ch in s:
        if ch.isdigit():
            dig += 1
        else:
            return False
    if dig <= 2:
        return False
    else:
        erturn True





