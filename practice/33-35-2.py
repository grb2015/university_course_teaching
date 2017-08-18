with open('key.txt', 'r') as keyf:
    for i in range(3):
	    keyf.readline()
    nextline = keyf.readline
    print(type(nextline))
