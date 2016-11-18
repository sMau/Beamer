import json
import logging


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

    ADD_DISPLAYABLE_FILE = 100
    ADD_COUNTDOWN = 103
    ADD_TICKER_TXTELT = 101
    REMOVE_MAIN_DISPLAYABLE = 102
    REMOVE_TICKER_DISPLAYABLE = 104


class Msg(object):
    """
    Object representation of a network layer message. For transfer its converted to JSON.

    Add file: 0 -> displayable name, 1 -> type vid/img
    Add file ack: 0 -> MediaFile(MainDisplayable)
    Add countdown 0 -> name, 1 -> duration
    Add ticket txt elt 0 -> text
    Remove x: 0 -> id
    """
    def __init__(self, *args, file_transfer=0, ack=0, cmd_id=Type.CMD_UNDEFINED, from_dict_magic=None):

        self.seq_no = SEQ_DEF_VALUE
        self.file_transfer = file_transfer
        self.ack = ack
        self.cmd_id = cmd_id
        self.init_msg = 0
        self.data = list(args)

        if from_dict_magic:
            self.__dict__.update(from_dict_magic)


    def __str__(self):
        return 'CMD ID: {!s}, ACK: {!s}, FTrans: {!s}'.format(self.cmd_id, self.ack, self.file_transfer)

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

        return json.dumps(self, default=_json_parse_helper, sort_keys=True, indent=4)


def _json_parse_helper(o):
    """
    Function to parse any python object to a json parsable dict representation.
    :param o: to parse
    :return: dict representation of o
    """
    return o.__dict__

