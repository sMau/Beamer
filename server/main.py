import server.controller as ctrl
import logging

LOG_FILE = ''

if __name__ == '__main__':
    logging.basicConfig(filename=LOG_FILE, level=logging.DEBUG)
    logging.info('Starting the 4s LAN Beamer Server Software')
    ctrl.tear_up()
