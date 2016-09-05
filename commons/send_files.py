# http://stackoverflow.com/questions/9382045/send-a-file-through-sockets-in-python
import logging
import socket
import struct
import threading


logger = None


class SendFiles:
    """
    File sender bound to certain remote adr and port.
    """
    def __init__(self, remote_adr, logger_name='', remote_port=11112):
        """
        :param logger_name: name of the logger to use in this module
        :param remote_adr: adr to connect to
        :param remote_port: port to connect to
        :return: void
        """
        global logger

        self.__remote_adr = remote_adr
        self.__remote_port = remote_port
        self.__queue = []
        self.__socket = None
        self.__t = None

        logger = logging.getLogger(logger_name)

    def transfer_file(self, name, path):
        """
        Transfer a file using this file sender. Uses an own thread, so returns immediately.
        :param name: name of the file to send
        :param path: full path to the file
        :return: void
        """

        logger.debug('Transfering file %s' % path)
        self.__queue.append((name, path))
        if self.__t is None:
            self.__t = threading.Thread(target=self.__transfer)
            self.__t.start()
        else:
            if not self.__t.is_alive():
                self.__t = threading.Thread(target=self.__transfer)
                self.__t.start()


    def __transfer(self):
        """
        transfers all files in the queue and returns after all files are transfered.
        :return: void
        """

        while self.__queue:
            self.__socket = socket.socket()
            self.__socket.connect((self.__remote_adr, self.__remote_port))

            name, path = self.__queue.pop()
            byte_length = struct.pack('!I', len(str(name)))

            self.__socket.send(byte_length)
            self.__socket.send(name.encode('utf-8'))

            with open(path, 'rb') as to_send:
                chunk = to_send.read(4096)
                while chunk:
                    self.__socket.send(chunk)
                    chunk = to_send.read(4096)

            self.__socket.close()
