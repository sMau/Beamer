import socket

import client.data as data
from commons import msg
from commons.JsonSocket import JsonSocket
from commons.msg import Msg

json_connection = None
gui = None


def tear_up(c_gui):

    global gui
    gui = c_gui
    __connect()


def __connect():
    global json_connection
    tmp_sock = socket.socket()
    tmp_sock.connect((data.host, data.port))

    json_connection = JsonSocket(tmp_sock)

    con_msg = Msg(command=msg.CMD_CONNECT)
    con_msg.data.append(data.login_name)
    con_msg.data.append(data.login_pw)

    json_connection.send_msg(con_msg)
    # http://cpiekarski.com/2011/05/09/super-easy-python-json-client-server/



def add_file(path):
    raise NotImplementedError


def add_ticker_elt(*args):
    raise NotImplementedError


def remove_something(id):
    raise NotImplementedError


def property_update(key, value):
    raise NotImplementedError


def receive(msg):
    raise NotImplementedError


def __something_removed(id):
    raise NotImplementedError


def __automode_changed(automode):
    raise NotImplementedError


def __login_success():
    gui.login_success()
    raise NotImplementedError
