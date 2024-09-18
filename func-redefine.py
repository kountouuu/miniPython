def foo(a, b, c):
    a = b

def foo(a, b, c=0, d):
    a = c

def bar(a, b, c):
    a = b

def bar(a, b, c):
    a = b

def test(a, b):
    a = b

def test(a, b, c=0):
    a = b

def add(x,y,z):
    x = y

def add(x,y,z=1):
    x = y
