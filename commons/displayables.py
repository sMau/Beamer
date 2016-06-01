import uuid
from abc import ABCMeta, abstractmethod

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
        self.background_color = 0
        self.enabled = True
        self.duration = duration
        self.type = None

    def __str__(self):
        return self.id + ', ' + self.name + ', ' + type_to_text(self.type) + ', ' + self.duration


class Countdown(MainDisplayable):
    """
    Countdown text-based, displayable in the main frame.
    """
    # TODO choose good formating for colors and select proper defaults for countdown
    def __init__(self, name, duration, background_color=0, foreground_color=0):
        super(Countdown, self).__init__(name, duration=duration)
        self.type = TYPE_COUNTDOWN



class MediaFile(MainDisplayable):
    """
    Media file, i.e., a video or a image file displayable in the main frame.
    """
    def __init__(self, name, path, type=TYPE_IMAGE):
        super(MediaFile, self).__init__(id, name)
        self.path = path
        self.type = type


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
        return self.id


class TickerTxtElt(TickerDisplayable):
    """
    Text elements displayable in the beamers ticker frame.
    """
    def __init__(self, text):
        super(TickerTxtElt, self).__init__()
        self.text = text

    def __str__(self):
        return super(TickerTxtElt, self).__str__() + ', ' + self.text
