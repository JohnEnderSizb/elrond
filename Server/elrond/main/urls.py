from django.urls import path, include

from . import views

from rest_framework import routers

router = routers.DefaultRouter()

router.register("group", views.GroupView)

router.register("profile", views.ProfileView)

router.register("tweet", views.TweetView)

router.register("tweetlikes", views.TweetlikesView)

router.register("tweetcomments", views.TweetcommentsView)

router.register("inbox", views.InboxView)

router.register("users", views.UsersView)

urlpatterns = [
    path('main', views.index, name='main'),

    path('gmail', views.gmail, name='gmail'),

    path('test', views.test, name='test'),

    path('', include(router.urls)),

    path('addnewuser', views.add_new_user, name='add_new_user'),
    
    path('emailverify', views.emailverify, name='emailverify'),
    
    path('loginuser', views.loginuser, name='loginuser'),
    
    
    
]
