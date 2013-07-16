#!/usr/bin/python3
import subprocess
import http.client, urllib.parse

#Configurations
machine_id="dc2-power"
#host="localhost:8080"
host="128.153.145.179"
url="/GDCDashboard/update"

#Send information

#vals = {machine_id+'.POWER_INPUT_I':5,\
#        machine_id+'.POWER_BTY_I':12,\
#        machine_id+'.POWER_INVRT_I':30,\
#        machine_id+'.POWER_BTY_V':22,\
#        machine_id+'.POWER_CHARGE':45 }
vals = {machine_id+".BTY_LEVEL":'BTY_NORMAL_LEVEL'}
params =','.join(['%s=%s'%(key,val) for (key,val) in vals.items()])
headers = {'Content-Type':'application/x-www-form-urlencoded', 'Accept':'text/plain'}

conn = http.client.HTTPConnection(host)

conn.request("POST", url, params, headers)
conn.close()
