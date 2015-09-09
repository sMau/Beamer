from abc import ABCMeta, abstractmethod

class MainDisplayable(metaclass = ABCMeta):

    def __init__(self, id, name):
        self.id = id
        self.name = name
        self.show_count = 0
        self.background_color = None
        self.duration = 0
        self.type = __determine_type(self)

    @abstractmethod
    def __determine_type(self):
        pass


class TickerDisplayable(metaclass = ABCMeta):
    pass


class TickerTxtElt(TickerDisplayable):
    pass


class Countdown(MainDisplayable):

    def __determine_type(self):
        return -1; #TODO

class MediaFile(MainDisplayable):

    def __init__(self):
        pass

    def __determine_type(self):
        pass