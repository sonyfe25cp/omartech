# -*- coding: UTF-8 -*-
__author__ = 'omar'

import os
import logging

import MySQLdb


FORMAT = '%(asctime)-15s [%(funcName)s] %(message)s'
logging.basicConfig(format=FORMAT, level=logging.INFO)


class Beauty:
    def __init__(self, id, title, count):
        self.id = id
        self.title = title
        self.total = count

    @staticmethod
    def save(array, db):
        c = 0
        for b in array:
            sql = '''insert into taotu(`meizi_id`, `title`, `total`) values (%d, '%s', %d) ''' % (b.id, b.title, b.total)
            cursor = db.cursor()
            cursor.execute(sql)
            c += 1
        logging.info('total save %d beauties.', c)
        db.commit()
        db.close()


def open_db(conf, db):
    host, port, user, pwd = conf.split('|')
    return MySQLdb.connect(host=host, user=user, passwd=pwd, port=int(port),
                           db=db, charset='utf8')


taotu_db = '127.0.0.1|7890|root|root1234'

folder = "full"


def fetchBeauties(folder):
    beauties = []
    for image_folder in os.listdir(folder):
        image_folder_path = os.path.join(folder, image_folder)
        if os.path.isdir(image_folder_path):
            image_id = int(image_folder.split('_')[0])
            image_name = ''.join(image_folder.split('_')[-1])
            print image_id, image_name
            max = 0
            for image in os.listdir(image_folder_path):
                file_name = image.split('.')[0]
                if len(file_name) > 0:
                    image_num = int(image.split('.')[0])
                    if max < image_num:
                        max = image_num
            beauty = Beauty(image_id, image_name, max)
            beauties.append(beauty)
    return beauties


def main():
    list = fetchBeauties(folder)
    connection = open_db(taotu_db, 'beauty')
    print 'fetch', len(list), 'beauties..'
    Beauty.save(list, connection)


if __name__ == '__main__':
    main()


