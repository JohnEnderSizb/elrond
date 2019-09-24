import json
from django.utils import timezone
from .models import *


def send_group_message(msg_dict):
    try:
        send_time = timezone.now()
        sender = msg_dict["sender"]
        message = msg_dict["message"]
        group = Group()
        group.send_time = send_time
        group.sender = sender
        group.message = message
        group.save()

        addNewContent("grp_mess", "group", sender)

    except:
        return False

    return True


def send_tweet(tweet_dict):
    try:
        #tweet_time = tweet_dict[""]
        tweet_time = timezone.now()
        tweet_content = tweet_dict["tweet"]
        tweet_bg = tweet_dict["tweet_bg"]
        sender = tweet_dict["sender"]

        tweet = Tweet()
        tweet.tweet_time = tweet_time
        tweet.tweet = tweet_content
        tweet.tweet_bg = tweet_bg
        tweet.sender = sender
        tweet.save()

        addNewContent("tweet", "public", sender)
    except:
        return False
    
    return True

def like_tweet(tweet_info_dict):
    pass

def comment_tweet(tweet_info_dicr):
    pass

def update_profile(profile_dict):
    pass

def send_inbox_message(message_dict):
    send_time = timezone.now()
    sender = message_dict["sender"]
    recepient = message_dict["recepient"]
    message = message_dict["message"]
    received_report = message_dict["received_report"]
    read_report = message_dict["read_report"]

    inbox = Inbox()

    inbox.send_time = send_time
    inbox.sender = sender
    inbox.recepient = recepient
    inbox.message = message
    inbox.received_report = received_report
    inbox.read_report = read_report

    inbox.save()

    addNewContent("inbox", recepient, sender)

def addNewContent(content_type, content_for, exception):
    content = NewContent()
    content.exception = exception
    content.content_type = content_type
    content.content_for = content_for



