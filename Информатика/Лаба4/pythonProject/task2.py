import re
import time
start_time = time.perf_counter()
tmp = open('timetable.yaml',).read().splitlines()
output = "<?xml version= \"1.0\" encoding=\"UTF8\" ?> \n"
end_tag = []
spaces = 0
print(tmp)
for i in tmp:
    now_spaces = i.count("  ")
    if spaces > now_spaces:
        output += end_tag[-1]
        end_tag.pop(-1)
    if i[-1] == ":":
        line = re.sub(":", "", i)
        line = re.sub(" ", "", line)
        output += "<" + line + ">" + "\n"
        end_tag.append("</" + line + ">\n")
    else:
        double_index = re.search(":", i).start()
        tag = i[:double_index]
        tag = re.sub(" ", "", tag)
        line = i[double_index+2:]
        output += "<"+tag+">"+line+"</"+tag+">" + "\n"
    spaces = now_spaces
output += "".join(end_tag[::-1])
file = open("task2_xml.xml", "w")
file.write(output)
file.close()
print(time.perf_counter() - start_time)






