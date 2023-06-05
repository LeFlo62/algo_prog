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

# Filter elements that are not of type "node" and have valid "type" values
allowed_tags = ["historic", "name", "start_date", "tourism", "alt_name", "description", "artist_name", "artwork_type", "wikidata"]
disallowed_types = ["apartment", "guest_house", "hotel", "yes", "motel", "hostel", "painting", "mosaic", "graffiti", "streetart", "architecture"]

elements = data['elements']
filtered_elements = []

# Connect to MongoDB
client = MongoClient(os.environ["MONGO_URL"])
db = client.algoprog
collection = db.test

# Count occurrences of "artwork_type" and "tourism" values
artwork_type_counts = {}
tourism_counts = {}

# Count occurrences in elements
for element in elements:
    artwork_type = element['tags'].get('artwork_type')
    tourism = element['tags'].get('tourism')

    if artwork_type:
        artwork_type_counts[artwork_type] = artwork_type_counts.get(artwork_type, 0) + 1

    if tourism:
        tourism_counts[tourism] = tourism_counts.get(tourism, 0) + 1

# Insert each filtered element as a separate document in MongoDB
for element in elements:
    if element['tags'].get('tourism') in disallowed_types or element['tags'].get('type') in disallowed_types:
        continue

    filtered_tags = {}
    for key in allowed_tags:
        if key in element['tags']:
            filtered_tags[key] = element['tags'][key]

    # Add "TEST" key if either "artwork_type" or "tourism" tag exists and value occurs more than 5 times
    artwork_type = element['tags'].get("artwork_type")
    tourism = element['tags'].get("tourism")

    if artwork_type and artwork_type_counts.get(artwork_type, 0) > 5:
        filtered_tags["TEST"] = artwork_type
    elif tourism and tourism_counts.get(tourism, 0) > 5:
        filtered_tags["TEST"] = tourism

    filtered_element = {
        '_id': element['id'],
        'lat': element.get('lat'),
        'lon': element.get('lon'),
        'name': element['tags'].get('name'),
        'type': filtered_tags.get('TEST')
    }

    if 'description' in element['tags']:
        filtered_element['description'] = element['tags']['description']

    # Check if mandatory fields are present and type is not disallowed
    if filtered_element['_id'] and filtered_element['lat'] and filtered_element['lon'] and filtered_element['name'] and filtered_element['type'] and filtered_element['type'] not in disallowed_types:
        collection.insert_one(filtered_element)

print("done")
