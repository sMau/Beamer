login_name = '1'
login_pw = '2'
host, control_port, file_port = '127.0.0.1', 11111, 11112
media = []
ticker_elements = []
templates = set()
gui_config = dict()


def serialize_gui_config():
    """
    serializes the gui configuration dictonary
    :return: void
    """
    raise NotImplementedError('')


def load_gui_config():
    """
    loads gui configuration from disk
    :return:
    """
    raise NotImplementedError('')
