from faker_generator import Data_Generator
from faker import Faker
import argparse

parser = argparse.ArgumentParser(description='Database script to generate data for the app.')
parser.add_argument('--users', type=int, default=10, help='Number of users to generate', dest="users")
parser.add_argument('--admins', type=int, default=10, help='Number of admins to generate', dest="admins")
args = parser.parse_args()


def insert_user(user):
    sql_template = "INSERT INTO users (id, email, first_name, last_name, nif, password, role,username) VALUES ({},'{}', '{}', '{}', '{}', {}, '{}','{}');\n"
    return sql_template.format(user['id'], user['email'], user['first_name'], user['last_name'], user['nif'], user['password'], user['role'], user['username'])

def insert_admin(admin):
    sql_template = "INSERT INTO admins (valid_from, id) VALUES ('{}', {});\n"
    return sql_template.format(admin['valid_from'],admin['id'] )

def insert_athlete(athlete):
    sql_template = "INSERT INTO athletes (enabled,phone_number ,id) VALUES ({}, '{}' ,{});\n"
    return sql_template.format(athlete['enabled'],athlete['phone_number'],athlete['id'] )

def insert_organizer(organizer):
    sql_template = "INSERT INTO organisers (enabled,company_name,address ,id) VALUES ({}, '{}','{}',{});\n"
    return sql_template.format(organizer['enabled'],organizer['company_name'], organizer['address'] ,organizer['id'] )

def create_sql(file_name, n_sep):
    admins = Data_Generator().generate_admins(Faker("es_ES"),10,0)
    athletes = Data_Generator().generate_athletes(Faker("es_ES"),10,10)
    organizers = Data_Generator().generate_organizers(Faker("es_ES"),10,20)

    with open(file_name, "w") as file:
        for admin in admins:
            file.write(insert_user(admin) + insert_admin(admin) +"\n")
        file.write("\n")
        for athlete in athletes:
            file.write(insert_user(athlete) + insert_athlete(athlete) +"\n")  
        for organizer in organizers:
            file.write(insert_user(organizer) + insert_organizer(organizer) +"\n")  

create_sql("load.sql", 10)