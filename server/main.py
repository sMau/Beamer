import logging

import server.controller as ctrl
from server.gui import ServerGUIImpl

LOG_FILE = ''

if __name__ == '__main__':
    logging.basicConfig(filename=LOG_FILE, level=logging.DEBUG)
    logging.info('Starting the 4s LAN Beamer Server Software')
    gui = ServerGUIImpl()
    ctrl.tear_up(gui)
