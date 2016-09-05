import json


SEQ_DEF_VALUE = 0


KEY_INIT_MSG = 'init_msg'
KEY_SEQUENCE_NO = 'seq_no'
KEY_FILE_TRANSFER = 'file_transfer'
KEY_CMD_ID = 'cmd_id'
KEY_ACK = 'ack'
KEY_DATA = 'data'


class Type:

    CMD_UNDEFINED = -1
    CMD_CONNECT = 0
    CMD_DISPLAY = 1
    CMD_DISPLAY_NEXT = 2
    CMD_PROP_UPDATE = 3

    ADD_FILE = 100
    ADD_COUNTDOWN = 103
    ADD_TICKER_TXTELT = 101
    REMOVE_MAIN_DISPLAYABLE = 102
    REMOVE_TICKER_DISPLAYABLE = 104


class Msg(object):
    """
    Object representation of a network layer message. For transfer its converted to JSON.

    Add file: 0 -> file_id, 1 -> displayable name, 2 -> type vid/img
    Add file ack: 0 -> MediaFile(MainDisplayable)
    Add countdown 0 -> name, 1 -> duration
    Add ticket txt elt 0 -> text
    Remove x: 0 -> id
    """
    def __init__(self, *args, file_transfer=0, ack=0, cmd_id=Type.CMD_UNDEFINED):
        self.seq_no = SEQ_DEF_VALUE
        self.file_transfer = file_transfer
        self.ack = ack
        self.cmd_id = cmd_id
        self.init_msg = 0
        self.data = list(args)

    def pack(self):
        """
        :return: JSON string as bytes representation of a msg object
        """
        json_bytes = self.to_json().encode()
        return len(json_bytes), json_bytes

    def to_json(self):
        """

        :return: JSON formatted string from a obj
        """

        return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)