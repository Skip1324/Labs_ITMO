import math
from pylatex import Document, Section, Subsection, Tabular, Math, NoEscape, Command, Package
import os

def beer(a):
    z=int(a)
    ost=a-z
    result="";shag=0
    while ost-int(ost)!=0 and shag<30:
        shag+=1
        ost=ost*2
        result+=str(int(ost))
        ost=ost-int(ost)
    return [bin(z)[2:],result]

def cut(r,adds=""):
    result=r[:13]
    if result[-1]=="1" and len(r)>13:
        result=bin(int(result[:-1],2)+1)[2:]
        result = adds+result
    else:
        result=result[:-1]
    return result+"0"*(13-len(result))

def dop(a):
    if a>0:
        b=bin(a)[2:]
        return "1"+"0"*(6-len(b))+b
    else:
        b=bin(a)[3:]
        _b=b.replace("1","O",b.count("1")-1)
        _b=(_b.replace("0","Z")[:_b.find("1")]+_b[_b.find("1"):]).replace("O","0").replace("Z","1")
        return "0"+"1"*(6-len(_b))+_b
    

def toF1(a):
    z=a[0]
    rest=a[1]
    if z!="0":
        len_z=len(z)
        if len_z%4!=0:
            add="0"*(4-len_z%4)
        else:
            add=""
        
        por=len_z//4
        if len_z%4!=0:
            por+=1
        return("0|"+dop(por)+"|"+cut(add+a[0]+rest,adds=add))
    else:
        proto_por=0
        for i in rest:
            if i=="0":
                proto_por+=1
            else:
                break
        por=(-1)*(proto_por//4)
        return("0|"+dop(por)+"|"+cut(rest[por*(-4):]))
    
#A=6.9  
#B=0.051 Your numbers

#print(beer(A,12), "\n", beer(B,12))


    


