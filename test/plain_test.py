import logging

from client import log
from client.gui import ClientGUIImpl
import client.controller as client_ctrl
from commons import recv_files, send_files
from server.gui import ServerGUIImpl
import server.controller as server_ctrl

if __name__ == '__main__':
    recv_files.init()
    send = send_files.SendFiles('127.0.0.1')
    send.transfer_file('test', '/Users/samuel/Documents/0001-ARV-2016-9825.PDF')