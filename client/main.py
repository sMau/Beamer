import sys
from PyQt5.QtWidgets import QApplication

import client.log as log
from client.main_window import MainWindow

if __name__ == '__main__':
    log.i('Starting the 4s LAN Beamer Client Software')

    app = QApplication(sys.argv)
    gui = MainWindow()
    gui.show()
    app.exec_()