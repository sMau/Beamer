import sys
from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QApplication

from client import controller, log
from client.main_window_gen import Ui_MainWindow


class MainWindow(QtWidgets.QMainWindow, Ui_MainWindow):

    def automode_changed(self):
        pass

    def server_shutdown(self):
        pass

    def ticker_data_changed(self):
        pass

    def media_data_changed(self):
        pass

    def display_info(self, msg):
        pass

    def display_error(self, msg, exc=None):
        pass

    def login_success(self):
        pass

    def __init__(self, parent=None):
        super(MainWindow, self).__init__(parent)
        self.setupUi(self)
        self.actionAdd_File.triggered.connect(self.trig_add_file)
        log.status_bar = self.statusBar()
        connect_to, ok_clicked = QtWidgets.QInputDialog.getText(self, 'Connect', 'Host')
        if not ok_clicked:
            sys.exit(0)
        log.i('Connecting to {}'.format(connect_to))
        controller.tear_up(self)
        log.i('Connection established.')

    def trig_add_file(self):
        files = QtWidgets.QFileDialog.getOpenFileNames()[0]
        log.d('Files selected, uploading now {}'.format(files))
        controller.add_files(files)
