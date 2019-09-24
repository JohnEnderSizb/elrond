from django.contrib import admin

from .models import *

admin.site.register(Group)
admin.site.register(Profile)
admin.site.register(Tweet)
admin.site.register(Tweetlikes)
admin.site.register(Tweetcomments)
admin.site.register(Inbox)