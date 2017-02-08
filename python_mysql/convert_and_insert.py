
import libxml2
import sys
import os
import commands
import re
import sys

import MySQLdb

from xml.dom.minidom import parse, parseString

# for converting dict to xml 
from cStringIO import StringIO
from xml.parsers import expat


def get_elms_for_atr_val(tag,atr,val):
   lst=[]
   
   elms = dom.getElementsByTagName(tag)
   
   for x in elms:
      if len(x.childNodes)==101:
         tab=x
   elements=tab.childNodes
   lst=elements[1:]


   return lst

# get all text recursively to the bottom
def get_text(e):
   lst=[]
   total=[]
   if e.nodeType in (3, 4):
      total.append(e.nodeValue)
   else:
      l=e.childNodes
      for x in l:
         total = get_text(x) + total

   return total

# replace whitespace chars
def replace_white_space(str):
   p = re.compile(r'\s+')
   new = p.sub(' ',str)   # a lot of \n\t\t\t\t\t\t
   return new.strip()

# replace but these chars including ':'
def replace_non_alpha_numeric(s):
   p = re.compile(r'[^a-zA-Z0-9:-]+')
   #   p = re.compile(r'\W+') # replace whitespace chars
   new = p.sub(' ',s)
   return new.strip()

# convert to xhtml
# use: java -jar tagsoup-1.2.jar --files html_file
def html_to_xml(fn):
   
   cmd="java -jar tagsoup-1.2.1.jar --files " + fn
   os.system(cmd)
   ret=fn+".xhtml"
   return ret

def extract_values(dm):
   lst = []
   l = get_elms_for_atr_val('table','class','most_actives')
   for x in range(0, 100):
      temp=(get_text(l[x]))
      for x in (7, 5, 3, 2 , 1, 0):
         lst.append(replace_white_space(temp[x]))
   # ............
   #    get_text(e)
   # ............
   return lst

# mysql> describe most_active;
def insert_to_db(l,tbl):
   import mysql.connector
   db=MySQLdb.connect("localhost", "root", "root", "cs288")
   cursor=db.cursor()
   cmd="drop table if exists " + tbl
   cursor.execute(cmd);
   cmd="create table " + tbl + " (rank int, name varchar(80), volume varchar(20), price varchar(10), chg varchar(6), perChg varchar(20));"
   cursor.execute(cmd)
   name=tbl
   i=0
   insertcmd="INSERT INTO " + tbl + " (rank, name, volume, price, chg, perChg) VALUES("
   current=""
   #print "INSERT INTO %s VALUES (%s, %s, %s, %s, %s, %s, %s)",(name, l[0], l[1], l[2], l[3], l[4], l[5])



   while(i < 594):
      cursor.execute("insert into " + tbl+ " values (%s, %s, %s, %s, %s, %s)" ,(l[i], l[i+1], l[i+2], l[i+3], l[i+4], l[i+5]))
      i=i+6

   cursor.execute("insert into " + tbl+ " values (%s, %s, %s, %s, %s, %s)" ,(l[594], l[595], l[596], l[597], l[598], l[599]))

   db.commit()
   db.close()
   return 0
   # ............


def main():
   html_fn = sys.argv[1]
   fn = html_fn.replace('.html','')
   xhtml_fn = html_to_xml(html_fn)

   global dom
   dom = parse(xhtml_fn)

   lst = extract_values(dom)

   

   cursor = insert_to_db(lst,fn) 

   #l = select_from_db(cursor,fn) 
   return 0
   #return xml

if __name__ == "__main__":
   main()

# end of hw7.py