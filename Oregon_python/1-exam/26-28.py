def q26(x):
	result = x * x		
	return result

def q27(x):
	x += 1
	return x
def main():
	result = q27(q26(3))
	print(result)
	return None
main()
