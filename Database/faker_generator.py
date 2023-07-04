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
            athlete['firstname'] = fake.first_name().encode('utf-8')
            athlete['lastname'] = fake.last_name().encode('utf-8')
            athlete['nif'] = fake.nif().encode('utf-8')
            athlete['username'] = 'athlete' + str(id)
            athlete['password'] = bcrypt.hashpw(athlete['username'].encode('utf-8'),bcrypt.gensalt())
            athlete['email'] = athlete['username'] + '@gmail.com'
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
            organizer['username'] = 'organizer' + str(id)
            organizer['password'] = bcrypt.hashpw(organizer['username'].encode('utf-8'),bcrypt.gensalt())
            organizer['email'] = organizer['username'] + '@gmail.com'
            organizer['company_name'] = fake.company().encode('utf-8')
            organizer['address'] = fake.address().encode('utf-8')
            organizer['enabled'] = True
            organizers.append(organizer)
        return organizers

    def generate_tournaments(self, fake, n, id):
        tournaments = []
        for i in range(n):
            id += 1
            tournament = {}
            tournament['id'] = id
            tournament['address'] = fake.address().encode('utf-8')
            tournament['description'] = fake.text().encode('utf-8')
            tournament['name'] = fake.name().encode('utf-8')
            tournament['enabled'] = True
            tournament['location'] = fake.city().encode('utf-8')
            tournament['id_organizers'] = 22
            tournament['id_sports_type'] = 1
            tournament['capacity'] = 100
            tournament['inscription'] = True
            tournaments.append(tournament)
        return tournaments

    def generate_events(self, fake, n, id):
        events = []
        for i in range(n):
            id += 1
            event = {}
            event['id'] = id
            event['address'] = fake.address().encode('utf-8')
            event['description'] = fake.text().encode('utf-8')
            event['name'] = fake.name().encode('utf-8')
            event['enabled'] = True
            event['location'] = fake.city().encode('utf-8')
            event['id_organizers'] = 22
            event['id_sports_type'] = 2
            event['capacity'] = -1
            event['inscription'] = False
            events.append(event)
        return events

if __name__ == '__main__':
  fake = Faker("es_ES")
  generator = Data_Generator()
  admins = generator.generate_admins(fake,10,0)
  athletes = generator.generate_athletes(fake,10,10)
  organizers = generator.generate_organizers(fake,10,20)
  tournaments = generator.generate_tournaments(fake,10,0)         
  events = generator.generate_events(fake,10,0)     
  print(f"Admins: \n{admins}")
  print(f"Atletas: \n{athletes}")
  print(f"Organizadores: \n{organizers}")
  print(f"Torneos: \n{tournaments}")
  print(f"Eventos: \n{events}")
 