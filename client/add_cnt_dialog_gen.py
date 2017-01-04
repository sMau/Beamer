# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'beamer/add_cntdwn_dialog.ui'
#
# Created by: PyQt5 UI code generator 5.7
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_AddCountdownDialog(object):
    def setupUi(self, AddCountdownDialog):
        AddCountdownDialog.setObjectName("AddCountdownDialog")
        AddCountdownDialog.resize(379, 156)
        self.buttonBox = QtWidgets.QDialogButtonBox(AddCountdownDialog)
        self.buttonBox.setGeometry(QtCore.QRect(20, 110, 341, 32))
        self.buttonBox.setOrientation(QtCore.Qt.Horizontal)
        self.buttonBox.setStandardButtons(QtWidgets.QDialogButtonBox.Cancel|QtWidgets.QDialogButtonBox.Ok)
        self.buttonBox.setObjectName("buttonBox")
        self.gridLayoutWidget = QtWidgets.QWidget(AddCountdownDialog)
        self.gridLayoutWidget.setGeometry(QtCore.QRect(20, 10, 246, 87))
        self.gridLayoutWidget.setObjectName("gridLayoutWidget")
        self.gridLayout = QtWidgets.QGridLayout(self.gridLayoutWidget)
        self.gridLayout.setContentsMargins(0, 0, 0, 0)
        self.gridLayout.setObjectName("gridLayout")
        self.radioBtnDueTo = QtWidgets.QRadioButton(self.gridLayoutWidget)
        self.radioBtnDueTo.setObjectName("radioBtnDueTo")
        self.gridLayout.addWidget(self.radioBtnDueTo, 2, 0, 1, 1)
        self.durationAsDate = QtWidgets.QTimeEdit(self.gridLayoutWidget)
        self.durationAsDate.setObjectName("durationAsDate")
        self.gridLayout.addWidget(self.durationAsDate, 2, 1, 1, 1)
        self.durationInSeconds = QtWidgets.QSpinBox(self.gridLayoutWidget)
        self.durationInSeconds.setMaximum(86400)
        self.durationInSeconds.setObjectName("durationInSeconds")
        self.gridLayout.addWidget(self.durationInSeconds, 0, 1, 1, 1)
        self.radBtnInSeconds = QtWidgets.QRadioButton(self.gridLayoutWidget)
        self.radBtnInSeconds.setObjectName("radBtnInSeconds")
        self.gridLayout.addWidget(self.radBtnInSeconds, 0, 0, 1, 1)

        self.retranslateUi(AddCountdownDialog)
        self.buttonBox.accepted.connect(AddCountdownDialog.accept)
        self.buttonBox.rejected.connect(AddCountdownDialog.reject)
        QtCore.QMetaObject.connectSlotsByName(AddCountdownDialog)

    def retranslateUi(self, AddCountdownDialog):
        _translate = QtCore.QCoreApplication.translate
        AddCountdownDialog.setWindowTitle(_translate("AddCountdownDialog", "Add Countdown"))
        self.radioBtnDueTo.setText(_translate("AddCountdownDialog", "due to hh:mm"))
        self.radBtnInSeconds.setText(_translate("AddCountdownDialog", "duration in s"))

