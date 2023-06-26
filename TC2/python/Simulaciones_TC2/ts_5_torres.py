# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import numpy as np
import scipy.signal as sig
import matplotlib as mpl
from matplotlib import pyplot as plt

from pytc2.sistemas_lineales import analyze_sys, pretty_print_lti, tf2sos_analog, pretty_print_SOS, bodePlot

plt.figure(1)
plt.close(1)

fig_sz_x = 13
fig_sz_y = 7
fig_dpi = 80 # dpi
fig_font_size = 16

mpl.rcParams['figure.figsize'] = (fig_sz_x, fig_sz_y)
mpl.rcParams['figure.dpi'] = fig_dpi
plt.rcParams.update({'font.size':fig_font_size})

def ordenDelFiltro(alphaRef, epsilon, ws):
    for n in range(5):
        arg   = 1+epsilon**2*ws**(2*n)
        alpha = 10*np.log10(arg)
        if alpha >= alphaRef:
            return n,alpha
        
def seleccionarOrden(**ordenes):
    lista = ordenes.values()
    return max(lista)
        
f0  = 22e3
fs1 = 17e3
fs2 = 36e3

Q =5
B = 1/Q

w0    = 2*np.pi*f0
ws1   = 2*np.pi*fs1
ws2   = 2*np.pi*fs2    

wp1_n = 0.905 
wp2_n = 1.105

ws1_n = ws1/w0
ws2_n = ws2/w0

W0_n  = w0/w0
Ws1_n = Q*(ws1_n**2-W0_n)/ws1_n
Ws2_n = Q*(ws2_n**2-W0_n)/ws2_n

alphaMax  = 0.5
alphaMin1 = 16
alphaMin2 = 24

epsilon = np.sqrt(10**(alphaMax/10)-1)

n1,alpha1 = ordenDelFiltro(alphaMin1, epsilon, Ws1_n)
n2,alpha2 = ordenDelFiltro(alphaMin2, epsilon, Ws2_n)

# n = seleccionarOrden(ordenes=[n1,n2] , atenuacion=[alpha1,alpha2] )

print("Frecuencias Normalizadas:")
print((f"W0 = {W0_n}\nWs1= {Ws1_n}\nWs1= {Ws2_n}\n"))
print("Ordenes del filro:")
print(f"n1= {n1}\nn2= {n2}")

z,p,k= sig.cheb1ap(n2,alphaMax)
Hs_n = sig.zpk2tf(z, p, k)
HS_n = sig.TransferFunction(Hs_n[0], Hs_n[1])

numerador = [0.7157]
denominador = [1,1.2529,1.5348,0.7157]
Hs =sig.TransferFunction(numerador,denominador)

# analyze_sys([Hs,HS_n], sys_name=['Pasabajo normalizado calculo directo','Funciones de python'])

raices =np.roots(denominador)
print("Polos de la transferencia:\n",raices)

print("Denominador del módulo cuadrático:\n",np.poly(raices[0:2]))
raice2 = np.roots(np.poly(raices[0:2]))
print("Polos del módulo cuadrático de la transferencia:\n",raice2)

num1BP = [0.1253,0]
num2BP = [0.0457,0,0]

den1BP = [1,0.1253,1]
den2BP = [1,0.1258,2.047,0.6264,1]

numBP = np.polymul(num1BP, num2BP)
denBP = np.polymul(den1BP, den2BP)

# HBP_n = sig.TransferFunction(numBP, denBP)
# analyze_sys(HBP_n, "Filtro Pasa Banda normalizado")

zBP, pBP, kBP = sig.lp2bp_zpk(z, p, k, 1, 0.2)
HBP_N = sig.zpk2tf(zBP, pBP, kBP)
HBP_n = sig.TransferFunction(HBP_N[0],HBP_N[1])
analyze_sys(HBP_n, "función pasabanda")