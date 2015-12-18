from abc import ABCMeta, abstractmethod


class ClientGUI(metaclass=ABCMeta):
    """
    Class representing the GUI of the client in an abstract way providing only the interface.
    """
    def __init__(self, controller, settings_gui, slidecreator_gui):
        self.controller = controller
        self.settings = settings_gui
        self.slide_creator = slidecreator_gui

    @abstractmethod
    def server_shutdown(self):
        pass

    @abstractmethod
    def login_success(self):
        pass

    @abstractmethod
    def ticker_data_changed(self):
        pass

    @abstractmethod
    def media_data_changed(self):
        pass

    @abstractmethod
    def display_info(self, msg):
        pass

    @abstractmethod
    def display_error(self, msg, exc=None):
        pass

    @abstractmethod
    def automode_changed(self):
        pass


class ClientGUIImpl(ClientGUI):
    """
    Certain implementation of a Client GUI
    """
    def server_shutdown(self):
        pass

    def display_error(self, msg, exc=None):
        pass

    def media_data_changed(self):
        pass

    def automode_changed(self):
        pass

    def ticker_data_changed(self):
        pass

    def login_success(self):
        pass

    def display_info(self, msg):
        pass
