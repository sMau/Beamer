import logging

import datetime

from client import data

timestamp = datetime.datetime.now().strftime('%Y-%m-%dT%H:%M:%S')

LOG_TO_FILE = False
LOG_FILE = data.LOG_PATH + 'client_' + timestamp + '.log'

logger = logging.getLogger('beamer_client')
logger.propagate = False
logger.setLevel(logging.DEBUG)

form = logging.Formatter(fmt='%(asctime)s - %(levelname)s:%(module)s - %(message)s')

if LOG_TO_FILE:
    fh = logging.FileHandler(filename=LOG_FILE)
    fh.setFormatter(form)
    logger.addHandler(fh)

console = logging.StreamHandler()
console.setLevel(logging.DEBUG)
console.setFormatter(form)
logger.addHandler(console)

status_bar = None

def d(msg):
    """
    Debug
    :param msg: Message to log
    :return:void
    """
    logger.debug(msg)


def i(msg):
    """
    Info
    :param msg: Message to log
    :return:void
    """
    logger.info(msg)
    __log_to_gui(msg)


def w(msg):
    """
    Warn
    :param msg: Message to log
    :return:void
    """
    logger.warning(msg)
    __log_to_gui(msg)


def e(msg, exc=None):
    """
    Error
    :param msg: Message to log
    :param exc: Exception
    :return:void
    """
    logger.error(msg, exc)
    __log_to_gui(msg, exc)


def c(msg, exc=None):
    """
    Critical
    :param msg: Message to log
    :param exc: Exception
    :return:void
    """
    logger.critical(msg, exc)
    __log_to_gui(msg, exc)


def __log_to_gui(msg, exc=None):
    """
    Display a message to the gui logging module
    :param msg: Message to display
    :param exc: Exception
    :return: void
    """
    if status_bar is not None:
        status_bar.showMessage(msg)
