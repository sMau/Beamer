import logging
import socket
import threading
import time
import uuid

import commons.displayables as displaybles
import server.data as data
from commons import msg, recv_files
from commons.displayables import MediaFile, Countdown, TickerTxtElt
from commons.json_socket import JsonSocket
from commons.msg import Msg
from utils.observer import Observer


class FileTransferObserver(Observer):

    def __init__(self):
        pass

    def notify(self, *args):
        assert len(*args) >= 2, 'Error not enough args in the notify msg of File Transfer Finished'
        file_transfer_finished(*args[0], *args[1])


gui = None
connections = dict()
timer = None  # check out http://stackoverflow.com/questions/8600161/executing-periodic-actions-in-python
__socket_main = None

file_receive_backlog = {}


def tear_up(srv_gui, host='127.0.0.1', port=11111, file_srv_port=11112):
    """
    Open connection to listen for incoming messages.
    Start file receiver.
    :param port: port the controll server should listen on
    :param file_srv_port: port the file server should listen on
    :param host: IP adress to bind to
    :param srv_gui: current server gui object
    :return: void
    """

    global gui, __socket_main
    gui = srv_gui

    logging.info('Starting main server thread')

    __socket_main = socket.socket()
    __socket_main.bind((host, port))
    __socket_main.listen(32)

    t = threading.Thread(target=__listen_for_new_connections)
    t.start()

    logging.info('Started main server thread. Listening now for incoming connections.')

    logging.info('Starting file receiver')

    recv_files.init(data.MEDIA_PATH, listen_port=file_srv_port)
    recv_files.subscribe(FileTransferObserver())

    logging.info('Starting message receive loop...')

    __check_for_new_messages()


def __check_for_new_messages():
    """
    Checks each connection for new messages periodically
    :return:
    """
    while True:
        time.sleep(0.2)
        broken_connections = []
        for con_key in list(connections):
            try:
                msg_dict = connections[con_key].check_for_new_msg()
                if msg_dict is not None:
                    cmd = int(msg_dict[msg.KEY_CMD_ID])

                    if cmd == msg.MsgType.CMD_CONNECT:
                        __connect_request(con_key)
                    elif cmd == msg.MsgType.CMD_DISPLAY:
                        pass
                    elif cmd == msg.MsgType.CMD_DISPLAY_NEXT:
                        pass
                    elif cmd == msg.MsgType.ADD_FILE and msg_dict[msg.KEY_FILE_TRANSFER] == '1':
                        __add_file_msg(msg_dict)
                    elif cmd == msg.MsgType.ADD_COUNTDOWN:
                        __add_countdown(msg_dict)
                    elif cmd == msg.MsgType.ADD_TICKER_TXTELT:
                        __add_ticker_txtelt(msg_dict)
                    elif cmd == msg.MsgType.REMOVE_TICKER_DISPLAYABLE:
                        __remove_ticker_displayble(msg_dict)
                    elif cmd == msg.MsgType.REMOVE_MAIN_DISPLAYABLE:
                        __remove_main_displayable(msg_dict)
                    elif cmd == msg.MsgType.CMD_UNDEFINED:
                        pass
                    elif cmd == msg.MsgType.CMD_PROP_UPDATE:
                        pass
                    else:
                        pass
            except ConnectionError:
                logging.error('Connection error occured {!s}, removing client from list.'.format(con_key))
                broken_connections.append(con_key)

        for c in broken_connections:
            del connections[c]


def __connect_request(con_key):
    msg_ack_connect = Msg(ack=1, cmd_id=msg.MsgType.CMD_CONNECT)
    connections[con_key].send(msg_ack_connect)


def __listen_for_new_connections():
    """
    Listens for new client connections.
    :return: void
    """
    while True:
        time.sleep(1)
        c, addr = __socket_main.accept()  # Establish connection with client.
        logging.info('Connection accepted: {}'.format(addr))
        __add_connection(addr, JsonSocket(c, addr))


