# algorithm with python

##**학습목표**
파이썬의 기초문법과 기능등을 손에 익히고,
더불어 뉴런 어딘가에서 방황하고 있을 10년전의 지식을 끄집에 내기위해 위해 가장 기초적인 알고리즘을 풀어보자

---
###1) `1`부터 정수`n`까지의 합
- for문과 연산자

```python
def  sum_n(n):
    s=0
    for i in range(1,n+1):
        s= s+i
    return s
    
print(sum_n(5))
print(sum_n(100))
```
```bash
55
5050
```

##### Q1) 시간복잡도를`O(n)`개선할 방법은 없을까?
```python
def  sum_n(n):
    # //는 정수 나눗셈을 할때 사용
    return n*(n+1)//2
```
### 2) 최대값 구하기
- python의 리스트

| 함수 | 설명 |
|--------|--------|
| `len(a)` | 리스트 길이 |
|`append(v)`|리스트 맨 뒤에 값`v` 추가|
|`insert(i,v)`|`i`번째에 값`v` 추가|
|`pop(i)`|`i`번째에 값을 리스트에서 빼면서 값 리턴|
|`clear()`|리스트 모든 값 삭제|
|`v in a`|값`v`가 리스트`a` 안에 있는지 `boolean`으로 리턴|


```python
def max(a):
    n=len(a)
    r = a[0]
    for i in range(0,n):
        if r < a[i]:
            r = a[i]
    return r
# 대괄호에 쉼표로 구분
v=[17,92,18,33,58,7,33,42]
print(max(v))
```
```bash
92
```

### 3) 같은 이름 찾기
- python의 집합`set`
 > 리스트와 달리 같은자료가 중복으로 들어가지 않고 자료 순서도 의미가 없다

| 함수 | 설명 |
|--------|--------|
| `len(a)` | 집합의 값 갯수 |
|`add(v)`|값`v`추가|
|`discard(v)`|값`v`가 존재한다면 삭제(없으면 변화 무)|
|`clear()`|모든 값 삭제|
|`v in a`|값`v`가 집합`a` 안에 있는지 `boolean`으로 리턴|

```python
def same_name(a):
    n=len(a)
    result = set()
    for i in range(0, n-1):
        for j in range(i+1, n):
            if a[i] == a[j]:
                result.add(a[i])
    return result

v=["Risa", "Jenny", "Jisoo", "Jenny","Rose","Risa"]
print(same_name(v))
```
```bash
# 순서가 의미 없는 set이므로 
{'Risa', 'Jenny'} or {'Jenny', 'Risa'}
```

### 4) factorial 구하기
- Recursion

```python

```



