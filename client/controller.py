import socket
import threading

import client.data as data
from commons import msg
from commons.json_socket import JsonSocket
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
    tmp_sock.connect((data.host, data.control_port))

    json_connection = JsonSocket(tmp_sock)

    con_msg = Msg(cmd_id=msg.CMD_CONNECT)
    con_msg.data.append(data.login_name)
    con_msg.data.append(data.login_pw)

    json_connection.send(con_msg)
    # http://cpiekarski.com/2011/05/09/super-easy-python-json-client-server/

    t = threading.Thread(target=__check_for_new_msgs)
    t.daemon = True
    t.start()


def __check_for_new_msgs():
    while True:
        msg_dict = json_connection.check_for_new_msgs()
        if msg_dict is not None:
            cmd = int(msg_dict['cmd_id'])
            ack = int(msg_dict['ack'])
            if cmd == msg.CMD_CONNECT and ack == 1:
                __login_success()
            elif cmd == msg.CMD_UNDEFINED:
                pass
            else:
                pass


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