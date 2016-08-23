import random
import numpy as np
import matplotlib.pyplot as plt
import time
import copy
import sys
import Queue
# Grafos




def randMatrPosWGraph(nNodes, sparseFactor, maxWeight=50.):
    matrix = np.zeros(shape=(nNodes, nNodes))
    matrix[:] = float('inf')

    for i in range(nNodes):
        for j in range(nNodes):
            if i == j:
                matrix[i,i] = 0
            else:
                if random.random() <= sparseFactor:
                    matrix[i, j] = random.randint(0, maxWeight)
    return matrix

def cuentaRamas(mG):
    i = 0
    for x in range(len(mG)):
        for y in range(len(mG[0])):
            if mG[x][y] != float('inf') and x != y:
                i += 1
    return i


def fromAdjM2Dict(mG):
    dict = {}
    i = 0
    for fila in mG:
        j = 0
        list = []

        for columna in fila:
            if columna != float('inf') and i != j:
                list.append((columna, j))
            j += 1
        dict.update({i: list})
        i += 1
    return dict


def sizeDict(dG):
    max = -1
    for elem in dG:
        if elem > max:
            max = elem
        for list in dG[elem]:
            if list[1] > max:
                max = list[1]
    return max + 1


def fromDict2AdjM(dG):
    size = sizeDict(dG)
    mat = np.zeros(shape=(size, size))
    j = 0
    for elem in dG:
        list = []
        i = 0
        for x in dG[elem]:

            while i <= x[1]:
                if x[1] == i:
                    list.append(x[0])
                else:
                    list.append(float('inf'))
                i += 1

        while (len(list) < size):
            list.append(float('inf'))
        mat[j] = np.matrix(list)
        mat[j][j] = 0;
        j += 1


    return mat



def dijkstraD(dG, u):
    size = sizeDict(dG)
    v = []
    p = []
    d = []

    for x in range(size):
        v.append(False)
        p.append(None)
        d.append(float('inf'))

    q = Queue.PriorityQueue()
    d[u] = 0
    q.put((0, u))

    while not q.empty():
        # w[0] -> coste
        # w[1] -> indice vertice
        w = q.get()
        if v[w[1]] == False:
            v[w[1]] = True
            # por cada vertice z adyacente a w
            for z in dG[w[1]]:
                # z[0] == coste(d[w[1]], z)
                # z[1] == indice vertice
                if d[z[1]] > d[w[1]] + z[0]:
                    d[z[1]] = d[w[1]] + z[0]
                    p[z[1]] = w[1]
                    q.put((d[z[1]], z[1]))

    return p, d


def dijkstraM(mG, u):
    size = len(mG)
    v = []
    p = []
    d = []

    for x in range(size):
        v.append(False)
        p.append(None)
        d.append(float('inf'))

    q = Queue.PriorityQueue()
    d[u] = 0
    q.put((0, u))

    while not q.empty():
        # w[0] -> coste
        # w[1] -> indice vertice

        w = q.get()
        if v[w[1]] == False:
            v[w[1]] = True
            i = 0
            # por cada vertice z adyacente a w
            for z in mG[w[1]]:

                if d[i] > d[w[1]] + z:
                    d[i] = d[w[1]] + z
                    p[i] = w[1]
                    q.put((d[i], i))
                i += 1  # indice del vertice explorando

    return p, d


def timeDijkstraM(nGraphs, nNodesIni, nNodesFin, step,sparseFactor =0.25):
    timelist = []

    for i in range(nNodesIni, nNodesFin, step):
        time3 =0
        for x in range(nGraphs):
            
            # print("permutacion" + repr(i) + " "+  repr(x))
            m=randMatrPosWGraph(i,sparseFactor)
            time1 = time.clock()
            dijkstraM(m,0)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3/ nGraphs)
    return timelist

def timeDijkstraD(nGraphs, nNodesIni, nNodesFin, step,sparseFactor =0.25):
    timelist = []

    for i in range(nNodesIni, nNodesFin, step):
        time3=0
        for x in range(nGraphs):
            # print("permutacion" + repr(i) + " "+  repr(x))
            d = fromAdjM2Dict(randMatrPosWGraph(i,sparseFactor))
            time1 = time.clock()
            dijkstraD(d,0)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3/ nGraphs)
    return timelist

# Metodos cutres, seria interesante contrastar su eficiencia

def dijkstraMCutre(mG, u):
    return dijkstraD(fromAdjM2Dict(mG), u)


def dijkstraDCutre(dG, u):
    return dijkstraM(fromDict2AdjM(dG), u)

def fitPlotDij(nGraphs, nNodesIni, nNodesFin, step,sparseFactor =0.25):
    times1 =timeDijkstraM(nGraphs, nNodesIni, nNodesFin, step,sparseFactor)

    times2 =timeDijkstraD(nGraphs, nNodesIni, nNodesFin, step,sparseFactor)

    x = []
    for z in range(nNodesIni, nNodesFin, step):
        x.append(z)

    ydata1 = np.polyfit(x, times1, 2)
    f1 = np.poly1d(ydata1)
    yfit1 = np.linspace(nNodesIni, nNodesFin,nNodesFin - nNodesIni / step)

    ydata2 = np.polyfit(x, times2, 2)
    f2 = np.poly1d(ydata2)
    yfit2 = np.linspace(nNodesIni, nNodesFin,nNodesFin - nNodesIni / step)

    plt.plot(x,times1,"b.", label="valor real M")
    plt.plot(yfit1, f1(yfit1),"r-", label='matriz')
    plt.plot(x,times2, "r.", label="valor real D")
    plt.plot(yfit2,f2(yfit2),"b-", label ='diccionario')
    plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3,ncol=2, mode="expand", borderaxespad=0.)
    plt.show()

    return


