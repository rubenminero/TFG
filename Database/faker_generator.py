from faker import Faker
from random import *
from datetime import datetime

import bcrypt

class Data_Generator:

    def __init__(self):
        self._user_id_counter = 0

    def generate_user(self, fake):
        user = fake.unique
        first_name, last_name = user.name().split(' ', 1)
        user = {
            # Campos comunes para usuarios
            'first_name': first_name,
            'last_name': last_name,
            'email': user.email(),
            'nif': user.nif(),
            'username': user.user_name(),
            'password': user.password(),
        }
        return user

    def generate_admins(self, fake, n,id):
        admins = []
        for i in range(n):
            id += 1
            admin = self.generate_user(fake)
            admin['id'] = id
            admin['role'] = 'ADMIN'
            admin['firstname'] = fake.first_name()
            admin['lastname'] = fake.last_name()
            admin['nif'] = fake.nif()
            admin['username'] = 'admin' + str(id) + 'SPM'
            admin['password'] = bcrypt.hashpw(admin['username'].encode('utf-8'),bcrypt.gensalt())
            admin['email'] = admin['username'] + '@gmail.com'
            admin['valid_from'] = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            admin['valid_to'] = None
            admins.append(admin)
        return admins

    def generate_athletes(self, fake, n, id):
        athletes = []
        for i in range(n):
            id += 1
            athlete = self.generate_user(fake)
            athlete['id'] = id
            athlete['role'] = 'ATHLETE'
            athlete['firstname'] = fake.first_name()
            athlete['lastname'] = fake.last_name()
            athlete['nif'] = fake.nif()
            athlete['username'] = 'athlete' + str(id)
            athlete['password'] = bcrypt.hashpw(athlete['username'].encode('utf-8'),bcrypt.gensalt())
            athlete['email'] = athlete['username'] + '@gmail.com'
            athlete['phone_number'] = fake.phone_number()
            athlete['enabled'] = True
            athletes.append(athlete)
        return athletes

    def generate_organizers(self, fake, n, id):
        organizers = []
        for i in range(n):
            id += 1
            organizer = self.generate_user(fake)
            organizer['id'] = id
            organizer['role'] = 'ORGANIZER'
            organizer['firstname'] = fake.first_name()
            organizer['lastname'] = fake.last_name()
            organizer['nif'] = fake.nif()
            organizer['username'] = 'organizer' + str(id)
            organizer['password'] = bcrypt.hashpw(organizer['username'].encode('utf-8'),bcrypt.gensalt())
            organizer['email'] = organizer['username'] + '@gmail.com'
            organizer['company_name'] = fake.company()
            organizer['address'] = fake.address()
            organizer['enabled'] = True
            organizers.append(organizer)
        return organizers


if __name__ == '__main__':
  fake = Faker("es_ES")
  generator = Data_Generator()
  admins = generator.generate_admins(fake,10,0)
  athletes = generator.generate_athletes(fake,10,10)
  organizers = generator.generate_organizers(fake,10,20)        
  print(f"Admins: \n{admins}")
  print(f"Atletas: \n{athletes}")
  print(f"Organizadores: \n{organizers}")
 