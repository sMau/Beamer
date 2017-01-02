from PyQt5 import QtCore
from os.path import expanduser

from client import log
from commons.displayables import MainDisplayable, TickerDisplayable

BASE_PATH = expanduser('~') + '/.beamersoftware_client/'
MEDIA_PATH = BASE_PATH + 'media/'
LOG_PATH = BASE_PATH + 'log/'
__DB_PATH = BASE_PATH + 'database.db'  # TODO implement db stuff etc


login_name = '1'
login_pw = '2'
host, control_port, file_port = '127.0.0.1', 11111, 11112
media = set()
ticker_elements = set()
templates = set()
gui_config = dict()


def add_media(m:MainDisplayable):
    """
    Add a main displayable to the set.
    :param m: main displayable to add
    :return:
    """
    media.add(m)
    log.i('Media File added successfully: {!s}'.format(m.name))


def add_ticker_element(e:TickerDisplayable):
    ticker_elements.add(e)

    log.i('Ticker element added successfully: {!s}'.format(e.text))


def serialize_gui_config():
    """
    serializes the gui configuration dictonary
    :return: void
    """
    raise NotImplementedError('')


def load_gui_config():
    """
    loads gui configuration from disk
    :return:
    """
    raise NotImplementedError('')
