import unittest

from client.gui import ClientGUIImpl
import client.controller as client_ctrl
from commons import recv_files, send_files
from server.gui import ServerGUIImpl
import server.controller as server_ctrl


class FileSend(unittest.TestCase):

    def setUp(self):
        recv_files.init()
        send = send_files.SendFiles("127.0.0.1")
        send.transfer_file('test', '/Users/samuel/Downloads/Shameless-S06-7p-TVP-SO/Shameless.S06E02.Weg.damit.GERMAN.DL.DUBBED.720p.HDTV.x264-TVP/tvp-shameless-s06e02-720p.mkv')


    def tearDown(self):
        pass


    def test_connect_disconnect(self):




        self.assertEqual(True, False)




if __name__ == '__main__':
    unittest.main()
