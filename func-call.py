def foo(a, b):
    a = b

def test(a, b=0):
    a = b

bar()
foo()
foo(1, 2)
foo(1)

# Correct
test(1)
# Correct
test(1, 2)
