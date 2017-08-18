def q23(s):
    res = []
    for ch in s:
        if ch.islower():
            res.append(ch)
    return res
name = 'Ducks'

#name = list('Ducks')
#print(type(name))

t = q23(name)
print(t)