def file_transfer_finished(file_id, file_path):
    if file_id in file_receive_backlog:
        name, type = file_receive_backlog.pop(file_id)
        logging.info('Received file {}, {}'.format(file_id, name))
        m = data.add_media(MediaFile(name, file_path, type=type))

        # TODO add the transfer of a preview image to the client
        ack_msg = Msg(m, ack=1, cmd_id=msg.MsgType.ADD_FILE)
        broadcast(ack_msg)
    else:
        logging.error('Received unknown file with id {}'.format(file_id))


def __add_file_msg(msg_dict):
    file_id = msg_dict[msg.KEY_DATA][0]
    name = msg_dict[msg.KEY_DATA][1]
    type = msg_dict[msg.KEY_DATA][2]
    file_receive_backlog[file_id] = name, type


def __add_countdown(msg_dict : dict):
    """
    Add countdown to media list
    :param countdown: countdown to add
    :return:
    """
    name = msg_dict[msg.KEY_DATA][0]
    duration = msg_dict[msg.KEY_DATA][1]

    countdown = Countdown(name, duration)

    logging.info('Adding countdown %s' % countdown)
    c = data.add_media(countdown)

    m = Msg(c, ack=1, cmd_id=msg.MsgType.ADD_COUNTDOWN)
    broadcast(m)


def __add_ticker_txtelt(msg_dict : dict):
    """
    Adds element to ticker list.
    :param elt: element to add
    :return:
    """

    text = msg_dict[msg.KEY_DATA][0]

    txtelt = TickerTxtElt(text)

    logging.info('Adding ticker element %s' % txtelt)
    data.add_ticker_txt_elt(txtelt)

    m = Msg(txtelt, ack=1, cmd_id=msg.MsgType.ADD_TICKER_TXTELT)
    broadcast(m)


def __remove_main_displayable(msg_dict):
    uid = msg_dict[msg.KEY_DATA][0]
    if data.remove_main_displayble(uid):
        logging.info('Removed main displayable {}'.format(uid))
        m = Msg(id, ack=1, cmd_id=msg.MsgType.REMOVE_MAIN_DISPLAYABLE)
        broadcast(m)


def __remove_ticker_displayble(msg_dict):
    uid = msg_dict[msg.KEY_DATA][0]
    if data.remove_ticker_displayble(uid):
        logging.info('Removed ticker displayable {}'.format(uid))
        m = Msg(id, ack=1, cmd_id=msg.MsgType.REMOVE_TICKER_DISPLAYABLE)
        broadcast(m)


def __add_connection(id, con):
    """
    Adds socket connection to connection list after new client connected.
    :param id: unique identifier of connection
    :param con: JSON Socket
    :return:
    """
    logging.info('Client connected %s' % id)
    global connections
    connections[id] = con


def __update_property(msg_dict):
    """
    Updates property in properties map.
    :param key: key of property
    :param value: new value
    :return:
    """
    key =  msg_dict[msg.KEY_DATA][0]
    value = msg_dict[msg.KEY_DATA][1]
    logging.info('Updating config property %s to %s' % key, value)
    data.property_update(key, value)

    m = Msg(key, value, ack=1, cmd_id=msg.MsgType.CMD_PROP_UPDATE)
    broadcast(m)



def shutdown_gracefully():
    raise NotImplementedError


def __display_in_main(id):
    """
    Display media in main frame.
    :param id: to display
    :return:
    """
    media_to_show = data.get_media_by_id(id)

    if media_to_show.type == displaybles.TYPE_IMAGE:
        gui.show_image(media_to_show)
    elif media_to_show.type == displaybles.TYPE_VIDEO:
        gui.show_video(media_to_show)
    elif media_to_show.type == displaybles.TYPE_COUNTDOWN:
        gui.show_countdown(media_to_show)
    else:
        logging.error('Unable to display. Unknown media type of media %s' % media_to_show)


def __display_next_main_rand():
    """
    Display random media in main frame.
    :return:
    """
    __display_in_main(data.get_rand_media())


def broadcast(m : msg.Msg):
    for c in connections:
        c.send(m)