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
            admin['firstname'] = fake.first_name().encode('utf-8')
            admin['lastname'] = fake.last_name().encode('utf-8')
            admin['nif'] = fake.nif().encode('utf-8')
            admin['username'] = 'admin' + str(id) + 'SPM'.encode('utf-8')
            admin['password'] = bcrypt.hashpw(admin['username'].encode('utf-8'),bcrypt.gensalt())
            admin['email'] = admin['username'] + '@gmail.com'.encode('utf-8')
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
            athlete['firstname'] = fake.first_name().encode('utf-8')
            athlete['lastname'] = fake.last_name().encode('utf-8')
            athlete['nif'] = fake.nif().encode('utf-8')
            athlete['username'] = 'athlete' + str(id).encode('utf-8')
            athlete['password'] = bcrypt.hashpw(athlete['username'].encode('utf-8'),bcrypt.gensalt())
            athlete['email'] = athlete['username'] + '@gmail.com'.encode('utf-8')
            athlete['phone_number'] = fake.phone_number().encode('utf-8')
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
            organizer['firstname'] = fake.first_name().encode('utf-8')
            organizer['lastname'] = fake.last_name().encode('utf-8')
            organizer['nif'] = fake.nif().encode('utf-8')
            organizer['username'] = 'organizer' + str(id).encode('utf-8')
            organizer['password'] = bcrypt.hashpw(organizer['username'].encode('utf-8'),bcrypt.gensalt())
            organizer['email'] = organizer['username'] + '@gmail.com'.encode('utf-8')
            organizer['company_name'] = fake.company().encode('utf-8')
            organizer['address'] = fake.address().encode('utf-8')
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
 