import random
import uuid
from os.path import expanduser

from commons import displayables

BASE_PATH = expanduser('~') + '/.beamersoftware_server/'
MEDIA_PATH = BASE_PATH + 'media/'
LOG_PATH = BASE_PATH + 'log/'
__DB_PATH = BASE_PATH + 'database.db'  # TODO implement db stuff etc

# __db_connection = sqlite3.connect(__DB_PATH)

_automode = False
_media = set()
_ticker_elements = set()
_config = dict()


def add_media(m : displayables.MediaFile):
    """
    Add a media to the set.
    :param m: media to add
    :return: media file itself
    """
    _media.add(m)
    return m

def add_ticker_txt_elt(t : displayables.TickerTxtElt):
    """

    :param t:
    :return:
    """
    _ticker_elements.add(t)
    return t


def remove_main_displayable(id : uuid):
    m = get_media_by_id(id)
    if m in _media:
        _media.remove(m)
        return True
    return False


def remove_ticker_displayble(id : uuid):
    t = get_ticker_elt_by_id(id)
    if t in _ticker_elements:
        _ticker_elements.remove(t)
        return True
    return False


def property_update(key, val):
    _config[key] = val


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
    return (random.choice([m for m in list(_media) if m.enabled])).id


def get_media_by_id(id):
    for m in _media:
        if m.id == id:
            return m


def get_ticker_elt_by_id(id):
    for t in _ticker_elements:
        if t.id == id:
            return t
