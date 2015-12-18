import logging
import socket
import threading
import time

import commons.displayables as displaybles
import server.data as data
from commons import msg, recv_files
from commons.json_socket import JsonSocket
from commons.msg import Msg

gui = None
connections = dict()
timer = None  # check out http://stackoverflow.com/questions/8600161/executing-periodic-actions-in-python
__socket_main = None


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
    # t.daemon = True TODO ?
    t.start()

    logging.info('Started main server thread. Listening now for incoming connections.')

    logging.info('Starting file receiver')

    recv_files.init(listen_port=file_srv_port)

    logging.info('Starting message receive loop...')

    __check_for_new_messages()


def __check_for_new_messages():
    """
    Checks each connection for new messages periodically
    :return:
    """
    while True:
        time.sleep(0.2)  # XXX eval a good timespan
        broken_connections = []
        for con_key in list(connections):
            try:
                msg_dict = connections[con_key].check_for_new_msg()
                if msg_dict is not None:
                    cmd = int(msg_dict['cmd_id'])
                    ack = int(msg_dict['ack'])

                    if cmd == msg.CMD_CONNECT:
                        msg_ack_connect = Msg(ack=1, cmd_id=msg.CMD_CONNECT)
                        connections[con_key].send(msg_ack_connect)
                    elif cmd == msg.CMD_UNDEFINED:
                        pass
                    else:
                        pass
            except ConnectionError:
                logging.error('Connection error occured {!s}, removing client from list.'.format(con_key))
                broken_connections.append(con_key)

        for c in broken_connections:
            del connections[c]


def __listen_for_new_connections():
    """
    Listens for new client connections.
    :return:
    """
    # TODO allow only one connection per client ip
    while True:
        time.sleep(1)  # XXX eval, if long enough
        c, addr = __socket_main.accept()  # Establish connection with client.
        logging.info('Connection accepted: {}'.format(addr))
        connections[addr] = JsonSocket(c, addr)
        # c.send('Thank you for connecting') TODO send connection ack msg


def __add_file(file):
    raise NotImplementedError


def __add_countdown(countdown):
    """
    Add countdown to media list
    :param countdown: countdown to add
    :return:
    """
    logging.info('Adding countdown %s' % countdown)
    data.media.add(countdown)


def __add_ticker_elt(elt):
    """
    Adds element to ticker list.
    :param elt: element to add
    :return:
    """
    logging.info('Adding ticker element %s' % elt)
    data.ticker_elements.add(elt)


def __add_connection(id, con):
    """
    Adds socket connection to connection list after new client connected.
    :param id: unique identifier of connection
    :param con: connection socket
    :return:
    """
    logging.info('Client connected %s' % con)
    global connections
    connections[id] = con


def __update_property(key, value):
    """
    Updates property in properties map.
    :param key: key of property
    :param value: new value
    :return:
    """
    logging.info('Updating config property %s to %s' % key, value)
    data.config[key] = value


def shutdown_gracefully():
    raise NotImplementedError


def __remove_main_displayable_by_id(id):
    """
    Removes main displayable from main media list.
    :param id: to remove
    :return:
    """
    data.media.remove(data.get_media_by_id(id))


def __remove_ticker_displayble_by_id(id):
    """
    Removes ticker displayable from ticker list.
    :param id: to remove
    :return:
    """
    data.ticker_elements.remove(data.get_ticker_elt_by_id(id))


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


def broadcast(m):
    raise NotImplementedError