#Dijkstra vs Floyd-Warshall

def dijkstraMAllPairs(mG):

    size = len(mG)
    mat = np.zeros(shape=(size, size))
    for x in range (size):
        mat[x] = dijkstraM(mG, x)[1]
    return mat


def floydWarshall(d):
    N = len(d)
    #N = len(mG)
    #d = copy.copy(mG)

    for k in range(N):
        for i in range(N):
            for j in range(N):
                if d[i][j] > d[i][k] + d[k][j]:
                    d[i][j] = d[i][k] + d[k][j]
    return d

def timeDijkstraMAllPairs(nGraphs, nNodesIni, nNodesFin, step, sparseFactor):

    timelist = []
    for i in range (nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):

            m = randMatrPosWGraph(i, sparseFactor)
            time1 = time.clock()
            dijkstraMAllPairs(m)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3/nGraphs)

    return timelist

def timeFloydWarshall(nGraphs, nNodesIni, nNodesFin, step, sparseFactor):

    timelist = []
    for i in range (nNodesIni, nNodesFin, step):
        time3 = 0
        for x in range(nGraphs):

            m = randMatrPosWGraph(i, sparseFactor)
            time1 = time.clock()
            floydWarshall(m)
            time2 = time.clock()
            time3 += time2 - time1
        timelist.append(time3/nGraphs)

    return timelist

def fitPlotFW(nGraphs, nNodesIni, nNodesFin, step,sparseFactor =0.25):

    times1 = timeDijkstraMAllPairs(nGraphs, nNodesIni, nNodesFin, step, sparseFactor)
    times2 = timeFloydWarshall(nGraphs, nNodesIni, nNodesFin, step, sparseFactor)

    print ("tiempios dijkstraMAllPairs")
    print (times1)
    print("tiempos FloydWarshall")
    print (times2)
    x = []
    for z in range(nNodesIni, nNodesFin, step):
        x.append(z)

    ydata1 = np.polyfit(x, times1, 2)
    print ("DJ time")
    print (ydata1)
    f1 = np.poly1d(ydata1)
    yfit1 = np.linspace(nNodesIni, nNodesFin,nNodesFin - nNodesIni / step)

    ydata2 = np.polyfit(x, times2, 3)
    print ("Fw time")
    print (ydata2)
    f2 = np.poly1d(ydata2)
    yfit2 = np.linspace(nNodesIni, nNodesFin,nNodesFin - nNodesIni / step)

    plt.plot(x,times1,"b.", label="valor real Dijkstra")
    plt.plot(yfit1, f1(yfit1),"r-", label='Dijkstra')
    plt.plot(x,times2, "r.", label="valor real FloydWarshall")
    plt.plot(yfit2,f2(yfit2),"b-", label ='FloydWarshall')
    plt.legend(bbox_to_anchor=(0., 1.02, 1., .102), loc=3,ncol=2, mode="expand", borderaxespad=0.)
    plt.show()

    return


#Trivial Graph Format


def dG2TGF(dG, fName):

    file = open(fName, "w")
    edges = ""

    for x in dG:
        file.write(str(x) + '\n')
        for value in dG[x]:
            edges += str(x) + ' ' + str(value[1]) + ' ' + str(value[0]) + '\n'

    file.write('#\n' + edges)

    #for x in dG:
    #    for value in dG[x]:
    #        file.write(str(x) + ' ' + str(value[1]) + ' ' + str(value[0]) + '\n')
    return


def TGF2dG(fName):
    dict = {}
    file = open(fName, "r")
    line = file.readline()[:-1]
    #Leer los vertices hasta encontrar '#'

    while line != '#\r':
        dict.update({int(line): []})
        line = file.readline()[:-1]

    #Leer la parte de las aristas y los pesos
    for line in file:
        #Dividir la linea en las 3 componentes
        parts = line.split()
        start = int(parts[0])
        end = int(parts[1])
        weight = float(parts[2])

        dict[start].append((weight, end))

    return dict

dG = TGF2dG("dg.tgf")
#m = randMatrPosWGraph(10, 1, 1.5)
print dijkstraD(dG, 0)


#print sizeDict(Dg)


#if len(sys.argv) != 2:
#    print("Ejecucion python grafos11.py fichGrafo.txt")
#    #fitPlotFW(2,100,1000 ,5,sparseFactor =0.80)
#    #exit()
#
#fName = sys.argv[1]
#dG= TGF2dG(fName)
#mG = fromDict2AdjM(dG)
#
#dijk = dijkstraMAllPairs(mG)
#FW = floydWarshall(mG)
#
#if len(dijk)/2 > 8:
#    for x in range (0,7,1):
#        list = []
#        for y in range (0,7,1):
#            list.append(dijk[x][y])
#        print(list)
#else:
#    for x in range(0,len(dijk)/2,1):
#        list = []
#        for y in range (0,len(dijk)/2,1):
#            list.append(dijk[x][y])
#        print(list)
#
#if len(FW)/2 >8:
#    for x in range (0,7,1):
#        list = []
#        for y in range (0,7,1):
#            list.append(FW[x][y])
#        print(list)
#
#else:
#    for x in range(0,len(FW)/2,1):
#        list = []
#        for y in range (0,len(FW)/2,1):
#            list.append(FW[x][y])
#        print(list)
#