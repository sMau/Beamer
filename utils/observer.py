from abc import ABCMeta, abstractmethod


class Observer(object):
    __metaclass__ = ABCMeta


    @abstractmethod
    def notify(self, *args):
        pass


class Observable(object):

    def __init__(self):
        self.observers = []

    def subscribe(self, observer):
        if not observer in self.observers:
            self.observers.append(observer)

    def unsubscribe(self, observer):
        if observer in self.observers:
            self.observers.remove(observer)

    def unsubscribe_all(self):
        if self.observers:
            del self.observers[:]

    def notify(self, *args):
        for observer in self.observers:
            observer.notify(*args)