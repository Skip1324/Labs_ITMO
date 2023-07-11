import time
start_time = time.perf_counter()
import yaml
from dict2xml import dict2xml
with open('timetable.yaml','r') as yaml_file:
    ouryaml = yaml.safe_load(yaml_file)
new_xml ="<?xml version= \"1.0\" encoding=\"UTF8\" ?> \n" + dict2xml(ouryaml)
file = open("task1_xml.xml", "w")
file.write(new_xml)
file.close()
print(time.perf_counter() - start_time)



















# with open('timetable.yaml') as f:
#     templates = yaml.safe_load(f)
# print(type(templates))
# xml = dicttoxml(templates)











