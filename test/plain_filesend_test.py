from commons import recv_files, send_files
from server import data


if __name__ == '__main__':
    recv_files.init(data.MEDIA_PATH)
    send = send_files.SendFiles('127.0.0.1')
    send.transfer_file('test', '/Users/samuel/Downloads/Shameless-S06-7p-TVP-SO/Shameless.S06E02.Weg.damit.GERMAN.DL.DUBBED.720p.HDTV.x264-TVP/tvp-shameless-s06e02-720p.mkv')
