import datetime

import time
from PyQt5 import QtCore

from PyQt5.QtWidgets import QDialog

from client import log
from client.add_cnt_dialog_gen import Ui_AddCountdownDialog


class AddCountdownDialog(QDialog, Ui_AddCountdownDialog):

    change_by_seconds = True

    def __init__(self, parent=None):
        super(AddCountdownDialog, self).__init__(parent)
        self.setupUi(self)

        self.radBtnInSeconds.setChecked(True)
        self.durationAsDate.setEnabled(False)
        self.durationInSeconds.valueChanged.connect(self.duration_in_seconds_changed)
        self.durationAsDate.timeChanged.connect(self.duration_date_changed)

        self.radBtnInSeconds.clicked.connect(self.radio_exclusive_trigger)
        self.radioBtnDueTo.clicked.connect(self.radio_exclusive_trigger)

        now = datetime.datetime.now()
        self.durationAsDate.setMinimumTime(QtCore.QTime(now.hour, now.minute + 1, now.second))
        self.durationAsDate.setTime(QtCore.QTime(now.hour, now.minute + 1, now.second))

        self.show()

    def duration_in_seconds_changed(self):
        if self.change_by_seconds:
            now = datetime.datetime.now()
            now += datetime.timedelta(seconds=self.durationInSeconds.value())

            self.durationAsDate.setTime(QtCore.QTime(now.hour, now.minute, now.second))

    def duration_date_changed(self):
        if not self.change_by_seconds:
            chosen_time = self.durationAsDate.time()
            as_py_time = chosen_time.toPyTime()
            as_datetime = datetime.datetime.combine(datetime.date.today(), as_py_time)
            dif = (as_datetime - datetime.datetime.now()).total_seconds()
            self.durationInSeconds.setValue(dif)

    def radio_exclusive_trigger(self):
        if self.radBtnInSeconds.isChecked():
            self.radioBtnDueTo.setChecked(False)
            self.change_by_seconds = True
            self.durationAsDate.setEnabled(False)
            self.durationInSeconds.setEnabled(True)

        if self.radioBtnDueTo.isChecked():
            self.radBtnInSeconds.setChecked(False)
            self.change_by_seconds = False
            self.durationInSeconds.setEnabled(False)
            self.durationAsDate.setEnabled(True)