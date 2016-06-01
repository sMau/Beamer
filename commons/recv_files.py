import socket
import struct
import threading
import time

import os

from client import log
from utils.observer import Observer

_s = socket.socket()

_stopper = False

_observers = []

_save_path = ''


def init(path, listen_adr='127.0.0.1', listen_port=11112):
    """
    Call this init method to start a thread listening for new file transfers.
    :param listen_adr: adr to listen on
    :param listen_port: port to listen on
    :return:
    """
    global _s, _save_path

    if not os.path.exists(path):
        os.makedirs(path)

    _save_path = path

    _s.bind((listen_adr, listen_port))
    _s.listen()

    t = threading.Thread(target=__listen_for_new_files)
    t.start()


def stop_file_receiver():
    global _stopper
    _stopper = True
    _s.close()


def subscribe(o):
    assert type(o) is Observer, 'passed object is not an observer'
    log.d('Subscriber added to the file receiver')
    if o not in _observers:
        _observers.append(o)


def unsubscribe(o):
    assert type(o) is Observer, 'passed object is not an observer'
    if o in _observers:
        _observers.remove(o)


def __transfer_finished(*args):
    log.i('File transfer finished: {}, path: {}'.format(*args))
    for o in _observers:
        o.notify(*args)


def is_stopped():
    """
    Check if the receiver thread is running.
    :return: true if stopped, false if not
    """
    return _stopper


def __listen_for_new_files():
    """
    Listening for new incoming file transfer connections.
    :return: void
    """
    log.d('Listening for new files now.')
    unique_id = None
    while not _stopper:
        time.sleep(0.1)
        sc, address = _s.accept()

        l_data = sc.recv(4)
        if len(l_data) > 0:
            log.d('received id of %s bytes' % str(len(l_data)))
            # '!I' -> using network byteorder
            l = struct.unpack('!I', l_data)[0]  # The return value of unpack is always a tuple

            if l > 0:
                log.d('Receiving id of length %s' % l)
                unique_id = sc.recv(l)
                unique_id = unique_id.decode('utf-8')
                log.d('Filname: %s' % unique_id)
            else:
                raise ConnectionError('Received a id of invalid size')

        file_path = _save_path + '/' + unique_id
        with open(file_path, 'wb') as f:
            l = sc.recv(4096)
            while l:
                f.write(l)
                l = sc.recv(4096)
            __transfer_finished(unique_id, file_path)
            sc.close()
