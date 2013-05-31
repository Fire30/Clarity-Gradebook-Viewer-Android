import urllib, urllib2,json,string,httplib,time,logging
from BeautifulSoup import BeautifulSoup
headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11',#need it to think we are using browser
        'Content-Type': 'application/x-www-form-urlencoded',
        'Cookie': 'InternetViewer.SchoolId='
    }
req = urllib2.Request('https://loudoun.gradebook.net/Pinnacle/PIV/UpcomingAssignments.aspx',
                          None,headers)
soup = BeautifulSoup(urllib2.urlopen(req).read())
soup = BeautifulSoup(soup.prettify())
the_json = [];
for school_class in soup.findAll('select',{'name':'uxSchools'}):
    for schoolName in school_class.findAll('option'):
        print('map.put("%s","%s");') % (schoolName['title'],schoolName['value'])
#print soup.prettify()
