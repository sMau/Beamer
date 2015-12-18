import logging
import socket
import struct
import threading
import time

s = socket.socket()


def init(listen_adr='127.0.0.1', listen_port=11112):
    global s
    s.bind((listen_adr, listen_port))
    s.listen()

    t = threading.Thread(target=__listen_for_new_files)
    t.daemon = True
    t.start()


def __listen_for_new_files():
    name = None
    while True:
        time.sleep(0.1)
        sc, address = s.accept()

        l_data = sc.recv(4)
        if len(l_data) > 0:
            logging.debug('received name of %s bytes' % str(len(l_data)))
            # '!I' -> using network byteorder
            l = struct.unpack('!I', l_data)[0]  # The return value of unpack is always a tuple

            if l > 0:
                logging.debug('Receiving name of length %s' % l)
                name = sc.recv(l)

            else:
                raise ConnectionError('Received a name of invalid size')

        with open(name, 'wb') as f:
            l = sc.recv(4096)
            while l:
                f.write(l)
                l = sc.recv(4096)
            sc.close()