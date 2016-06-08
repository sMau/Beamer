/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.6.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QLocale>
#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QHBoxLayout>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QListWidget>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenu>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QTableView>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QAction *actionAdd_File;
    QAction *actionDelete;
    QAction *actionShow;
    QAction *actionShow_Next;
    QAction *actionSettings;
    QAction *actionAdd_Countdown;
    QAction *actionAdd_Ticker;
    QAction *actionAdd_Slide;
    QWidget *centralwidget;
    QHBoxLayout *horizontalLayout_2;
    QListWidget *listWidget;
    QTableView *tableView;
    QMenuBar *menubar;
    QMenu *menuFile;
    QMenu *menuSettings;
    QStatusBar *statusbar;
    QToolBar *toolBar;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QStringLiteral("MainWindow"));
        MainWindow->resize(911, 606);
        QSizePolicy sizePolicy(QSizePolicy::MinimumExpanding, QSizePolicy::MinimumExpanding);
        sizePolicy.setHorizontalStretch(0);
        sizePolicy.setVerticalStretch(0);
        sizePolicy.setHeightForWidth(MainWindow->sizePolicy().hasHeightForWidth());
        MainWindow->setSizePolicy(sizePolicy);
        MainWindow->setMinimumSize(QSize(550, 350));
        MainWindow->setLocale(QLocale(QLocale::English, QLocale::UnitedStates));
        MainWindow->setUnifiedTitleAndToolBarOnMac(false);
        actionAdd_File = new QAction(MainWindow);
        actionAdd_File->setObjectName(QStringLiteral("actionAdd_File"));
        actionDelete = new QAction(MainWindow);
        actionDelete->setObjectName(QStringLiteral("actionDelete"));
        actionShow = new QAction(MainWindow);
        actionShow->setObjectName(QStringLiteral("actionShow"));
        actionShow_Next = new QAction(MainWindow);
        actionShow_Next->setObjectName(QStringLiteral("actionShow_Next"));
        actionSettings = new QAction(MainWindow);
        actionSettings->setObjectName(QStringLiteral("actionSettings"));
        actionAdd_Countdown = new QAction(MainWindow);
        actionAdd_Countdown->setObjectName(QStringLiteral("actionAdd_Countdown"));
        actionAdd_Ticker = new QAction(MainWindow);
        actionAdd_Ticker->setObjectName(QStringLiteral("actionAdd_Ticker"));
        actionAdd_Slide = new QAction(MainWindow);
        actionAdd_Slide->setObjectName(QStringLiteral("actionAdd_Slide"));
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName(QStringLiteral("centralwidget"));
        sizePolicy.setHeightForWidth(centralwidget->sizePolicy().hasHeightForWidth());
        centralwidget->setSizePolicy(sizePolicy);
        centralwidget->setMinimumSize(QSize(500, 300));
        horizontalLayout_2 = new QHBoxLayout(centralwidget);
        horizontalLayout_2->setObjectName(QStringLiteral("horizontalLayout_2"));
        horizontalLayout_2->setContentsMargins(0, 0, 0, 0);
        listWidget = new QListWidget(centralwidget);
        listWidget->setObjectName(QStringLiteral("listWidget"));
        QSizePolicy sizePolicy1(QSizePolicy::Fixed, QSizePolicy::MinimumExpanding);
        sizePolicy1.setHorizontalStretch(0);
        sizePolicy1.setVerticalStretch(0);
        sizePolicy1.setHeightForWidth(listWidget->sizePolicy().hasHeightForWidth());
        listWidget->setSizePolicy(sizePolicy1);
        listWidget->setMinimumSize(QSize(150, 300));

        horizontalLayout_2->addWidget(listWidget);

        tableView = new QTableView(centralwidget);
        tableView->setObjectName(QStringLiteral("tableView"));
        sizePolicy.setHeightForWidth(tableView->sizePolicy().hasHeightForWidth());
        tableView->setSizePolicy(sizePolicy);
        tableView->setMinimumSize(QSize(300, 300));

        horizontalLayout_2->addWidget(tableView);

        MainWindow->setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar->setObjectName(QStringLiteral("menubar"));
        menubar->setGeometry(QRect(0, 0, 911, 22));
        menuFile = new QMenu(menubar);
        menuFile->setObjectName(QStringLiteral("menuFile"));
        menuSettings = new QMenu(menubar);
        menuSettings->setObjectName(QStringLiteral("menuSettings"));
        MainWindow->setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar->setObjectName(QStringLiteral("statusbar"));
        MainWindow->setStatusBar(statusbar);
        toolBar = new QToolBar(MainWindow);
        toolBar->setObjectName(QStringLiteral("toolBar"));
        MainWindow->addToolBar(Qt::TopToolBarArea, toolBar);

        menubar->addAction(menuFile->menuAction());
        menubar->addAction(menuSettings->menuAction());
        menuFile->addAction(actionAdd_File);
        menuFile->addAction(actionAdd_Slide);
        menuFile->addAction(actionAdd_Countdown);
        menuFile->addAction(actionAdd_Ticker);
        menuFile->addAction(actionDelete);
        menuFile->addSeparator();
        menuFile->addAction(actionShow);
        menuFile->addAction(actionShow_Next);
        menuSettings->addAction(actionSettings);
        toolBar->addAction(actionAdd_File);
        toolBar->addAction(actionAdd_Slide);
        toolBar->addAction(actionAdd_Countdown);
        toolBar->addAction(actionAdd_Ticker);
        toolBar->addAction(actionDelete);
        toolBar->addSeparator();
        toolBar->addAction(actionShow);
        toolBar->addAction(actionShow_Next);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QApplication::translate("MainWindow", "Beamer", 0));
        actionAdd_File->setText(QApplication::translate("MainWindow", "Add File", 0));
