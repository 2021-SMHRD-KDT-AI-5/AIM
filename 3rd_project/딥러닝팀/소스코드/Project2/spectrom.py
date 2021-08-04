import numpy as np
import pyaudio as pa
import struct
import matplotlib.pyplot as plt


CHUNK = 1024 * 2
FORMAT = pa.paInt16
CHANNELS = 1
RATE = 44100 

p = pa.PyAudio()
stream = p.open(
    format= FORMAT,
    channels= CHANNELS,
    rate= RATE,
    input = True,
    output= True,
    frames_per_buffer=CHUNK

)


fig, ax = plt.subplots()
x = np.arrange(0, 2*CHUNK,2)
line, = ax.plot(x, np.random.rand(CHUNK), 'r')
ax.set_ylim(-60000, 60000)
ax.set_xlim(0, CHUNK)
fig.show()

while 1:
    data = pa.Stream.read(CHUNK)
    dataInt = struct.unpack(str(CHUNK) + 'h', data) 
    line.set_ydata(dataInt)
    fig.canvas.draw()
    fig.canvas.flush_ebents()