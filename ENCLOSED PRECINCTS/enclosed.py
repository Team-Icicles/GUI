import csv

neighbors_filepath = 'all_precinct_neighbors.csv'
output_filepath = 'enclosed_precincts.csv'

csv_columns = ['id', 'error_type']
csv_dict = []

precinct_sofar = {}

with open(neighbors_filepath, 'r') as csvfile:
    csv_reader = csv.DictReader(csvfile)
    first_row = True
    for rows in csv_reader:
        
        current = rows['id']
        
        if(first_row == True):
            precinct_sofar[current] = True
            first_row = False
            continue
        
        if(current in precinct_sofar.keys()):
            precinct_sofar[current] = False

        if(current not in precinct_sofar.keys()):
            precinct_sofar[current] = True

for key in precinct_sofar.keys():
    enclosed =precinct_sofar[key]
    if(enclosed == True):
        en_dict = {}
        en_dict['id'] = key
        en_dict['error_type'] = 'ENCLOSED'
        csv_dict.append(en_dict)

with open(output_filepath, 'w',  newline = '') as csvfile:
    write = csv.DictWriter(csvfile, fieldnames = csv_columns)
    write.writeheader()
    for row in csv_dict:
        write.writerow(row)
    print('finished printing enclosed precincts')
    
        
        
