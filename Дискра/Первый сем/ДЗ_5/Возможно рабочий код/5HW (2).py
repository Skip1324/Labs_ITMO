import numpy as np

from pylatex import Document, Section, Subsection, Tabular, Math, Command, Package
from pylatex.utils import italic
import os

#title=input("Фамилия, имя, группа, вариант:")
#A=input("A:")
#B=input("B:")
title="ZZz"
A=1152
B=16
a="0"*(16-len(bin(A)[2:]))+bin(A)[2:]
b="0"*(8-len(bin(B)[2:]))+bin(B)[2:]
_a=a.replace("1","O",a.count("1")-1)
_a=(_a.replace("0","Z")[:_a.find("1")]+_a[_a.find("1"):]).replace("O","0").replace("Z","1")
_b=b.replace("1","O",b.count("1")-1)
_b=(_b.replace("0","Z")[:_b.find("1")]+_b[_b.find("1"):]).replace("O","0").replace("Z","1")
if __name__ == '__main__':
    
    doc = Document()
    doc.packages.append(Package('babel',["russian"]))
    doc.preamble.append(Command('selectlanguage', 'russian'))

    with doc.create(Section('Домашняя работа №5', False)):

        doc.append(title)
        doc.append("\nA="+str(A)+" B="+str(B))
        doc.append("\n[+A]пр = "+a+"  "+"[-A]доп = "+_a)
        doc.append("\n[+B]пр = "+b+" "+"[-B]доп = "+_b)
        with doc.create(Subsection('Делимое положительное, делитель отрицательный(A>0,B<0):', False)):
            with doc.create(Tabular('|c|c|c|c|')) as table:
                table.add_hline()
                table.add_row(("№ шага", "Операнды и действия", "Делимое и остаток", "Делимое и остаток"))
                table.add_row(("", "", "(старшие разряды)", "(младшие разряды),"))
                table.add_row(("", "", "", "частное"))
                table.add_hline()
                table.add_row((1, 2, 3, 4))
                table.add_hline()
                table.add_row((0, "[A]пр", a[0:8], a[8:]))
                table.add_hline()
                table.add_row((1, "[B]доп", Command('underline','1'*8), Command('underline',_b)))
                r=bin(int('1'*8+_b,2)+int(a,2))[2:][1:17]
                #print(r)
                table.add_row(("", "R1", r[0:8], r[8:]))
                r=r[1:17]+"0"
                #print(r)
                table.add_row(("", "R1<-", r[0:8], ""))
                table.add_row(("", "+", "", ""))
                table.add_row(("", "[B]доп",Command('underline',_b), ""))
                r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                table.add_row(("", "R1", r[0:8], r[8:]))
                

   
        
doc.generate_tex('full')
os.system("/opt/local/bin/pdflatex full.tex")
#os.system("rm full.tex")
os.system("rm full.log")
os.system("rm full.aux")

