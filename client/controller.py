import client.gui
import client.log

connection = None
gui = None


def connect(client_gui):
    global gui, connection
    gui = client_gui
    # do some connect stuff here TODO


def __send_msg(msg):
    raise NotImplementedError


def add_file(path):
    raise NotImplementedError


def add_ticker_elt(*args):
    raise NotImplementedError


def remove_something(id):
    raise NotImplementedError


def property_update(key, value):
    raise NotImplementedError


def receive(msg):
    raise NotImplementedError


def __something_removed(id):
    raise NotImplementedError


def __automode_changed(automode):
    raise NotImplementedError


def __login_success():
    gui.login_success()
    raise NotImplementedError
