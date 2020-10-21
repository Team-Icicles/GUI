import csv

csv_filepath = 'demographic_anomalies.csv'
output_filepath = 'identified_errors.csv'

csv_columns = ['id', 'error_type']
csv_dict = []

with open(csv_filepath, 'r') as csvfile:
    csv_reader = csv.DictReader(csvfile)
    for rows in csv_reader:
        pop100 = rows['pop100']
        precinct_id = rows['id']
        if(pop100 == '0'):
            row_dict = {}
            row_dict['id'] = precinct_id
            row_dict['error_type'] = 'DEMANOM'
            csv_dict.append(row_dict)

with open(output_filepath, 'w',  newline = '') as csvfile:
    write = csv.DictWriter(csvfile, fieldnames = csv_columns)
    write.writeheader()
    for row in csv_dict:
        write.writerow(row)

    print('exported demographic errors')


            
    
