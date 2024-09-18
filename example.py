#! /usr/bin/env python
#A miniPython example

import math
import math as m, lib, lib2 as l
import math, meow
from foo import math as m
from foo import math as m, lib, lib2 as l

def fib(n):    # write Fibonacci series up to n
               a = 0
               b = 1
               while a < n:
                        print a
                        a = b
                        b = a + b

               for i in a:
                    a = b

def funcwithdef(name,university="aueb"):
    print name, " studies in ", university

def anotherTest(b=None, c=0):
    return 0

def compare(a, b, c):
    if a == b and a != b or a <= b and a >= b:
        a = b

    # Testing multiple OR in series
    if a == b or a == b or a == b:
        meow = 0

    if not a < b:
        a = b

    if not not true and not true:
        a = b

def math(test):
    a = 0
    b = 1
    c = b % a
    b -= a
    a /= b

    c = b ** a
    c = 2 ** a
    c = 2 ** 2
    c = 2 ** f.meow()

    a = max(5, b)
    b = min(a.function(), 3)

    a[4] = 5
    a[b] = 5

functionCall()
functionCallWithArgs(a, b)
a = 'foo'
