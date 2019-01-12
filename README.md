# BruteForce

When you start the application it will require you to introduce a sentence formed from digits, x signs and opperation.  
An example will be: 
```
2x6 * 1xx = x5x + xx24 + 2xx8 = 37xxx
```
where  
```
x5x = 2x6 * <first digit of the multiplicand>(1)
xx24 = 2x6 * <second digit of multiplicand>(x)
2xx8 = 2x6 * <third digit of multiplicand>(x)
```
The result will be:  
256 148 37888
  
Another example:  
```
4x6xx * x2x = x9xx2x + 9xx4x + 19x4x4 = 3xx3xxx4  
```

If you ommit the intermediate numbers and introduce just the multiplier, multiplicand and the final result 
``` 2x6 * 1xx = 37xxx```
the program will list you all possible solutions.
