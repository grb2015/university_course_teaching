P = 5
R = 3
def calculateNeed(need, maxm, allot): 
    for i in range(P): 
        for j in range(R): 
            need[i][j] = maxm[i][j] - allot[i][j]  
  
def isSafe(processes, avail, maxm, allot): 
	# step1 计算need 
	need = []  
	for i in range(P): 
		l = [] 
		for j in range(R): 
			l.append(0) 
		need.append(l) 
	print(need)
	calculateNeed(need, maxm, allot) 
	print(need)

	# step2 初始化一些变量
	finish = [0] * P   # [0,0,0,0,0]  array   int finish = [0,0,...]
	safeSeq = [0] * P  # [0,0,0,0,0]
	work = [0] * R  	# [0,0,0]
	# print("finish = ",finish)
	# print("safeSeq = ",safeSeq)
	# print("work = ",work)

	# 初始化work[R] 为 avail[R]
	for i in range(R): 
		work[i] = avail[i]  
	count = 0
	while (count < P): 
		found = False
		for p in range(P):  
			if (finish[p] == 0):  
				# 判断need_i是否小于work
				# 不小于 ,则退出 ,此时found为false
				for j in range(R): 
					if (need[p][j] > work[j]): 
						break  
				# 小于 ,则该资源可以分配
				if (j == R - 1):  
					for k in range(R):  
						work[k] += allot[p][k]   # 释放了进程,系统可用资源增加了。
					safeSeq[count] = p   #记录安全序列
					count += 1  
					finish[p] = 1  #将该进程标记为完成。因为它已经释放了
					found = True           
		if (found == False): 
			print("System is not in safe state") 
			return False        
	print("System is in safe state.\n Safe sequence is: ", end = " ") 
	print(safeSeq)  
	return True
if __name__ =="__main__": 
    processes = [0, 1, 2, 3, 4] 
    avail = [3, 3, 2]  
    maxm = [
				[7, 5, 3], 
				[3, 2, 2], 
				[9, 0, 2], 
				[2, 2, 2], 
				[4, 3, 3]
			] 
    allot = [	
				[0, 1, 0], 
				[2, 0, 0], 
				[3, 0, 2], 
				[2, 1, 1], 
				[0, 0, 2]
			]  
    isSafe(processes, avail, maxm, allot)  