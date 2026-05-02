import mysql.connector

def OceanDB():
    mydb = mysql.connector.connect(
    host="ocean-production.czhtcuge3v5t.us-east-1.rds.amazonaws.com",
    user="readonly",
    password="Qo3rQ9C{\"L3<ob{9", #CUIDADO! Tem um escape char -->\"<-- aqui!!!"
    database="ocean"
    )

    return mydb

   

class OceanDB:

    def __init__(self):
        self.mydb = mysql.connector.connect(
        host="ocean-production.czhtcuge3v5t.us-east-1.rds.amazonaws.com",
        user="readonly",
        password="Qo3rQ9C{\"L3<ob{9", #CUIDADO! Tem um escape char -->\"<-- aqui!!!"
        database="ocean"
        )

    def __del__(self):
        self.mydb.close()
    
    def getCursor(self):
        return  self.mydb.cursor()
