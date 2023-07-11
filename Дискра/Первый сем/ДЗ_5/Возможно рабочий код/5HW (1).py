import numpy as np

from pylatex import Document, Section, Subsection, Tabular, Math, Command, Package
from pylatex.utils import italic
import os

#title=input("Фамилия, имя, группа, вариант:")
#A=int(input("A:"))
#B=int(input("B:"))
title="ZZZ"
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
    #doc.packages.append(Package('fontenc',["T1","T2A"]))
    #doc.packages.append(Package('finputenc',["utf8"]))
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
                table.add_row(1, 2, 3, 4)
                table.add_hline()
                table.add_row(0, "[A]пр", a[0:8], a[8:])
                table.add_hline()
                table.add_row(1, "[B]доп", Command('underline','1'*8), Command('underline',_b))
                r=bin(int('1'*8+_b,2)+int(a,2))[2:][1:17]
                #print(r)
                table.add_row("", "R1", r[0:8], r[8:])
                r=r[1:17]+"0"
                #print(r)
                table.add_row("", "R1<-", r[0:8], "")
                table.add_row("", "[B]доп",Command('underline',_b), "")
                r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                table.add_row("", "R1", r[0:8], r[8:15]+"|"+r[15])
                table.add_row("", "", r[0:8], r[8:15]+"|"+r[0])
                r=r[:-1]+r[0]
                table.add_hline()
                is_sub=r[0]==a[0]
                
                for i in range(7):
                    r=r[1:17]+"0"
                    if i!=6:
                        table.add_row(str(i+2), "R"+str(i+1)+"<-", r[0:8], r[8:14-i]+"|"+r[14-i:])
                    else:
                        table.add_row("8", "R7<-", r[0:8], r[8:])
                        
                    if not(is_sub):
                        table.add_row("", "[-B]пр",Command('underline',b) , "")
                        r=bin(int(r[0:8],2)+int(b,2))[2:][-8:]+r[8:]
                    else:
                        table.add_row("", "[B]доп",Command('underline',_b) , "")
                        r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                    r=r[:-1]+r[0]
                    if i!=6:
                        table.add_row("", "R"+str(i+2), r[0:8], r[8:14-i]+"|"+r[14-i:])
                    else:
                        table.add_row("", "R8", r[0:8], r[8:])
                    table.add_hline()

                    is_sub=r[0]==a[0]
            doc.append("\n")
            if r[8:][0]=="1":
                _r=r[8:].replace("1","O",r[8:].count("1")-1)
                _r=(_r.replace("0","Z")[:_r.find("1")]+_r[_r.find("1"):]).replace("O","0").replace("Z","1")
                doc.append("\nCдоп = "+r[8:]+"= -"+str(int(_r,2)))
                doc.append("\nОстаток = "+r[:8]+" = "+str(int(r[:8],2)))
            else:
                doc.append("\nCдоп = "+r[8:]+"= "+str(int(_r,2)))
                doc.append("\nОстаток = "+r[:8]+" = "+str(int(r[:8],2)))
                
        with doc.create(Subsection('Делимое отрицательное, делитель отрицательный(A<0,B<0):', False)):
            with doc.create(Tabular('|c|c|c|c|')) as table:
                table.add_hline()
                table.add_row(("№ шага", "Операнды и действия", "Делимое и остаток", "Делимое и остаток"))
                table.add_row(("", "", "(старшие разряды)", "(младшие разряды),"))
                table.add_row(("", "", "", "частное"))
                table.add_hline()
                table.add_row(1, 2, 3, 4)
                table.add_hline()
                table.add_row(0, "[A]доп", _a[0:8], _a[8:])
                table.add_hline()
                r=_a
                r=r[1:17]+"0"
                table.add_row(1, "[A]доп<-", r[0:8], r[8:])
                table.add_row("", "[-B]пр",Command('underline',b) , "")
                r=bin(int(r[0:8],2)+int(b,2))[2:][-8:]+r[8:]
                table.add_row("", "R1", r[0:8], r[8:15]+"|"+r[15:])
                table.add_hline()
                is_sub=True
                i=0
                for i in range(7):
                    r=r[1:17]+"0"
                    if i!=6:
                        table.add_row(str(i+2), "R"+str(i+1)+"<-", r[0:8], r[8:14-i]+"|"+r[14-i:])
                    else:
                        table.add_row("8", "R8", r[0:8], r[8:])
                        
                    if not(is_sub):
                        table.add_row("", "[-B]пр",Command('underline',b) , "")
                        r=bin(int(r[0:8],2)+int(b,2))[2:][-8:]+r[8:]
                    else:
                        table.add_row("", "[B]доп",Command('underline',_b) , "")
                        r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                    r=r[:-1]+r[0]
                    if i!=6:
                        table.add_row("", "R"+str(i+2), r[0:8], r[8:14-i]+"|"+r[14-i:])
                    else:
                        table.add_row("", "R8", r[0:8], r[8:])
                        table.add_hline()
                        if r[0]=="1":
                            table.add_row("", "[B]пр",Command('underline',b) , "")
                            r=bin(int(r[0:8],2)+int(b,2))[2:][-8:]+r[8:]
                        else:
                            
                            table.add_row("", "[B]доп",Command('underline',_b) , "")
                            r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                        table.add_row("", "R8", r[0:8], r[8:])
                
                    table.add_hline()

                    is_sub=r[0]==a[0]
            doc.append("\n")
            if r[8:][0]==_a[0]:
                _r=r[8:].replace("1","O",r[8:].count("1")-1)
                _r=(_r.replace("0","Z")[:_r.find("1")]+_r[_r.find("1"):]).replace("O","0").replace("Z","1")
                doc.append("\nCдоп = "+r[8:]+"= -"+str(int(_r,2)))
                doc.append("\nОстаток = "+r[:8]+" = "+str(int(r[:8],2)))
            else:
                doc.append("\nCдоп = "+r[8:]+"= "+str(int(_r,2)))
                doc.append("\nОстаток = "+r[:8]+" = "+str(int(r[:8],2)))

        with doc.create(Subsection('Делимое положительное, делитель положительный(A>0,B>0):', False)):
            with doc.create(Tabular('|c|c|c|c|')) as table:
                table.add_hline()
                table.add_row(("№ шага", "Операнды и действия", "Делимое и остаток", "Делимое и остаток"))
                table.add_row(("", "", "(старшие разряды)", "(младшие разряды),"))
                table.add_row(("", "", "", "частное"))
                table.add_hline()
                table.add_row(1, 2, 3, 4)
                table.add_hline()
                table.add_row(0, "[A]пр", _a[0:8], _a[8:])
                table.add_hline()
                r=_a
                r=r[1:17]+"0"
                table.add_row(1, "[A]пр<-", r[0:8], r[8:])
                table.add_row("", "[-B]доп",Command('underline',b) , "")
                r=bin(int(r[0:8],2)+int(b,2))[2:][-8:]+r[8:]
                table.add_row("", "R1", r[0:8], r[8:15]+"|"+r[15:])
                table.add_hline()
                is_sub=True
                i=0
                for i in range(7):
                    r=r[1:17]+"0"
                    if i!=6:
                        table.add_row(str(i+2), "R"+str(i+1)+"<-", r[0:8], r[8:14-i]+"|"+r[14-i:])
                    else:
                        table.add_row("8", "R8", r[0:8], r[8:])
                        
                    if not(is_sub):
                        table.add_row("", "[-B]пр",Command('underline',b) , "")
                        r=bin(int(r[0:8],2)+int(b,2))[2:][-8:]+r[8:]
                    else:
                        table.add_row("", "[B]доп",Command('underline',_b) , "")
                        r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                    r=r[:-1]+r[0]
                    if i!=6:
                        table.add_row("", "R"+str(i+2), r[0:8], r[8:14-i]+"|"+r[14-i:])
                    else:
                        table.add_row("", "R8", r[0:8], r[8:])
                        table.add_hline()
                        if r[0]=="1":
                            table.add_row("", "[B]пр",Command('underline',_b) , "")
                            r=bin(int(r[0:8],2)+int(b,2))[2:][-8:]+r[8:]
                        else:
                            
                            table.add_row("", "[B]доп",Command('underline',_b) , "")
                            r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                        table.add_row("", "R8", r[0:8], r[8:])
                
                    table.add_hline()

                    is_sub=r[0]==a[0]
            doc.append("\n")
            if r[8:][0]==_a[0]:
                _r=r[8:].replace("1","O",r[8:].count("1")-1)
                _r=(_r.replace("0","Z")[:_r.find("1")]+_r[_r.find("1"):]).replace("O","0").replace("Z","1")
                doc.append("\nCдоп = "+r[8:]+"= -"+str(int(_r,2)))
                doc.append("\nОстаток = "+r[:8]+" = "+str(int(r[:8],2)))
            else:
                doc.append("\nCдоп = "+r[8:]+"= "+str(int(_r,2)))
                doc.append("\nОстаток = "+r[:8]+" = "+str(int(r[:8],2)))
        
                
