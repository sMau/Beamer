import socket
import threading

import os

import client.data as data
from client import log
from commons import msg, send_files
from commons.displayables import MediaFile, TickerTxtElt
from commons.json_socket import JsonSocket
from commons.msg import Msg


control_channel = None
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
    global control_channel
    tmp_sock = socket.socket()
    tmp_sock.connect((data.host, data.control_port))

    control_channel = JsonSocket(tmp_sock)

    con_msg = Msg(data.login_name, cmd_id=msg.Type.CMD_CONNECT)


    control_channel.send(con_msg)
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
        msg_dict = control_channel.check_for_new_msg()
        if msg_dict is not None:
            log.d('Received new msg: {}'.format(msg_dict))

            msg_dec = Msg(from_dict_magic=msg_dict)

            cmd = msg_dec.cmd_id
            ack = msg_dec.ack

            log.d('decoded msg: {!s}'.format(msg_dec))

            if cmd == msg.Type.CMD_CONNECT and ack == 1:
                __login_success()
            elif cmd == msg.Type.ADD_DISPLAYABLE_FILE and ack == 1:
                data.add_media(MediaFile('','', from_dict_magic=msg_dec.data[0]))
            elif cmd == msg.Type.ADD_TICKER_TXTELT and ack == 1:
                data.add_ticker_element(TickerTxtElt('', from_dict_magic=msg_dec.data[0]))
            elif cmd == msg.Type.CMD_UNDEFINED:
                pass
            else:
                log.e('Received msg with unknown cmd_id: {!s}'.format(cmd))


def add_files(files):
    files = filter_files(files)
    for f in files:
        add_file(f)


def filter_files(files):
    # TODO, filter non media files out, or files the server cannot work with
    return files


def add_file(path):
    log.d('Adding file: {}'.format(os.path.basename(path)))

    file_name = os.path.basename(path)

    add_media_msg = Msg(file_name,file_transfer=1, cmd_id=msg.Type.ADD_DISPLAYABLE_FILE)
    control_channel.send(add_media_msg)

    file_send_connection.transfer_file(file_name, path)


def add_ticker_elt(text:str):
    # TODO NEXT! 1. make adding ticker elements possible. 2. Make countdowns addable. 3. hole adding fine tuning, i.e. filtering of non valid media etc
    log.d('Adding ticker text element: {}'.format(text))

    add_tick_msg = Msg(text, cmd_id=msg.Type.ADD_TICKER_TXTELT)

    control_channel.send(add_tick_msg)


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
    log.d('Login successful.')
    gui.login_success()
    file_send_connection = send_files.SendFiles(data.host, logger_name='beamer_client')
