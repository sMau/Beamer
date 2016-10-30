import logging

import sys

import datetime

import server.controller as ctrl
from server import data
from server.gui import ServerGUIImpl

timestamp = datetime.datetime.now().strftime('%Y-%m-%dT%H:%M:%S')

LOG_TO_FILE = False
LOG_FILE = data.LOG_PATH + 'srv_' + timestamp + '.log'
logger = None

if __name__ == '__main__':

    logger = logging.getLogger('beamer_server')
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

    logger.info('Starting the 4s LAN Beamer Server Software')
    gui = ServerGUIImpl()
    ctrl.tear_up(gui)
