# Generated by Django 2.2.5 on 2019-09-08 18:59

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('main', '0007_remove_tweet_tweet_id'),
    ]

    operations = [
        migrations.AddField(
            model_name='users',
            name='verificationCode',
            field=models.IntegerField(default=0),
        ),
    ]
