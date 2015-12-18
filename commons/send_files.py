# http://stackoverflow.com/questions/9382045/send-a-file-through-sockets-in-python
import socket
import struct
import threading


class SendFiles():
    """
    File sender bound to certain remote adr and port.
    """
    def __init__(self, remote_adr, remote_port=11112):
        """

        :param remote_adr: adr to connect to
        :param remote_port: port to connect to
        :return:
        """
        self.__remote_adr = remote_adr
        self.__remote_port = remote_port
        self.__queue = []
        self.__socket = None
        self.__t = None

    def transfer_file(self, name, path):
        """
        Transfer a file using this file sender. Uses an own thread, so returns immediately.
        :param name: name of the file to send
        :param path: full path to the file
        :return:
        """
        global __t
        self.__queue.append((name, path))
        if __t is not None:
            if not __t.is_alive():
                __t = threading.Thread(target=self.__transfer)
                __t.daemon = True
                __t.start()

    def __transfer(self):
        """
        transfers all files in the queue and returns after all files are transfered.
        :return:
        """

        while self.__queue:
            self.__socket = socket.socket()
            self.__socket.connect((self.__remote_adr, self.__remote_port))

            id, path = self.__queue.pop()
            byte_length = struct.pack('!I', len(str(id)))

            self.__socket.send(byte_length)
            self.__socket.send(id)

            with open(path, 'rb') as to_send:
                chunk = to_send.recv(4096)
                while to_send:
                    self.__socket.send(to_send)
                    chunk = to_send.recv(4096)

            self.__socket.close()
