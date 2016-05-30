import unittest

from client.gui import ClientGUIImpl
import client.controller as client_ctrl
from commons import recv_files, send_files
from server.gui import ServerGUIImpl
import server.controller as server_ctrl


class FileSend(unittest.TestCase):

    def setUp(self):
        gui = ServerGUIImpl()
        server_ctrl.tear_up(gui)

        gui = ClientGUIImpl(None, None, None)  # TODO!!
        client_ctrl.tear_up(gui)

        recv_files.init()
        send = send_files.SendFiles("127.0.0.1")
        send.transfer_file('test', '/Users/samuel/Documents/0001-ARV-2016-9825.PDF')


    def tearDown(self):
        pass


    def test_connect_disconnect(self):
        self.assertEqual(True, False)




if __name__ == '__main__':
    unittest.main()
