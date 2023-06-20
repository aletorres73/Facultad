# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import numpy as np
import scipy.signal as sig
import matplotlib as mpl
from matplotlib import pyplot as plt

from pytc2.sistemas_lineales import analyze_sys, pretty_print_lti, tf2sos_analog, pretty_print_SOS

#w0 = 2* np.pi*22*10**3
w0 = 138*10**3

num1 = [w0*0.0335,0]
den1 = [1,w0*0.335,w0**2]

num2 = [w0*0.1368*2.045,0]
den2 = [1,w0*0.1368,0.7758*w0]

num3 = [w0*0.181*2.045,0]
den3 = [1,w0*0.181,w0*2.157]

# Multiplicación de los numeradores
numerador = np.polymul(np.polymul(num1, num2), num3)

# Multiplicación de los denominadores
denominador = np.polymul(np.polymul(den1, den2), den3)

print("Numerador:", numerador)
print("Denominador:", denominador)

Hs =sig.TransferFunction(numerador,denominador)
Hs

plt.figure(1)
plt.close(1)

fig_sz_x = 13
fig_sz_y = 7
fig_dpi = 80 # dpi
fig_font_size = 16

mpl.rcParams['figure.figsize'] = (fig_sz_x, fig_sz_y)
mpl.rcParams['figure.dpi'] = fig_dpi
plt.rcParams.update({'font.size':fig_font_size})

analyze_sys(Hs,['Función transferencia'])