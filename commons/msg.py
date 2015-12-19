import json

SEQ_DEF_VALUE = 0
CMD_UNDEFINED = 0
CMD_CONNECT = 1


class Msg(object):
    """
    Object representation of a network layer message. For transfer its converted to JSON.
    """
    def __init__(self, file_transfer=0, ack=0, cmd_id=CMD_UNDEFINED):
        self.seq_no = SEQ_DEF_VALUE
        self.file_transfer = file_transfer
        self.ack = ack
        self.cmd_id = cmd_id
        self.init_msg = 0
        self.data = []

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
