import logging
import socket
import threading
import time

import commons.displayables as displayables
import server.data as data
from commons import msg, recv_files
from commons.displayables import MediaFile, Countdown, TickerTxtElt
from commons.json_socket import JsonSocket
from commons.msg import Msg
from utils.observer import Observer

logger = logging.getLogger('beamer_server.controller')


class FileTransferObserver(Observer):

    def __init__(self):
        pass

    def notify(self, *args):
        assert len(args) >= 2, 'Error not enough args in the notify msg of File Transfer Finished'
        file_transfer_finished(args[0], args[1])


gui = None
connections = dict()
timer = None  # check out http://stackoverflow.com/questions/8600161/executing-periodic-actions-in-python
__socket_main = None


# TODO autoclean backlog with according error messages to the client
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

    logger.info('Starting main server thread')

    __socket_main = socket.socket()
    __socket_main.bind((host, port))
    __socket_main.listen(32)

    t = threading.Thread(target=__listen_for_new_connections)
    t.start()

    logger.info('Started main server thread. Listening now for incoming connections.')

    logger.info('Starting file receiver')

    recv_files.init(data.MEDIA_PATH, listen_port=file_srv_port, logger_name='beamer_server')
    recv_files.subscribe(FileTransferObserver())

    logger.info('Starting message receive loop...')

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

                logger.debug('Received msg on control channel: {}'.format(str(msg_dict)))

                if msg_dict is not None:
                    cmd = int(msg_dict[msg.KEY_CMD_ID])

                    if cmd == msg.Type.CMD_CONNECT:
                        __connect_request(con_key)
                    elif cmd == msg.Type.CMD_DISPLAY:
                        pass
                    elif cmd == msg.Type.CMD_DISPLAY_NEXT:
                        pass
                    elif cmd == msg.Type.ADD_DISPLAYABLE_FILE and msg_dict[msg.KEY_FILE_TRANSFER] == 1:
                        __add_displayable_msg(msg_dict)
                    elif cmd == msg.Type.ADD_COUNTDOWN:
                        __add_countdown(msg_dict)
                    elif cmd == msg.Type.ADD_TICKER_TXTELT:
                        __add_ticker_txtelt(msg_dict)
                    elif cmd == msg.Type.REMOVE_TICKER_DISPLAYABLE:
                        __remove_ticker_displayble(msg_dict)
                    elif cmd == msg.Type.REMOVE_MAIN_DISPLAYABLE:
                        __remove_main_displayable(msg_dict)
                    elif cmd == msg.Type.CMD_UNDEFINED:
                        pass
                    elif cmd == msg.Type.CMD_PROP_UPDATE:
                        pass
                    else:
                        pass
            except ConnectionError:
                logger.error('Connection error occured {!s}, removing client from list.'.format(con_key))
                broken_connections.append(con_key)

        for c in broken_connections:
            del connections[c]


def __connect_request(con_key):
    msg_ack_connect = Msg(ack=1, cmd_id=msg.Type.CMD_CONNECT)
    connections[con_key].send(msg_ack_connect)


def __listen_for_new_connections():
    """
    Listens for new client connections.
    :return: void
    """
    while True:
        time.sleep(1)
        c, addr = __socket_main.accept()  # Establish connection with client.
        logger.info('Connection accepted: {}'.format(addr))
        __add_connection(addr, JsonSocket(c, addr))


def file_transfer_finished(name, file_path):
    """
    This function is called when a file transfer finishes. Called by the recv_files.py module.
    :param file_id: The unique id of this file
    :param file_path: path to the file, where the recv_files.py module stored the file
    :return: void
    """
    logger.debug('Transfer finished, recognized name: {}'.format(name))
    logger.debug(str(file_receive_backlog))
    if name in file_receive_backlog:
        name, type = file_receive_backlog.pop(name)
        logger.info('Received file {}, {}'.format(name, type))
        m = data.add_media(MediaFile(name, file_path, type=type))

        # TODO add the transfer of a preview image to the client
        ack_msg = Msg(m, ack=1, cmd_id=msg.Type.ADD_DISPLAYABLE_FILE)
        broadcast(ack_msg)
    else:
        logger.error('Received unknown file with name {}'.format(name))


def __add_displayable_msg(msg_dict):
    name = msg_dict[msg.KEY_DATA][0]
    type = displayables.TYPE_IMAGE # TODO determine type correctly!!! At the moment assume always image type
    file_receive_backlog[name] = name, type


def __add_countdown(msg_dict : dict):
    """
    Add countdown to media list
    :param countdown: countdown to add
    :return:
    """
    name = msg_dict[msg.KEY_DATA][0]
    duration = msg_dict[msg.KEY_DATA][1]

    countdown = Countdown(name, duration)

    logger.info('Adding countdown %s' % countdown)
    c = data.add_media(countdown)

    m = Msg(c, ack=1, cmd_id=msg.Type.ADD_COUNTDOWN)
    broadcast(m)


def __add_ticker_txtelt(msg_dict : dict):
    """
    Adds element to ticker list.
    :param elt: element to add
    :return:
    """

    text = msg_dict[msg.KEY_DATA][0]

    txtelt = TickerTxtElt(text)

    logger.info('Adding ticker element %s' % txtelt)
    data.add_ticker_txt_elt(txtelt)

    m = Msg(txtelt, ack=1, cmd_id=msg.Type.ADD_TICKER_TXTELT)
    broadcast(m)


def __remove_main_displayable(msg_dict):
    uid = msg_dict[msg.KEY_DATA][0]
    if data.remove_main_displayble(uid):
        logger.info('Removed main displayable {}'.format(uid))
        m = Msg(id, ack=1, cmd_id=msg.Type.REMOVE_MAIN_DISPLAYABLE)
        broadcast(m)


def __remove_ticker_displayble(msg_dict):
    uid = msg_dict[msg.KEY_DATA][0]
    if data.remove_ticker_displayble(uid):
        logger.info('Removed ticker displayable {}'.format(uid))
        m = Msg(id, ack=1, cmd_id=msg.Type.REMOVE_TICKER_DISPLAYABLE)
        broadcast(m)


def __add_connection(id, con):
    """
    Adds socket connection to connection list after new client connected.
    :param id: unique identifier of connection
    :param con: JSON Socket
    :return:
    """
    logger.info('Client connected {}'.format(id))
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
    logger.info('Updating config property %s to %s' % key, value)
    data.property_update(key, value)

    m = Msg(key, value, ack=1, cmd_id=msg.Type.CMD_PROP_UPDATE)
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

    if media_to_show.type == displayables.TYPE_IMAGE:
        gui.show_image(media_to_show)
    elif media_to_show.type == displayables.TYPE_VIDEO:
        gui.show_video(media_to_show)
    elif media_to_show.type == displayables.TYPE_COUNTDOWN:
        gui.show_countdown(media_to_show)
    else:
        logger.error('Unable to display. Unknown media type of media %s' % media_to_show)


def __display_next_main_rand():
    """
    Display random media in main frame.
    :return:
    """
    __display_in_main(data.get_rand_media())


def broadcast(m : msg.Msg):
    for c in connections.keys():
        connections[c].send(m)