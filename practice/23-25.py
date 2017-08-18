def q23(s):
    res = []
    for ch in s:
        if ch.islower():
            res.append(ch)
        else:
            return res
name = 'Ducks'
t = q23(name)
print(t)

#[root@localhost practice]# python 23-25.py 
#[]
#[root@localhost practice]# 

