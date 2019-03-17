def q35(s1, s2): 
'''(str, str) -> (??)
34 Exam function
5 '''
	field_width = 18
	ttl_len = len(s1) + len(s2) 
	fits = ttl_len <= field_width 
	return fits
q35('Hood', 'Sandy')