import logging
import threading

from commons.JsonSocket import JsonSocket
import commons.displayables as displaybles
import server.data as data
import socket
import time


gui = None
connections = dict()
timer = None  # check out http://stackoverflow.com/questions/8600161/executing-periodic-actions-in-python
__socket_main = None


def tear_up(srv_gui, host='127.0.0.1', port=11111):
    """
    Open connection to listen for incoming messages.
    Start FTP server.
    :return: void
    """

    global gui, __socket_main
    gui = srv_gui

    logging.info('Starting main server thread')

    __socket_main = socket.socket()
    __socket_main.bind((host, port))
    __socket_main.listen(32)

    t = threading.Thread(target=__listen_for_new_connections)
    t.daemon = True
    t.start()

    logging.info('Started main server thread. Listening now for incoming connections.')

    logging.info('Starting message receive loop...')

    __check_for_new_messages()


def __check_for_new_messages():
    while True:
        time.sleep(0.2) # XXX eval a good timespan
        for con_key in list(connections):
            msg_dict = connections[con_key].check_for_new_msg()
            if msg_dict != None:
                pass #TODO do something with the msg


def __listen_for_new_connections():
    while True:
        time.sleep(1) # XXX eval, if long enough
        c, addr = __socket_main.accept()     # Establish connection with client.
        logging.info('Connection accepted: %s', addr)
        connections[addr] = JsonSocket(c, addr)
        #c.send('Thank you for connecting') TODO send connection ack msg
9

def __add_file(file):
    raise NotImplementedError


def __add_countdown(countdown):
    logging.info('Adding countdown %s' % countdown)
    data.media.add(countdown)


def __add_ticker_elt(elt):
    logging.info('Adding ticker element %s' % elt)
    data.ticker_elements.add(elt)


def __add_connection(id, con):
    logging.info('Client connected %s' % con)
    global connections
    connections[id] = con


def __update_property(key, value):
    logging.info('Updating config property %s to %s' % key, value)
    data.config[key] = value


def shutdown_gracefully():
    raise NotImplementedError


def __remove_main_displayable_by_id(id):
    data.media.remove(data.get_media_by_id(id))


def __remove_ticker_displayble_by_id(id):
    data.ticker_elements.remove(data.get_ticker_elt_by_id(id))


def __display_in_main(id):
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
    __display_in_main(data.get_rand_media())


def broadcast(msg):
    raise NotImplementedError
