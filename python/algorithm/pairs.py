def pairs(a):
    n=len(a)
    result = set()

    for i in range(0, n-1):        
        for j in range(i+1, n):
                result.add(a[i]+" - " +a[j])
    return result

v=["Risa", "Jenny", "Jisoo"]
print(pairs(v))
