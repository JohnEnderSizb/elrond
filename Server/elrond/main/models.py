from django.db import models
from django.utils.timezone import localtime 
# Create your models here.

class Group(models.Model):
    send_time = models.DateTimeField()
    sender = models.CharField(max_length=50)
    message = models.TextField()
    group_ID = models.IntegerField()

    def __str__(self):
        return "Time sent: {}, Sender: {}, Message: {}".format(self.send_time, self.sender, self.message)

class Profile(models.Model):
    full_name = models.CharField(max_length=50)
    nick_name = models.CharField(max_length=50)
    date_of_birth = models.DateTimeField()
    relationship_status = models.CharField(max_length=100)
    telephone_number = models.CharField(max_length=15)
    email_address = models.CharField(max_length=50)
    resident_address = models.CharField(max_length=200)
    what_makes_you_happy = models.TextField()
    what_makes_you_angry = models.TextField()
    what_makes_you_sad = models.TextField()
    #username = models.CharField(max_length=50)


class Tweet(models.Model):
    #tweet_id = models.TextField()
    tweet_time = models.DateTimeField()
    tweet = models.TextField()
    #tweet_bg = models.CharField(max_length=15)
    sender =  models.CharField(max_length=50)

    def __str__(self):
        return "{}, {}, {}, {}".format(self.tweet_time, self.tweet, self.tweet_bg, self.sender)


class Tweetlikes(models.Model):
    tweet_id = models.IntegerField()
    liker = models.CharField(max_length=50)
    like_time = models.DateTimeField()

class Tweetcomments(models.Model):
    tweet_id = models.IntegerField()
    comment_by = models.CharField(max_length=50)
    comment = models.TextField()
    comment_time = models.DateTimeField()

class Inbox(models.Model):
    send_time = models.DateTimeField()
    sender = models.CharField(max_length=50)
    recepient = models.CharField(max_length=50)
    message = models.TextField()
    received_report = models.IntegerField(default=0) # 1 for yes, 0 for no
    read_report = models.IntegerField(default=0) # 1 for yes, 0 for no

    def __str__(self):
        return "{}, {}, {}, {}, {}, {}".format(self.send_time, self.sender, self.recepient, self.message, self.received_report, self.read_report)

class NewContent(models.Model):
    exception = models.CharField(max_length=50)
    content_type = models.CharField(max_length=50)
    content_for = models.CharField(max_length=50)


class Users(models.Model):
    name =  models.CharField(max_length=50)
    email = models.CharField(max_length=50)
    password = models.CharField(max_length=50)
    emailVerificationcode = models.IntegerField(default=0)
    isemailverified =  models.IntegerField(default=0)
    
    
class GroupMemberships(models.Model):
    email = models.CharField(max_length=50)
    group_ID =  models.IntegerField()
    
