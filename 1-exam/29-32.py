# -*- coding: UTF-8 -*-
def q29(weight):
	cost = weight * .05	  	
	print(cost)
	if weight > 100:
		print("hello")
		cost = cost - 1.50
	return cost
q29(200)
