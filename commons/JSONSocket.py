import json
import logging

import struct


class JsonSocket:
    """
    JsonSocket class is designed to provide the possibility of sending JSON objects via sockets.
    It takes automatically care of the the low-level networking stuff.
    A ConnectionError is raised if an invalid message is received,
    more specific if the size contained in the header is invalid.
    """
    def __init__(self, socket, con_key='no connection key set'):
        """

        :type socket: socket
        :type con_key: string
        """
        self.__socket = socket
        self.__con_key = con_key

    def check_for_new_msg(self):
        #TODO detect socket close, gracefully and not gracefully
        """

        Check this socket for a new message.
        :return: Either returns the decoded msg as decoded dict or returns None.
        """
        logging.debug('Checking connection %s for new msgs.' % str(self.__con_key))
        l_data = self.__socket.recv(4) # TODO check for correctness (if in any case exactly 4 bytes are received)
        if len(l_data) > 0:
            logging.debug('received %s bytes' % str(len(l_data)))
            #'!I' -> using network byteorder
            l = struct.unpack('!I', l_data)[0] # The return value of unpack is always a tuple

            if l > 0:
                logging.debug('Receiving message of length %s' % l)
                msg = self.__socket.recv(l)
                return self.__receive_message(msg)
            else:
                raise ConnectionError('Received a msg of invalid size')

        else:
            logging.debug('No new messages')

        return None

    def __receive_message(self, msg):
        """
        Converts bytes in msg, to valid json object, and redirects filtered data to correct handling function
        :param msg: plain bytes of the received msg
        :return: decoded json msg as dict
        """
        json_msg = json.loads(msg.decode()) #should return a dict
        logging.debug('Decoded the following json %s' % json_msg)

        return json_msg

    def send_msg(self, msg_to_send):
        """
        Send a msg object via this JSON Socket.
        :param msg_to_send: a msg object
        :return: void
        """
        length, packed_msg = msg_to_send.pack()

        logging.debug('Sending msg. Length: %s, msg: %s' %(length, packed_msg))
        byte_length = struct.pack('!I', length) #'!I' -> using network byteorder
        print(byte_length)
        self.__socket.send(byte_length)
        self.__socket.send(packed_msg)