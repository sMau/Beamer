import sys
from PyQt5 import QtWidgets, QtCore
from PyQt5.QtCore import QObject
from client import controller, log
from client.main_window_gen import Ui_MainWindow


class MainWindow(QtWidgets.QMainWindow, Ui_MainWindow):

    log_to_gui_sig = QtCore.pyqtSignal(str)

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
        self.actionAdd_Ticker.triggered.connect(self.trig_add_ticker_txt_elt)
        self.actionAdd_Countdown.triggered.connect(self.trig_add_countdown)
        self.actionAdd_Slide.triggered.connect(self.trig_add_slide)

        self.log_to_gui_sig.connect(self.log_to_gui)

        log.log_to_gui_signal = self.log_to_gui_sig

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

    def trig_add_ticker_txt_elt(self):

        text, ok_clicked = QtWidgets.QInputDialog.getText(self, 'Add Ticker Element', 'Text')
        if ok_clicked:
            log.d('Adding ticker element with text: {}'.format(text))
            controller.add_ticker_elt(text)

    def trig_add_countdown(self):
        # TODO extend the input dialog to be able to add bg and fg color and a name
        duration, ok_clicked = QtWidgets.QInputDialog.getInt(self, 'Add Countdown', 'Duration in seconds')
        if ok_clicked:
            log.d('Add countdown with duration: {!s}'.format(duration))
            controller.add_countdown('Countdown_Name', duration)

    def trig_add_slide(self):
        pass

    def log_to_gui(self, msg:str):
        self.statusbar.showMessage(msg)