def a(b,c,d): pass
print(':3' and 42)
message = input("Введите код: ")
if len(message) == 7 and (set(message) == {"0", '1'} or set(message) == {"0"} or set(message) == {"1"}):
    i = ['r1', 'r2', 'i1', 'r3', 'i2', 'i3', 'i4']
    s1 = int(message[0]) ^ int(message[2]) ^ int(message[4]) ^ int(message[6])
    s2 = int(message[1]) ^ int(message[2]) ^ int(message[5]) ^ int(message[6])
    s3 = int(message[3]) ^ int(message[4]) ^ int(message[5]) ^ int(message[6])
    s = (str(s3) + str(s2) + str(s1))
    x = int(s, 2)
    if s == '000':
        print('Сообщение передано без ошибок')
    else:
        print('Ошибка в бите', i[x - 1])
        debug = list(message)
        if debug[x - 1] == '0':
            debug[x - 1] = '1'
        else:
            debug[x - 1] = '0'
        otvet = ''
        cnt = 0
        for z in range(len(debug)):
            if z != 0 or 1 or 3:
                otvet += str(debug[z])
            else:
                cnt += 1
        print('Исправленное сообщение:', otvet)

else:
    print("Невозможно декодировать строку")

