import uuid
from abc import ABCMeta, abstractmethod

IMAGE_EXTENSIONS_VALID = ('.png', '.jpg', '.jpeg', '.tiff', '.bmp')
VIDEO_EXTENSIONS_VALID = ('.mkv', '.mv4', '.mp4', '.mpeg', '.mpg', '.avi', '.flv')

TYPE_UNDEFINED = -1
TYPE_COUNTDOWN = 0
TYPE_IMAGE = 1
TYPE_VIDEO = 2


def type_to_text(media_type):
    """
    Converts from the media type as int to a human readable string representation.
    :param media_type: type as int
    :return: text based representation of the media type
    """
    switcher = {
        -1: 'Undefined',
        0: 'Countdown',
        1: 'Image',
        2: 'Video'
    }
    return switcher.get(media_type, 'undefined')


def _generate_random_uuid():
    """
    :return: random uuid 128 Bit
    """

    return uuid.uuid4()

def determine_file_type(file: str):
    if file.lower().endswith(IMAGE_EXTENSIONS_VALID):
        return TYPE_IMAGE
    elif file.lower().endswith(VIDEO_EXTENSIONS_VALID):
        return TYPE_VIDEO


class MainDisplayable(metaclass=ABCMeta):
    """
    Displayable in the main frame of the Beamer.
    """
    def __init__(self, name, duration=0):
        """

        :param name: name to show to the clients
        :param duration: duration to show
        :return:
        """
        self.id = _generate_random_uuid()
        self.name = name
        self.show_count = 0
        self.background_color = (0,0,0)
        self.enabled = True
        self.duration = duration
        self.type = None

    def __str__(self):
        return str(self.id) + ', ' + self.name + ', ' + type_to_text(self.type) + ', ' + str(self.duration)


class Countdown(MainDisplayable):
    """
    Countdown text-based, displayable in the main frame.
    """
    def __init__(self, name: str, duration: int, background_color: (int,int,int)=(0,0,0), foreground_color: (int,int,int)=(255,255,255), from_dict_magic: dict=None):
        super(Countdown, self).__init__(name, duration=duration)
        self.type = TYPE_COUNTDOWN
        self.background_color = background_color
        self.foreground_color = foreground_color

        if from_dict_magic:
            self.__dict__.update(from_dict_magic)


class MediaFile(MainDisplayable):
    """
    Media file, i.e., a video or a image file displayable in the main frame.
    """
    def __init__(self, name: str, path: str, type=TYPE_IMAGE, from_dict_magic: dict=None):
        super(MediaFile, self).__init__(name)
        self.path = path
        self.type = type

        if from_dict_magic:
            self.__dict__.update(from_dict_magic)

    def get_preview(self):
        raise NotImplementedError('Get Preview is not implemented yet.')


class TickerDisplayable(metaclass=ABCMeta):
    """
    Objects displayble in the ticker frame of the beamer.
    """
    def __init__(self):
        self.id = _generate_random_uuid()
        self.enabled = True

    def __str__(self):
        return str(self.id)


class TickerTxtElt(TickerDisplayable):
    """
    Text elements displayable in the beamers ticker frame.
    """
    def __init__(self, text: str, from_dict_magic: dict=None):
        super(TickerTxtElt, self).__init__()
        self.text = text

        if from_dict_magic:
            self.__dict__.update(from_dict_magic)

    def __str__(self):
        return super(TickerTxtElt, self).__str__() + ', ' + self.text