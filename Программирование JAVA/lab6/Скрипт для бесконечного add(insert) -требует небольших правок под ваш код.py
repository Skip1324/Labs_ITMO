import random
import string
f = input("File:")
ids = int(input("Id start:"))
h = int(input("how many:"))
letters = string.ascii_lowercase
file=open(f,"w")
for i in range(h):
    lines=""
    lines+="insert " + str(ids) + "\n"
    ids+=1
    lines+=''.join(random.choice(letters) for k in range(10))+"\n"
    lines+=str(random.randint(1, 50))+"\n"
    lines+=str(random.uniform(1, 50))+"\n"
    lines+=str(random.randint(1,50))+"\n"
    lines+=str(random.randint(1,50))+"\n"
    lines+=str(random.randint(1,50))+"\n"
    lines+=str(random.randint(1,50))+"\n"
    lines+=["TUNDRA","RAIN_FOREST","OCEANIC","MEDITERRANIAN"][random.randint(0,2)]+"\n"
    lines += ["LOW", "HIGH", "ULTRA_HIGH", "VERY_HIGH"][random.randint(0, 2)] + "\n"
    lines+=''.join(random.choice(letters) for k in range(10)) + "\n"
    file.write(lines)
file.close()