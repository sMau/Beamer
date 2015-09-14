import logging

FILENAME = '/beamer_client.log'
LOG_TO_GUI = True

def configure(log_path='', lvl=logging.DEBUG):
    if log_path == '':
        logging.basicConfig(level=lvl)
    else:
        logging.basicConfig(filename=log_path + FILENAME,level=lvl)

def d(msg):
    '''
    Debug
    :param msg: Message to log
    :return:void
    '''
    logging.debug(msg)

def i(msg):
    '''
    Info
    :param msg: Message to log
    :return:void
    '''
    logging.info(msg)
    log_to_gui(msg)


def w(msg):
    '''
    Warn
    :param msg: Message to log
    :return:void
    '''
    logging.warning(msg)
    log_to_gui(msg)

def e(msg, exc=None):
    '''
    Error
    :param msg: Message to log
    :param exc: Exception
    :return:void
    '''
    logging.error(msg, exc)
    log_to_gui(msg, exc)

def c(msg, exc=None):
    '''
    Critical
    :param msg: Message to log
    :param exc: Exception
    :return:void
    '''
    logging.critical(msg, exc)
    log_to_gui(msg, exc)


def log_to_gui(msg, exc=None):
    '''
    Display a message to the gui logging module
    :param msg: Message to display
    :param exc: Exception
    :return: void
    '''
    if LOG_TO_GUI:
        raise NotImplementedError('')