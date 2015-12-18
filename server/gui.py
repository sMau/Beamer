from abc import ABCMeta, abstractmethod


class ServerGUI(metaclass=ABCMeta):
    def __init__(self):
        pass

    @abstractmethod
    def show_countdown(self, countdown):
        pass

    @abstractmethod
    def show_image(self, image):
        pass

    @abstractmethod
    def show_video(self, video):
        pass

    @abstractmethod
    def show_ticker_displayable(self, ticker_displayble):
        pass


class ServerGUIImpl(ServerGUI):
    def show_countdown(self, countdown):
        pass

    def show_video(self, video):
        pass

    def show_ticker_displayable(self, ticker_displayble):
        pass

    def show_image(self, image):
        pass
