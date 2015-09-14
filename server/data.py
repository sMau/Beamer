import sqlite3

__DB_PATH = '' #TODO implement db stuff etc

__db_connection = sqlite3.connect(__DB_PATH)

automode = False
media = set()
ticker_elements = set()
gui_config = dict()


def serialize_all():
    serialize_gui_config()
    serialize_media()
    serialize_ticker()

def serialize_media():
    raise NotImplementedError('')

def serialize_ticker():
    raise NotImplementedError('')

def serialize_gui_config():
    raise NotImplementedError('')

def load_all():
    load_gui_config()
    load_media()
    load_ticker()

def load_gui_config():
    raise NotImplementedError('')

def load_media():
    raise NotImplementedError('')

def load_ticker():
    raise NotImplementedError('')