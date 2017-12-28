def max(a):
    n=len(a)
    r = a[0]
    for i in range(0,n):        
        if r < a[i]:
            r = a[i]
    return r

v=[17,92,18,33,58,7,33,42]
print(max(v))
