import client.controller as ctrl
import client.log as log
from client.gui import ClientGUIImpl

if __name__ == '__main__':
    log.i('Starting the 4s LAN Beamer Client Software')
    gui = ClientGUIImpl(None, None, None)  # TODO
    ctrl.tear_up(gui)