#ifndef QT_NO_TOOLTIP
        actionAdd_File->setToolTip(QApplication::translate("MainWindow", "Add new file", 0));
#endif // QT_NO_TOOLTIP
        actionAdd_File->setShortcut(QApplication::translate("MainWindow", "Ctrl+A", 0));
        actionDelete->setText(QApplication::translate("MainWindow", "Delete", 0));
#ifndef QT_NO_TOOLTIP
        actionDelete->setToolTip(QApplication::translate("MainWindow", "Delete selected items", 0));
#endif // QT_NO_TOOLTIP
        actionDelete->setShortcut(QApplication::translate("MainWindow", "Del", 0));
        actionShow->setText(QApplication::translate("MainWindow", "Show", 0));
#ifndef QT_NO_TOOLTIP
        actionShow->setToolTip(QApplication::translate("MainWindow", "Show selected item", 0));
#endif // QT_NO_TOOLTIP
        actionShow->setShortcut(QApplication::translate("MainWindow", "Ctrl+S", 0));
        actionShow_Next->setText(QApplication::translate("MainWindow", "Show Next", 0));
#ifndef QT_NO_TOOLTIP
        actionShow_Next->setToolTip(QApplication::translate("MainWindow", "Show next item", 0));
#endif // QT_NO_TOOLTIP
        actionShow_Next->setShortcut(QApplication::translate("MainWindow", "Ctrl+N", 0));
        actionSettings->setText(QApplication::translate("MainWindow", "Settings", 0));
#ifndef QT_NO_TOOLTIP
        actionSettings->setToolTip(QApplication::translate("MainWindow", "Open Settings", 0));
#endif // QT_NO_TOOLTIP
        actionAdd_Countdown->setText(QApplication::translate("MainWindow", "Add Countdown", 0));
#ifndef QT_NO_TOOLTIP
        actionAdd_Countdown->setToolTip(QApplication::translate("MainWindow", "Add new countdown", 0));
#endif // QT_NO_TOOLTIP
        actionAdd_Ticker->setText(QApplication::translate("MainWindow", "Add Ticker", 0));
        actionAdd_Slide->setText(QApplication::translate("MainWindow", "Add Slide", 0));
#ifndef QT_NO_TOOLTIP
        actionAdd_Slide->setToolTip(QApplication::translate("MainWindow", "Add new slide", 0));
#endif // QT_NO_TOOLTIP
        menuFile->setTitle(QApplication::translate("MainWindow", "File", 0));
        menuSettings->setTitle(QApplication::translate("MainWindow", "Settings", 0));
        toolBar->setWindowTitle(QApplication::translate("MainWindow", "toolBar", 0));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
