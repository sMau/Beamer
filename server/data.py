import random
from os.path import expanduser

BASE_PATH = expanduser('~') + '/.beamersoftware_server/'
MEDIA_PATH = BASE_PATH + 'media/'
__DB_PATH = BASE_PATH + 'database.db'  # TODO implement db stuff etc

# __db_connection = sqlite3.connect(__DB_PATH)

automode = False
media = set()
ticker_elements = set()
config = dict()


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


def get_rand_media():
    """
    Only respects enabled media
    :return: random, enabled element from set media
    """
    return (random.choice([m for m in list(media) if m.enabled])).id


def get_media_by_id(id):
    for m in media:
        if m.id == id:
            return m


def get_ticker_elt_by_id(id):
    for t in ticker_elements:
        if t.id == id:
            return t
