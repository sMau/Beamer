#http://stackoverflow.com/questions/9382045/send-a-file-through-sockets-in-python
import socket
import struct
import threading

from client import data

__queue = []
__socket = None
__t = None


def transfer_file(name, path):
    global __t
    __queue.append((name, path))
    if __t is not None:
        if not __t.is_alive():
            __t = threading.Thread(target=__transfer)
            __t.daemon = True
            __t.start()


def __transfer():
    global __socket

    while __queue:
        __socket = socket.socket()
        __socket.connect((data.host, data.file_port))

        id, path = __queue.pop()
        byte_length = struct.pack('!I', len(str(id)))

        __socket.send(byte_length)
        __socket.send(id)

        with open(path, 'rb') as to_send:
            chunk = to_send.recv(4096)
            while to_send:
                __socket.send(to_send)
                chunk = to_send.recv(4096)

        __socket.close()
