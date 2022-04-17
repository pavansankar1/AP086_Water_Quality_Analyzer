import requests

url  = 'http://192.168.137.238:5000'

sensorValues = {}

id_ = None
def sendType(t):
    global id_
    data = {'type_':t}
    r = requests.post(url+'/sendType',json=data)
    status = r.status_code
    k = request_()
    id_ = k['id']
    return status

def request_():
    global sensorValues
    r = requests.get(url+'/requestDetails/')
    return r.json()


def requestDetails():
    global sensorValues
    r = requests.get(url+'/requestDetails/')
    data = r.json()
    if data['id']!=id_:
        print(1)
        sensorValues = data
        return "1"
    else:
        return "0"

def values(type_):
    return sensorValues[type_]


