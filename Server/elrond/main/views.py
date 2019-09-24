from django.http import HttpResponse
from django.template import loader
from .functions import *
from django.core.mail import send_mail
import json
from django.http import JsonResponse

from django.shortcuts import render
from rest_framework import viewsets
from .models import *
from .serializers import *
from django.views.decorators.csrf import csrf_exempt
from random import randint
from django.utils import timezone
from django.utils.timezone import localtime 



####################################################################################################

class GroupView(viewsets.ModelViewSet):
    queryset = Group.objects.all()
    serializer_class = GroupSerializer

class ProfileView(viewsets.ModelViewSet):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer


class TweetView(viewsets.ModelViewSet):
    queryset = Tweet.objects.all()
    serializer_class = TweetSerializer


class TweetlikesView(viewsets.ModelViewSet):
    queryset = Tweetlikes.objects.all()
    serializer_class = TweetlikesSerializer

class TweetcommentsView(viewsets.ModelViewSet):
    queryset = Tweetcomments.objects.all()
    serializer_class = TweetcommentsSerializer



class InboxView(viewsets.ModelViewSet):
    queryset = Inbox.objects.all()
    serializer_class = InboxSerializer

class UsersView(viewsets.ModelViewSet):
    queryset = Users.objects.all()
    serializer_class = UsersSerializer




####################################################################################################

@csrf_exempt
def testing(request):
    body_unicode = request.body.decode('utf-8')
    body = json.loads(body_unicode)
    content = body['name']
    
    mydict = {}
    mydict["name"] = "Johnson"
    mydict["age"] = "Siziba"
    #myJson = json.dumps(mydict)
    
    #return HttpResponse(mydict)
    return JsonResponse(mydict)


@csrf_exempt
def add_new_user(request):
    body_unicode = request.body.decode('utf-8')
    json_data = json.loads(body_unicode)
    #content = body['name']

    email = json_data['email']

    result_emails = Users.objects.filter(email=email).count()
    if(result_emails >= 1):
        outputData = {"outcome":"duplicate_email"}
        return JsonResponse(outputData)

    else:
        ver_code = randint(100000, 999999)

        
        email_body = "Your email verification code is: {}".format(ver_code)
        send_mail('Please verify your email address.', email_body, 'elrondapp@gmail.com', [email])

        name =  json_data['name']
        
        password = json_data['password']
        emailVerificationcode = ver_code
        isemailverified =  0

        new_user = Users()
        new_user.name = name
        new_user.email = email
        new_user.password = password
        new_user.isemailverified = isemailverified
        new_user.emailVerificationcode = emailVerificationcode
        new_user.save()

        outputData = {
            "outcome":"done"
        }
        return JsonResponse(outputData)


@csrf_exempt
def emailverify(request):
    body_unicode = request.body.decode('utf-8')
    json_data = json.loads(body_unicode)
    #content = body['name']

    sentEmail = json_data['email']
    code = json_data['code']
    result_emails = Users.objects.filter(email=sentEmail, emailVerificationcode=code).count()
    #print(result_emails)

    if(result_emails != 1):
        outputData = {"outcome":"email_not_exists"}
        return JsonResponse(outputData)
    
    
    try:
        user = Users.objects.get(email=sentEmail)
        user.isemailverified = 1
        user.save()

        outputData = {"outcome":"done"}
        return JsonResponse(outputData)


    except:
        outputData = {"outcome":"error"}
        return JsonResponse(outputData)


    
    
@csrf_exempt
def loginuser(request):
    body_unicode = request.body.decode('utf-8')
    json_data = json.loads(body_unicode)
    #content = body['name']

    sentEmail = json_data['email']
    sentPassword = json_data['password']
    
    result_emails = Users.objects.filter(email=sentEmail, password=sentPassword).count()
    #print(result_emails)

    if(result_emails != 1):
        outputData = {"outcome":"incorrect"}
        return JsonResponse(outputData)
    
    else:
        outputData = {"outcome":"done"}
        return JsonResponse(outputData)
    
    
    

@csrf_exempt
def send_message_inbox(request):
    body_unicode = request.body.decode('utf-8')
    json_data = json.loads(body_unicode)
    
    send_time = timezone.localtime(timezone.now())
    sender = json_data['sender']
    recepient = json_data['recepient']
    message = json_data['message']
    received_report = 0
    read_report = 0
    
    inbox = Inbox()
    inbox.send_time = send_time
    inbox.sender = sender
    inbox.recepient = recepient
    inbox.message = message
    inbox.received_report = received_report
    inbox.read_report = read_report
    inbox.save()
    
    outputData = {"outcome":"sent"}
    
    



















##########################################################################################################

def index(request):
    my_message = {
        "sender": "Bat-Man",
        "recepient": "Wonder-Woman",
        "message": "Hey! I'm Batman. You wanna know my secret identity?",
        "received_report": 0,
        "read_report": 0
    }

    send_inbox_message(my_message)

    return HttpResponse("Hello, world. You're at Elrond.")



def gmail(request):
    send_mail('Test email on Elrond', 'Hello, World!', 'elrondapp@gmail.com', ['siziba.uz@gmail.com'])
    return HttpResponse("Done.")



def test(request):
    if request.method == 'POST':
        json_data = json.loads(request.body) # request.raw_post_data w/ Django < 1.4
        try:
            data = json_data['data']
        except KeyError:
            return HttpResponseServerError("Malformed data!")
        return HttpResponse("Got json data")

    else:
        data = {
        'name': 'Vitor',
        'location': 'Finland',
        'is_active': True,
        'count': 28
        }
        dump = json.dumps(data)
        return HttpResponse(dump, content_type='application/json')

