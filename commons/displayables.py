from abc import ABCMeta, abstractmethod
import uuid

TYPE_UNDEFINED = -1
TYPE_COUNTDOWN = 0
TYPE_IMAGE = 1
TYPE_VIDEO = 2

def type_to_text(type):
    switcher = {
        -1: 'Undefined',
        0: 'Countdown',
        1: 'Image',
        2: 'Video'
    }
    return switcher.get(type, 'undefined')

def _generate_random_uuid():
    return uuid.uuid4()


class MainDisplayable(metaclass=ABCMeta):

    def __init__(self, name, duration=0):
        self.id = _generate_random_uuid()
        self.name = name
        self.show_count = 0
        self.background_color = 0
        self.enabled = True
        self.duration = duration
        self.__type = self.__determine_type()

    @abstractmethod
    def __determine_type(self):
        return TYPE_UNDEFINED


class Countdown(MainDisplayable):

    # TODO choose good formating for colors and select proper defaults for countdown
    def __init__(self, name, duration, background_color=0, foreground_color=0):
        super().__init__(id, name, duration)

    def __determine_type(self):
        return TYPE_COUNTDOWN

class MediaFile(MainDisplayable):

    def __init__(self, name, path):
        super().__init__(id, name)
        self.path = path

    def __determine_type(self):
        return TYPE_IMAGE # TODO

    def get_preview(self):
        raise NotImplementedError('Get Preview is not implemented yet.') #TODO


class TickerDisplayable(metaclass=ABCMeta):

    def __init__(self):
        self.id = _generate_random_uuid()
        self.enabled = True


class TickerTxtElt(TickerDisplayable):

    def __init__(self, text):
        super.__init__()
        self.text = text

