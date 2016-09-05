import socket
import threading

import os

import client.data as data
from client import log
from commons import msg, send_files
from commons.json_socket import JsonSocket
from commons.msg import Msg


json_connection = None
file_send_connection = None
gui = None


def tear_up(c_gui):
    """
    Start up the client controller including connection init.
    :param c_gui: The current client gui object
    :return: void
    """
    global gui
    gui = c_gui
    __connect()


def __connect():
    """
    init connection to remote server. Server adress and port is taken from the data module.
    :return: void
    """
    global json_connection
    tmp_sock = socket.socket()
    tmp_sock.connect((data.host, data.control_port))

    json_connection = JsonSocket(tmp_sock)

    con_msg = Msg(data.login_name, cmd_id=msg.Type.CMD_CONNECT)


    json_connection.send(con_msg)
    # http://cpiekarski.com/2011/05/09/super-easy-python-json-client-server/

    t = threading.Thread(target=__check_for_new_msgs)
    t.daemon = True
    t.start()


def __check_for_new_msgs():
    """
    Infinite loop checking the control connection periodically for new messages.
    :return: void
    """
    while True:
        msg_dict = json_connection.check_for_new_msg()
        if msg_dict is not None:
            cmd = int(msg_dict['cmd_id'])
            ack = int(msg_dict['ack'])
            if cmd == msg.Type.CMD_CONNECT and ack == 1:
                __login_success()
            elif cmd == msg.Type.CMD_UNDEFINED:
                pass
            else:
                pass

def add_files(files):
    files = filter_files(files)
    for f in files:
        add_file(f)


def filter_files(files):
    # TODO, filter non media files out, or files the server cannot work with
    return files


def add_file(path):
    log.d('Adding file: {}'.format(os.path.basename(path)))
    file_send_connection.transfer_file(os.path.basename(path), path)
    # TODO last worked here, test if file is transferred

def add_ticker_elt(*args):
    raise NotImplementedError


def remove_something(media_id):
    raise NotImplementedError


def property_update(key, value):
    raise NotImplementedError


def receive(m):
    raise NotImplementedError


def __something_removed(id):
    raise NotImplementedError


def __automode_changed(automode):
    raise NotImplementedError


def __login_success():
    """
    Called when the login is acked by the server.
    :return: void
    """
    global file_send_connection
    gui.login_success()
    file_send_connection = send_files.SendFiles(data.host, logger_name='beamer_client')