with doc.create(Subsection('Делимое отрицательное, делитель положительный(A<0,B>0):', False)):
            with doc.create(Tabular('|c|c|c|c|')) as table:
                table.add_hline()
                table.add_row(("№ шага", "Операнды и действия", "Делимое и остаток", "Делимое и остаток"))
                table.add_row(("", "", "(старшие разряды)", "(младшие разряды),"))
                table.add_row(("", "", "", "частное"))
                table.add_hline()
                table.add_row(1, 2, 3, 4)
                table.add_hline()
                table.add_row(0, "[A]доп", _a[0:8], _a[8:])
                table.add_hline()
                table.add_row(1, "[-B]доп", Command('underline','1'*8), Command('underline',_b))
                r=bin(int('1'*8+_b,2)+int(a,2))[2:][1:17]
                #print(r)
                table.add_row("", "R1", r[0:8], r[8:])
                r=r[1:17]+"0"
                #print(r)
                table.add_row("", "R1<-", r[0:8], "")
                table.add_row("", "[-B]доп",Command('underline',_b), "")
                r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                table.add_row("", "R1", r[0:8], r[8:15]+"|"+r[15])
                table.add_row("", "", r[0:8], r[8:15]+"|"+r[0])
                r=r[:-1]+r[0]
                table.add_hline()
                is_sub=r[0]==a[0]
                for i in range(7):
                    r=r[1:17]+"0"
                    if i!=6:
                        table.add_row(str(i+2), "R"+str(i+1)+"<-", r[0:8], r[8:14-i]+"|"+r[14-i:])
                    else:
                        table.add_row("8", "R7<-", r[0:8], r[8:])
                        
                    if not(is_sub):
                        table.add_row("", "[-B]пр",Command('underline',b) , "")
                        r=bin(int(r[0:8],2)+int(b,2))[2:][-8:]+r[8:]
                    else:
                        table.add_row("", "[-B]доп",Command('underline',_b) , "")
                        r=bin(int(r[0:8],2)+int(_b,2))[2:][-8:]+r[8:]
                    r=r[:-1]+r[0]
                    if i!=6:
                        table.add_row("", "R"+str(i+2), r[0:8], r[8:14-i]+"|"+r[14-i:])
                    else:
                        table.add_row("", "R8", r[0:8], r[8:])
                    table.add_hline()

                    is_sub=r[0]==a[0]
            doc.append("\n")
            if r[8:][0]=="1":
                _r=r[8:].replace("1","O",r[8:].count("1")-1)
                _r=(_r.replace("0","Z")[:_r.find("1")]+_r[_r.find("1"):]).replace("O","0").replace("Z","1")
                doc.append("\nCдоп = "+r[8:]+"= -"+str(int(_r,2)))
                doc.append("\nОстаток = "+r[:8]+" = "+str(int(r[:8],2)))
            else:
                doc.append("\nCдоп = "+r[8:]+"= "+str(int(_r,2)))
                doc.append("\nОстаток = "+r[:8]+" = "+str(int(r[:8],2)))
   
        
doc.generate_pdf('full', compiler='/opt/local/bin/pdflatex')
