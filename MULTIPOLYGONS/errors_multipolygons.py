import csv
import json

output_filepath = 'identified_errors.csv'
multipolygon_filepath = 'multipolygons.json'

csv_columns = ['id', 'error_type']
csv_dict = []

with open(multipolygon_filepath, 'r') as json_file:
    data = json.load(json_file)
    for d in data['features']:
        mp_dict = {}
        precinct_id = int(d['properties']['id'])
        mp_dict['id'] = precinct_id
        mp_dict['error_type'] = 'MULTI'
        csv_dict.append(mp_dict)
        
with open(output_filepath, 'w',  newline = '') as csvfile:
    write = csv.DictWriter(csvfile, fieldnames = csv_columns)
    write.writeheader()
    for row in csv_dict:
        write.writerow(row)
    
    print('exported multipolygon errors')

