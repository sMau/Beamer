SEQ_DEF_VALUE = 0
CMD_UNDEFINED = 0

class Msg(object):
    def __init__(self):
        self.seq_no = SEQ_DEF_VALUE
        self.file_transfer = False
        self.ack = False
        self.command = True
        self.init_msg = False
        self.cmd_id = CMD_UNDEFINED
        self.data = [] #TODO last worked here

    def pack(self):
        '''
        :return: JSON string representation of a msg object
        '''
        raise NotImplementedError('')

def unpack(json_msg):
    '''
    Unpacks a JSON string to a msg object.
    :param json_msg: message in JSON representation
    :return: valid message object
    '''
    raise NotImplementedError('')