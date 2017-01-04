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
        AddCountdownDialog.resize(379, 170)
        self.buttonBox = QtWidgets.QDialogButtonBox(AddCountdownDialog)
        self.buttonBox.setGeometry(QtCore.QRect(20, 120, 341, 32))
        self.buttonBox.setOrientation(QtCore.Qt.Horizontal)
        self.buttonBox.setStandardButtons(QtWidgets.QDialogButtonBox.Cancel|QtWidgets.QDialogButtonBox.Ok)
        self.buttonBox.setObjectName("buttonBox")
        self.gridLayoutWidget = QtWidgets.QWidget(AddCountdownDialog)
        self.gridLayoutWidget.setGeometry(QtCore.QRect(20, 10, 246, 94))
        self.gridLayoutWidget.setObjectName("gridLayoutWidget")
        self.gridLayout = QtWidgets.QGridLayout(self.gridLayoutWidget)
        self.gridLayout.setContentsMargins(0, 0, 0, 0)
        self.gridLayout.setObjectName("gridLayout")
        self.radioBtnDueTo = QtWidgets.QRadioButton(self.gridLayoutWidget)
        self.radioBtnDueTo.setObjectName("radioBtnDueTo")
        self.gridLayout.addWidget(self.radioBtnDueTo, 3, 0, 1, 1)
        self.radBtnInSeconds = QtWidgets.QRadioButton(self.gridLayoutWidget)
        self.radBtnInSeconds.setObjectName("radBtnInSeconds")
        self.gridLayout.addWidget(self.radBtnInSeconds, 1, 0, 1, 1)
        self.durationAsDate = QtWidgets.QTimeEdit(self.gridLayoutWidget)
        self.durationAsDate.setEnabled(True)
        self.durationAsDate.setObjectName("durationAsDate")
        self.gridLayout.addWidget(self.durationAsDate, 3, 1, 1, 1)
        self.durationInSeconds = QtWidgets.QSpinBox(self.gridLayoutWidget)
        self.durationInSeconds.setMaximum(86400)
        self.durationInSeconds.setObjectName("durationInSeconds")
        self.gridLayout.addWidget(self.durationInSeconds, 1, 1, 1, 1)
        self.lineEditName = QtWidgets.QLineEdit(self.gridLayoutWidget)
        self.lineEditName.setObjectName("lineEditName")
        self.gridLayout.addWidget(self.lineEditName, 0, 1, 1, 1)
        self.label = QtWidgets.QLabel(self.gridLayoutWidget)
        self.label.setObjectName("label")
        self.gridLayout.addWidget(self.label, 0, 0, 1, 1)

        self.retranslateUi(AddCountdownDialog)
        self.buttonBox.accepted.connect(AddCountdownDialog.accept)
        self.buttonBox.rejected.connect(AddCountdownDialog.reject)
        QtCore.QMetaObject.connectSlotsByName(AddCountdownDialog)

    def retranslateUi(self, AddCountdownDialog):
        _translate = QtCore.QCoreApplication.translate
        AddCountdownDialog.setWindowTitle(_translate("AddCountdownDialog", "Add Countdown"))
        self.radioBtnDueTo.setText(_translate("AddCountdownDialog", "due to hh:mm"))
        self.radBtnInSeconds.setText(_translate("AddCountdownDialog", "duration in s"))
        self.lineEditName.setText(_translate("AddCountdownDialog", "Countdown"))
        self.label.setText(_translate("AddCountdownDialog", "Name"))

