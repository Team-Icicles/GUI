import csv

dem_filepath = 'dem_errors.csv'
election_filepath = 'election_errors.csv'
output_filepath = 'identified_errors.csv'

csv_columns = ['id', 'error_type']
csv_dict = []

election_precincts = []
ghost_precincts = []

with open(election_filepath, 'r') as csvfile:
    csv_reader = csv.DictReader(csvfile)
    for rows in csv_reader:
        precinct_id = rows['id']
        election_precincts.append(precinct_id)

with open(dem_filepath, 'r') as csvfile:
    csv_reader = csv.DictReader(csvfile)
    for rows in csv_reader:
        precinct_id = rows['id']
        if(precinct_id in election_precincts):
            ghost_dict = {}
            ghost_dict['id'] = precinct_id
            ghost_dict['error_type'] = 'GHOST'
            csv_dict.append(ghost_dict)
            ghost_precincts.append(precinct_id)


with open(dem_filepath, 'r') as csvfile:
    csv_reader = csv.DictReader(csvfile)
    for rows in csv_reader:
        precinct_id = rows['id']
        if(precinct_id not in ghost_precincts):
            election_dict = {}
            election_dict['id'] = precinct_id
            election_dict['error_type'] = 'DEMANOM'
            csv_dict.append(election_dict)

with open(election_filepath, 'r') as csvfile:
    csv_reader = csv.DictReader(csvfile)
    for rows in csv_reader:
        precinct_id = rows['id']
        if(precinct_id not in ghost_precincts):
            election_dict = {}
            election_dict['id'] = precinct_id
            election_dict['error_type'] = 'VOTANOM'
            csv_dict.append(election_dict)
        

with open(output_filepath, 'w',  newline = '') as csvfile:
    write = csv.DictWriter(csvfile, fieldnames = csv_columns)
    write.writeheader()
    for row in csv_dict:
        write.writerow(row)

    print('exported demographic, election, and ghost errors')


            
    
