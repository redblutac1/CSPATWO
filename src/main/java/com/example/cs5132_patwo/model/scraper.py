import requests
from bs4 import BeautifulSoup
import re
from google.protobuf.json_format import MessageToJson
from google.protobuf.message import Message
from google.protobuf.text_format import Parse

pairs = [("ord_dataset-d9b6cc4de43c4597b14030ffd44791bc","uspto-1995"),
         ("ord_dataset-df21fa35f2d54662b00b89e12450c75e","uspto-1993"),
         ("ord_dataset-83bfd1dac1b94c9eaa65b314baeb37d0","uspto-1997"),
         ("ord_dataset-e2103f90b283456b82024392b65719f5","uspto-2005"),
         ("ord_dataset-f400f3024cb541b6884630bbe6e9dace","uspto-2008"),
         ("ord_dataset-f6e510ee6abe48bb9f9ddd9a7153160c","uspto-1994"),
         ("ord_dataset-fa5a7d9322b445ab8e1efb1dfda208f2","uspto-1976"),
         ("ord_dataset-de0979205c84441190feef587fef8d6d","unknown-9"),
         ("ord_dataset-fc83743b978f4deea7d6856deacbfe53","deoxyfluorination")]

j = 1
for pair in pairs:
    URL = "http://localhost:5001/client/search?dataset_ids=" + pair[0] + "&limit=15000"
    page = requests.get(URL)

    soup = BeautifulSoup(page.content, "html.parser")
    script = soup.find_all("script")[4].text
    ids = re.findall("ord-[\w\d]+", script)

    outfile = open("new/" + pair[1] + ".txt", "w")
    i = 1

    for rxnid in ids:
        print(str(j) + "-" + str(i))
        rxnpage = requests.get("http://localhost:5001/client/id/" + rxnid)
        rxnsoup = BeautifulSoup(rxnpage.content, "html.parser")
        div = rxnsoup.find("div",id="pbtxt")
        json = "{" + div.find("pre").text + "}"
        json = json.replace(" "," ").replace("{", " { ").replace("}", " } ")
        json = re.sub("\s+", " ", json)
        outfile.write(json + "\n")
        i += 1
    outfile.close()
    j += 1