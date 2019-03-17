def q26(myl):
    check = 0
    for item in myl:
 	    if item < check:
 		    return True
    return False
numlist = [0, 10, 10, 20]
#t = q26(numlist)
#print(t)

#numlist = numlist.append(-50)       ### 注意这里是接受append的返回值,而其返回值为none，所以结果为none
numlist.append(-50)       
print(numlist)

a=[1,2,3]
a = a.append(-4)
print(a)
