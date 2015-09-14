connections = dict()
timer = None #check out http://stackoverflow.com/questions/8600161/executing-periodic-actions-in-python


def receive_message(msg):
    raise NotImplementedError


def __add_file(file):
    raise NotImplementedError


def __add_countdown(coutdown):
    raise NotImplementedError


def __add_ticker_elt(elt):
    raise NotImplementedError


def __add_connection(id, con):
    global connections
    connections[id] = con


def __update_property(key, value):
    raise NotImplementedError


def shutdown_gracefully():
    raise NotImplementedError


def __remove_main_displayable_by_id(id):
    raise NotImplementedError


def __remove_ticker_displayble_by_id(id):
    raise NotImplementedError


def __display_main(id):
    raise NotImplementedError


def __display_next():
    raise NotImplementedError


def send_to_all(msg):
    raise NotImplementedError
