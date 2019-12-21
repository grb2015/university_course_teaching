#!/usr/bin/env python
# breif : python 实现linux wc 命令
# ref https://www.cnblogs.com/dachenzi/p/6792430.html
import os,sys
from optparse import OptionParser
def opt():
    'Get Command line parser'
    parser = OptionParser()
    parser.add_option('-c','--char',dest='chars',action='store_true',default=False,help='used to count chars')
    parser.add_option('-w','--word',dest='words',action='store_true',default=False,help='used to count words')
    parser.add_option('-l','--line',dest='lines',action='store_true',default=False,help='used to count lines')
    option,args = parser.parse_args()
    return option,args

def get_count(data):
    'count for lines ,words or chars'
    chars = len(data)
    words = len(data.split())
    lines = data.count('\n')
    return lines,words,chars

def print_wc(option,lines,words,chars,filename):
    'print lines,words or chars'
    if option.lines:
        print (lines),
    if option.words:
        print (words),
    if option.chars:
        print (chars),
    print (filename)

def main():
    'main functions'
    option,args = opt()
    if not (option.chars or option.words or option.lines):
        option.chars,option.words,option.lines = True,True,True
    print("args = ")
    print(args)
    if args:
        total_lines,total_words,total_chars = 0, 0, 0
        for filename in args:
            if os.path.isfile(filename):
                with open(filename) as fd:
                    data = fd.read()
                    print("data = ")
                    print(data)
                    print(type(data))
                    lines,words,chars = get_count(data)
                    print_wc(option,lines,words,chars,filename)
                    total_lines += lines
                    total_words += words
                    total_chars += chars
            elif os.path.isdir(filename):
                print ('%s is a directory' % filename)
            else:
                sys.exit('%s : No such file or Directory' % filename)
        if len(args) > 1:
            if not option.nototal:
                print_wc(option,total_lines,total_words,total_chars,'total')
    else:
        data = sys.stdin.read()
        filename = ''
        lines,words,chars = get_count(data)
        print_wc(option,lines,words,chars,filename)


if __name__ == '__main__':
    main()