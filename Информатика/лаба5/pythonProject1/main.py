import matplotlib.pyplot as plt
import csv

file_csv = open("lab5.csv", newline='')
s = csv.reader(file_csv)
date = set()
data = [[] for i in range(16)]
lbl = []
k = -4
count = 0

for row in s:
    if count > 0:
        if row[2] not in date:
            date.add(row[2])
            k += 4
            lbl += ["opn", "mx", "mn", "cls"]
        data[k] += [float(row[4])]
        data[k + 1] += [float(row[5])]
        data[k + 2] += [float(row[6])]
        data[k + 3] += [float(row[7])]
    count += 1

plt.boxplot(data, labels=lbl)
plt.xlabel('11/09/18              11/10/18             11/11/18               11/12/18')
plt.show()
