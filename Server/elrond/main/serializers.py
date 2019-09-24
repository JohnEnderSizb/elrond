from rest_framework import serializers

from .models import *


class GroupSerializer(serializers.ModelSerializer):
    class Meta:
        model = Group
        fields = ("send_time", "sender", "message")

class ProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = Profile
        fields = ("id",
            "full_name",
            "nick_name",
            "date_of_birth",
            "relationship_status",
            "telephone_number",
            "email_address",
            "resident_address",
            "what_makes_you_happy",
            "what_makes_you_sad",
            "what_makes_you_angry")

class TweetSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tweet
        fields = ("id", "tweet_time", "tweet", "sender")


class TweetlikesSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tweetlikes
        fields = (
            "tweet_id",
            "liker",
            "like_time"
        )


class TweetcommentsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tweetcomments
        fields = (
            "tweet_id",
            "comment_by",
            "comment",
            "comment_time"
        )


class InboxSerializer(serializers.ModelSerializer):
    class Meta:
        model = Inbox
        fields = (
            "send_time",
            "sender",
            "recepient",
            "message",
            "received_report",
            "read_report"
        )

class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users
        fields = (
            "name",
            "email",
            "password",
            "emailVerificationcode",
            "isemailverified"
        )