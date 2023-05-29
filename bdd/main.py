import requests
import json
import os
from pymongo import MongoClient

# Define the Overpass API query to retrieve data
overpass_url = "http://overpass-api.de/api/interpreter"
overpass_query = """
[out:json];
area[name="Paris"]->.boundaryarea;
(
  node["tourism"](area.boundaryarea);
  way["tourism"](area.boundaryarea);
  node["historic"](area.boundaryarea);
  way["historic"](area.boundaryarea);
);
out;
"""

# Make a GET request to the Overpass API
response = requests.get(overpass_url, params={'data': overpass_query})

# Convert the response to a JSON string
data = response.json()

# Export prefiltered data to a JSON file
with open('prefiltered_data.json', 'w') as outfile:
    json.dump(data, outfile)

# Filter elements that have a "start_date" field and are not of type "node"
allowed_tags = ["historic", "name", "start_date", "tourism", "alt_name", "description", "artist_name", "artwork_type", "wikidata"]

elements = data['elements']
filtered_elements = []

for element in elements:
    if element['tags'].get('tourism') == 'hotel':
        continue

    if 'start_date' not in element['tags']:
        continue

    filtered_tags = {}
    for key in allowed_tags:
        if key in element['tags']:
            filtered_tags[key] = element['tags'][key]

    filtered_element = {
        '_id': element['id'],
        'tags': filtered_tags
    }

    if element.get('lat') is not None and element.get('lon') is not None:
        filtered_element['lat'] = element['lat']
        filtered_element['lon'] = element['lon']

    filtered_elements.append(filtered_element)

# Connect to MongoDB
client = MongoClient(os.environ["MONGO_URL"])
db = client.algoprog
collection = db.test

# Insert each filtered element as a separate document in MongoDB
for element in filtered_elements:
    collection.insert_one(element)

print("done")